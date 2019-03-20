package cn.itcast.core.service.seckill;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.Map;

public interface SeckillGoodsService {
    /**
     * 查询所有商品
     * @param page
     * @param rows
     * @param seckillGoods
     * @return
     */
    PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods);

    /**
     * 商品申请秒杀
     * @param map
     */
    void add(Map map, String sellerId);

    /**
     *
     * @param seckillGoods
     */
    void addNewSeckill(SeckillGoods seckillGoods);
}
