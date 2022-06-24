package com.example.common.config.service;

import com.example.biz.entity.SysAccessToken;
import com.example.biz.entity.SysUser;
import com.example.biz.service.AccessTokenService;
import com.example.biz.service.UserService;
import com.example.common.config.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 身份认证
 * 实现UserDetailsService接口，自动调用loadUserByname，将输入的用户名，获取用户及其权限信息，封装UserDetails,
 * 然后就可以认证用户名，密码与数据库的用户名和密码校验
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    /* loadUserByUsername根据用户登录时输入的用户名，去存储中读取用户信息，封装后返回，springsecurity拿用户信息做相应的处理和校验*/
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库当中查询用户
        SysUser user = userService.findByName(username);
        return getUserDetails(user); //不为空就返回

    }

    private UserDetails getUserDetails(SysUser user) {
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 持久化当前登录的Token值，并存储到SpringSecurity的SecurityContextHolder对象中
        //登录成功生成token令牌，保存到数据库
        String token = UUID.randomUUID().toString();
        accessTokenService.add(new SysAccessToken(token, user.getUsername()));
        //定义权限的集合
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ADMIN");

        return new AuthUser(user.getId(), token, user.getUsername(), user.getPassword(), authorityList);
        //当前返回的用户有哪些权限，然后去安全配置中验证用户的权限
    }
}
