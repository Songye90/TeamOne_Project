package cn.itcast.core.controller.order;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.ShopOrder;
import cn.itcast.core.service.order.ShopOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping({"/ShopOrder"})
public class ShopOrderController {
    @Reference
    private ShopOrderService shopOrderService;

    public ShopOrderController() {
    }

    @RequestMapping({"/search.do"})
    public PageResult search(Integer page, Integer rows, @RequestBody ShopOrder shopOrder) {
        return this.shopOrderService.search(page, rows, shopOrder);
    }

    @RequestMapping({"/count.do"})
    public List<ShopOrder> count(@RequestBody ShopOrder shopOrder) {
        return this.shopOrderService.count(shopOrder);
    }
}