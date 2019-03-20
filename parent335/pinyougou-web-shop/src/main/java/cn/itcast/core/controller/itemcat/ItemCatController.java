package cn.itcast.core.controller.itemcat;

import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.itemcat.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;


    /**
     * 商品分类列表查询
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId.do")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    /**
     * 新增商品：回显模板id
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public ItemCat findOne(Long id){
        return itemCatService.findOne(id);
    }

    /**
     * 商品列表回显分类信息
     * @return
     */
    @RequestMapping("/findAll.do")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }

    /**
     * 分类申请
     * @param itemCat
     * @return
     */
    @RequestMapping
    public Result add(@RequestBody ItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new Result(true, "申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "申请失败");
        }
    }
}
