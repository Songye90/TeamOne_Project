package cn.itcast.core.service.seckill;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.utils.datetostring.DateToString;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Resource
    private SeckillGoodsDao seckillGoodsDao;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private SeckillOrderDao seckillOrderDao;

    /**
     * 查询所有商品
     * @param page
     * @param rows
     * @param seckillGoods
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods) {
        // 设置分页的条件
        PageHelper.startPage(page, rows);
        // 设置查询条件：设置当前的商家
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        if(seckillGoods.getSellerId() != null && !"".equals(seckillGoods.getSellerId())){
            query.createCriteria().andStatusEqualTo("1").andSellerIdEqualTo(seckillGoods.getSellerId());

        }
        query.setOrderByClause("id desc");
        // 查询
        Page<SeckillGoods> p = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(query);
        String pic = "../";
        for (SeckillGoods secgoods : p) {
            secgoods.setSmallPic(pic+secgoods.getSmallPic());
        }
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 商品秒杀申请
     * @param map
     */
    @Override
    public void add(Map map,String sellerId) {
        DateToString datetoString = new DateToString();
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStatus("0");
        seckillGoods.setSellerId(sellerId);
        String goods = map.get("goods").toString();
        Map<String,String> goodsMap = JSON.parseObject(goods, Map.class);
        if (goodsMap!=null&&goodsMap.size()>0) {
            String startTime1 = goodsMap.get("startTime");
            if (startTime1 != null &&! "".equals(startTime1)) {
            Date startTime = datetoString.dataToString(startTime1 );
             seckillGoods.setStartTime(startTime);
            }
            String endTime1 = goodsMap.get("endTime");
            if (endTime1 != null && !"".equals(endTime1)) {
            Date endTime = datetoString.dataToString(endTime1);
                seckillGoods.setEndTime(endTime);
            }
            /*BigDecimal price = new BigDecimal(goodsMap.get("price"));
            seckillGoods.setPrice(price);*/
        }
        String itemList = map.get("itemList").toString().replace("[", "").replace("]", "");
        Map<String,String> itemListMap = JSON.parseObject(itemList, Map.class);
        System.out.println(itemListMap.toString());
        if (itemListMap!=null&&itemListMap.size() > 0) {
            String title = itemListMap.get("title");
            seckillGoods.setTitle(title);
            String goodsId = itemListMap.get("goodsId").toString();
            if (goodsId != null && !"".equals(goodsId)) {
            seckillGoods.setGoodsId(Long.parseLong(goodsId));
            }

        }
        String goodsDesc = map.get("goodsDesc").toString() .replace("[", "").replace("]", "");
        Map<String,String> goodsDescMap = JSON.parseObject(goodsDesc, Map.class);
        System.out.println(goodsDescMap.toString());
        if (goodsDescMap != null & goodsDescMap.size() > 0) {
            String introduction = (String) goodsDescMap.get("introduction");
            System.out.println(introduction);
            seckillGoods.setIntroduction(introduction);
        }
        seckillGoodsDao.insertSelective(seckillGoods);
    }

    /**
     * 新建秒杀商品申请
     * @param seckillGoods
     */
    @Override
    public void addNewSeckill(SeckillGoods seckillGoods) {
        seckillGoods.setStatus("0");
        seckillGoodsDao.insertSelective(seckillGoods);
    }
}
