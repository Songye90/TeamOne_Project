package cn.itcast.core.controller.seckill;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.seckill.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/seckillgoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillgoosService;


    /**
     * 查询所有商品
     * @param page
     * @param rows
     * @param seckillGoods
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillGoods seckillGoods){
        // 设置条件：商家的id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return seckillgoosService.search(page, rows, seckillGoods);
    }

    /**
     * 秒杀商品申请
     * @param map
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody Map map ) {
        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            seckillgoosService.add(map,sellerId);
            return new Result(true, "申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "申请失败");
        }
    }

    /**
     * 新建秒杀商品申请
     * @param seckillGoods
     * @return
     */
    @RequestMapping("/addNewSeckill.do")
    public Result addNewSeckill(@RequestBody SeckillGoods seckillGoods ) {
        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            seckillGoods.setSellerId(sellerId);
            seckillgoosService.addNewSeckill(seckillGoods);
            return new Result(true, "申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "申请失败");
        }
    }
}
