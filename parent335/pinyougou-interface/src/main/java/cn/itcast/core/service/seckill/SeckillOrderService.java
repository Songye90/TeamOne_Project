package cn.itcast.core.service.seckill;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;

public interface SeckillOrderService {
    /**
     * 查询所有订单
     * @param page
     * @param rows
     * @param seckillOrder
     * @return
     */
    public PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder);
}
