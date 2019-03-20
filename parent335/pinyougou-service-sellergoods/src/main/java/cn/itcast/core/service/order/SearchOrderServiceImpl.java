//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.itcast.core.service.order;

import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderItemQuery.Criteria;
import cn.itcast.core.service.searchOrder.SearchOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;

@Service
public class SearchOrderServiceImpl implements SearchOrderService {
    @Resource
    private OrderItemDao orderItemDao;

    public SearchOrderServiceImpl() {
    }

    public PageResult search(Integer pageNo, Integer pageSize, OrderItem orderItem) {
        PageHelper.startPage(pageNo, pageSize);
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        Criteria criteria = orderItemQuery.createCriteria();
        if (orderItem.getId() != null && !"".equals(orderItem.getId())) {
            criteria.andOrderIdEqualTo(orderItem.getId());
        }

        orderItemQuery.setOrderByClause("id desc");
        Page<OrderItem> page = (Page)this.orderItemDao.selectByExample(orderItemQuery);
        return new PageResult(page.getTotal(), page.getResult());
    }
}

