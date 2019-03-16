package cn.itcast.core.service.user;

import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import cn.itcast.core.utils.md5.MD5Util;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination smsDestination;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserDao userDao;

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
    public PageResult search(Integer page, Integer rows,User user) {
        PageHelper.startPage(page,rows);
        PageHelper.orderBy("id desc");
        UserQuery query = new UserQuery();
        UserQuery.Criteria criteria = query.createCriteria();

        if (user!=null) {
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
}
