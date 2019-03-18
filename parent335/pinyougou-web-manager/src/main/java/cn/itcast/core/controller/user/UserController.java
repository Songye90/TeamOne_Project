package cn.itcast.core.controller.user;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    /* 冻结用户对用户表status使用状态修改 Y正常 N非正常
     *
     * */
    @Reference
    UserService userService;

    @RequestMapping("/deleteOne.do")
    public Result deleteOne(Long id) {
        try {
            userService.deleteOne(id);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }

    }

    @RequestMapping("/search.do")
    public PageResult search(Integer page, Integer rows, @RequestBody User user) {

        PageResult result = userService.search(page, rows, user);
        return result;
    }

    @RequestMapping("/deleteMany.do")
    public Result deleteMany(Long[] ids) {
        try {
            userService.deleteMany(ids);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }
    }
}
