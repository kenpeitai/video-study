package com.example.biz.controller;

import com.example.biz.entity.SysUser;
import com.example.biz.service.UserService;
import com.example.common.config.AuthUser;
import com.example.common.controller.BaseController;
import com.example.common.exception.GlobalException;
import com.example.common.utils.QueryPage;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public R register(@RequestBody SysUser sysUser) {
        userService.add(sysUser);
        return new R<>();
    }

    @PostMapping("/login")
    public R login(@RequestBody SysUser sysUser) {
        try {
            //登陆，服务器返回一个token
            String token = userService.login(sysUser.getUsername(), sysUser.getPassword());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            return new R<>(map);  //第一次登陆，后端给前端返回token
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(e);
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/forgot")
    public R forgot(@RequestBody SysUser sysUser) {
        userService.forgot(sysUser);
        return new R<>();
    }

    /**
     * 获取已登录用户信息
     */
    @GetMapping("/info")
    public R getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthUser) {
            AuthUser authUser = (AuthUser) principal;
            return new R<>(userService.findByName(authUser.getUsername()));
        } else {
            throw new GlobalException("用户登录状态失效，请重新登录");
        }
    }

    /**
     * 条件查询
     */
    @PostMapping("/list")
    public R getArticleByFilter(@RequestBody SysUser sysUser, QueryPage queryPage) {
        return new R<>(this.getData(userService.list(sysUser, queryPage)));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(userService.getById(id));
    }

    @PostMapping
    public R add(@RequestBody SysUser sysUser) {
        sysUser.setAvatar("/cache/default.jpg");
        sysUser.setIdentity("普通用户");
        userService.add(sysUser);
        return new R();
    }
     /*
        个人中心，更新用户
      */
    @PutMapping
    public R update(@RequestBody SysUser sysUser) {
        userService.update(sysUser);
        return new R();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        userService.delete(id);
        return new R();
    }
}
