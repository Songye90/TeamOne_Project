package cn.itcast.core.service;

import cn.itcast.core.service.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的认证类
 */
public class UserDetailServiceImpl implements UserDetailsService {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 授权
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //程序走到这里肯定已经登陆成功了，把已登录用户的用户名加入redis
        userService.addToRedis(username);
        userService.updatelastlogintime(username);
        Set<GrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(grantedAuthority);
        User user = new User(username, "", authorities);
        return user;
    }
}
