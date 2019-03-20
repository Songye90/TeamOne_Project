package cn.itcast.core.service.user;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserHot;
import cn.itcast.core.pojo.user.UserQuery;
import cn.itcast.core.utils.md5.MD5Util;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private ItemDao itemDao;

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination smsDestination;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserDao userDao;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 用户获取短信验证码
     * @param phone
     */
    @Override
    public void sendCode(final String phone) {
        // 将获取短信验证码的数据发送到mq中
        // 手机号、验证码、签名、模板
        final String code = RandomStringUtils.randomNumeric(6);
        System.out.println("code:"+code);
        // 保存验证码
        redisTemplate.boundValueOps(phone).set(code);
        // 设置验证码的过期时间
        redisTemplate.boundValueOps(phone).expire(5, TimeUnit.MINUTES);
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 封装map消息体
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("phoneNumbers", phone);
                mapMessage.setString("signName", "阮文");
                mapMessage.setString("templateCode", "SMS_140720901");
                mapMessage.setString("templateParam", "{\"code\":\""+code+"\"}");
                return mapMessage;
            }
        });
    }
    @Transactional
    @Override
    public void deleteOne(Long id) {
        userDao.deleteOne(id);
    }
    @Transactional
    @Override
    public void deleteMany(Long[] ids) {
        for (Long id : ids) {
            userDao.deleteOne(id);
        }
    }

    @Override
    public void addToRedis(String username) {

        String currentData = dateFormat.format(new Date());
        redisTemplate.expire(currentData,7,TimeUnit.DAYS);
        redisTemplate.boundSetOps(currentData).add(username);



    }

    @Override
    public void updatelastlogintime(String username) {
        UserQuery query = new UserQuery();
        UserQuery.Criteria criteria = query.createCriteria();
        criteria.andNameEqualTo(username);
        List<User> userList = userDao.selectByExample(query);
        for (User user : userList) {
            user.setLastLoginTime(new Date());
            userDao.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public Integer getcurrentlogincount() {
        String currentData = dateFormat.format(new Date());
        Long size = redisTemplate.boundSetOps(currentData).size();
        return size.intValue();
       
    }

    @Override
    @Scheduled(cron =  "00 59 23 * * ?")
    public void savecurrentlogin() {
        UserHot userHot = new UserHot();
        String onlinedate = dateFormat.format(new Date());
        userHot.setOnlinedata(onlinedate);
        Integer onlinenum = getcurrentlogincount();
        userHot.setOnlinenum(onlinenum);
        userDao.saveUserHot(userHot);

    }

    @Override
    public UserHot findUserHotByDate(String date) {
        return userDao.findUserHotByDate(date);
    }

    @Override
    public PageResult search(Integer page, Integer rows,User user) {
        PageHelper.startPage(page,rows);
        PageHelper.orderBy("id desc");
        UserQuery query = new UserQuery();
        UserQuery.Criteria criteria = query.createCriteria();

        if (user.getUsername()!=null) {
        criteria.andUsernameLike("%"+user.getUsername()+"%");
        }
        Page<User> userList = (Page<User>) userDao.selectByExample(query);

        return new PageResult(userList.getTotal(),userList.getResult());
    }

    /**
     * 用户注册
     * @param user
     * @param smscode
     */
    @Transactional
    @Override
    public void add(User user, String smscode) {
        // 校验验证码是否正确
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if(smscode != null && !"".equals(smscode) && smscode.equals(code)){
            // 对密码加密
            String password = MD5Util.MD5Encode(user.getPassword(), null);
            user.setPassword(password);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            userDao.insertSelective(user);
        }else{
            throw new RuntimeException("输入的验证码不正确");
        }
    }

    /**
     * 用户 我的订单查询
     * @param page
     * @param rows
     * @param userId
     * @return
     */
    @Override
    public PageResult findAll(Integer page, Integer rows, String userId) {
        //设置分页条件
        PageHelper.startPage(page, rows);
        //订单查询
        OrderQuery orderQuery = new OrderQuery();
        if (userId != null && !"".equals(userId)) {
            orderQuery.createCriteria().andUserIdEqualTo(userId);

        }
        List<Order> orderList = orderDao.selectByExample(orderQuery);

        ArrayList<Object> list = new ArrayList<>();
        if (orderList != null && orderList.size() > 0) {
            for (Order order : orderList) {
                //存放规格选项 规格集合
                Map<String, Object> orderMap = new HashMap<>();
                //存放规格集合
                ArrayList<Object> list1 = new ArrayList<>();
                OrderItemQuery orderItemQuery = new OrderItemQuery();
                orderItemQuery.createCriteria().andOrderIdEqualTo(order.getOrderId());
                //查询出所有的订单项
                List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
                //遍历订单项
                for (OrderItem orderItem : orderItems) {
                    //存放订单项  规格集合
                    HashMap<String, Object> orderItemMap = new HashMap<>();
                    ArrayList<String> orderItemList = new ArrayList<>();
                    //查询出订单项的规格集合
                    Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());
                    if (item.getSpec() != null) {
                        Map map = JSON.parseObject(item.getSpec(), Map.class);
                        Set set = map.keySet();
                        for (Object o : set) {
                            orderItemList.add(map.get(o).toString());

                        }
                    }
                    //从内向外存  第一步 把订单项和其对应的规格list集合 存到  一个 map里
                    orderItemMap.put("orderItem", orderItem);
                    orderItemMap.put("specList", orderItemList);
                    //第二步  将上一步的map集合 存到一个 List几何中
                    list1.add(orderItemMap);

                }
                //把上步的获得的订单项与订单对应的 订单项集合存到一个map中
                orderMap.put("order", order);
                orderMap.put("orderItemList", list1);
                //把上一步获得的map存到list中
                list.add(orderMap);
            }
        }
        return new PageResult(list.size(),list);
    }
}
