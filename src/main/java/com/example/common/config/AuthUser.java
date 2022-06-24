package com.example.common.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/*User中封装了用户输入的账号、密码、权限等信息*/
public class AuthUser extends User {

    @Getter
    private Long id;

    @Getter
    private String token;

    public AuthUser(Long id, String token, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.token = token;
    }


}
