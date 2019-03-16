package cn.itcast.core.controller.user;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.user.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    /* 用户表 tb_user表中添加is_delete字段 冻结用户对用户进行逻辑删除 "0" 表示可用 "1"表示已删除
    *  ALTER TABLE tb_user ADD is_delete  VARCHAR(50) NOT NULL;
    * */
    @Reference
    UserService userService;
    @RequestMapping("/deleteOne.do")
    public Result deleteOne(Long id){
        try {
            userService.deleteOne(id);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }

    }
    @RequestMapping("/search.do")
    public PageResult search(@RequestBody User user){

        return null;
    }

}
