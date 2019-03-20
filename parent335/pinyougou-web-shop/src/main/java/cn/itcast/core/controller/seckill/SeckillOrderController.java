package cn.itcast.core.controller.seckill;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.seckill.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillorder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 查询所有订单
     * @param page
     * @param rows
     * @param seckillOrder
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillOrder seckillOrder){
        // 设置条件：商家的id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrder.setSellerId(sellerId);
        return seckillOrderService.search(page, rows, seckillOrder);
    }
}
