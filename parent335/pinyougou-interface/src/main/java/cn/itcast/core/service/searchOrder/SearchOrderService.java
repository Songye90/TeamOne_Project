//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.itcast.core.service.searchOrder;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.order.OrderItem;

public interface SearchOrderService {
    PageResult search(Integer var1, Integer var2, OrderItem var3);
}