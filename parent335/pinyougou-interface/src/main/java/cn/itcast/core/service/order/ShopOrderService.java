//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.itcast.core.service.order;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.order.ShopOrder;
import java.util.List;

public interface ShopOrderService {
    PageResult search(Integer var1, Integer var2, ShopOrder var3);

    List<ShopOrder> count(ShopOrder var1);
}

