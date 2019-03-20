package cn.itcast.core.service.seckill;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Resource
    private SeckillOrderDao seckillOrderDao;

    /**
     * 查询所有订单
     * @param page
     * @param rows
     * @param seckillOrder
     * @return
     */
    public PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder) {
        // 设置分页的条件
        PageHelper.startPage(page, rows);
        // 设置查询条件：设置当前的商家
        SeckillOrderQuery query = new SeckillOrderQuery();
        if(seckillOrder.getSellerId() != null && !"".equals(seckillOrder.getSellerId())){
            query.createCriteria().andStatusEqualTo("1").andSellerIdEqualTo(seckillOrder.getSellerId());
        }
        // 查询
        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(query);
        return new PageResult(p.getTotal(), p.getResult());
    }
}
