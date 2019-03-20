
package cn.itcast.core.service.user;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserHot;

public interface UserService {

    /**
     * 用户获取短信验证码
     *
     * @param phone
     */
    public void sendCode(String phone);

    /**
     * 用户注册
     *
     * @param user
     * @param smscode
     */
    public void add(User user, String smscode);

    void deleteOne(Long id);

    PageResult search(Integer page, Integer rows, User user);

    void deleteMany(Long[] ids);

    void addToRedis(String username);

    void updatelastlogintime(String username);

    Integer getcurrentlogincount();

    void savecurrentlogin();

    UserHot findUserHotByDate(String date);

    /**
     * 用户 我的订单查询
     * @param page
     * @param rows
     * @param userId
     * @return
     */
    public PageResult findAll(Integer page, Integer rows, String userId);
}