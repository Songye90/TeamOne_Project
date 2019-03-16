package cn.itcast.core.service.user;

import cn.itcast.core.entity.PageResult;
import cn.itcast.core.pojo.user.User;

public interface UserService {

    /**
     * 用户获取短信验证码
     * @param phone
     */
    public void sendCode(String phone);

    /**
     * 用户注册
     * @param user
     * @param smscode
     */
    public void add(User user, String smscode);

    void deleteOne(Long id);

    PageResult search(Integer page, Integer rows,User user);

    void deleteMany(Long[] ids);

}
