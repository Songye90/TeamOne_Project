package cn.itcast.core.controller.brand;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.brand.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    /**
     * 品牌申请
     * @param brand
     * @return
     */
    @RequestMapping("/saveBrand.do")
    public Result saveBrand(@RequestBody Brand brand) {
        try {
            brandService.saveBrand(brand);
            return new Result(true, "申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "申请失败");
        }
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping("/findPage.do")
    public PageResult findPage(Integer pageNo, Integer pageSize){
        return brandService.findPage(pageNo, pageSize);
    }

    /**
     * 条件查询
     * @param pageNo
     * @param pageSize
     * @param brand
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult search(Integer pageNo, Integer pageSize, @RequestBody Brand brand){
        return brandService.search(pageNo, pageSize, brand);
    }
}
