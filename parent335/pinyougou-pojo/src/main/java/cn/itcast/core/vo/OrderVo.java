package cn.itcast.core.vo;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

public class OrderVo implements Serializable {
    private Order order;
    private OrderItem orderItem;

    public OrderVo(Order order, OrderItem orderItem) {
        this.order = order;
        this.orderItem = orderItem;
    }

    public OrderVo() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
