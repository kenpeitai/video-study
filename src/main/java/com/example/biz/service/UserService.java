package com.example.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.biz.entity.SysUser;
import com.example.common.utils.QueryPage;

public interface UserService extends IService<SysUser> {

    String login(String username, String password);

    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    SysUser findByName(String username);

    /**
     * 新增
     *
     * @param sysUser
     */
    void add(SysUser sysUser);

    /**
     * 更新
     *
     * @param sysUser
     */
    void update(SysUser sysUser);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    IPage<SysUser> list(SysUser sysUser, QueryPage queryPage);

    void forgot(SysUser sysUser);





}
