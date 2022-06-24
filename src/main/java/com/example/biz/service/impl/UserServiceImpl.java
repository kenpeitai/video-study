package com.example.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.biz.dao.UserDao;
import com.example.biz.entity.SysUser;
import com.example.biz.service.UserService;
import com.example.common.config.AuthUser;
import com.example.common.exception.GlobalException;
import com.example.common.utils.QueryPage;
import com.example.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 1.用户名和密码进行登录
     * 2.验证该用户名的密码正确  自定义认证方式
     * 3.获取该用户的环境信息 (他们的角色列表等).
     * 4.为用户建立安全的环境。
     * 5.用户进行，可能执行一些操作，这是潜在的保护的访问控制机制，检查所需权限，对当前的安全的环境信息的操作
     * @param username  输入的用户名
     * @param password  输入的密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        try {
            //自定义认证方式：实现UserDetailsService接口，自动调用loadUserByname，将输入的用户名，获取用户及其权限信息，封装UserDetails,然后就可以认证
            //通过传递的用户名提供UserDetails实例，UserDetails通过应用DAO，提供必要的信息，构建Authentication对象
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new GlobalException("登录密码错误");  //matches比对登录的加密后和数据库中的密码
            }
            //用户名和密码进行组合成一个实例UsernamePasswordAuthenticationToken，通过用户名，密码，权限构建用户环境信息
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //安全环境是通过调用 SecurityContextHolder.getContext().setAuthentication(…​)传递到返回的验证对象建立的
            //将身份认证通过后的账户信息存储大到Security的SecurityContextHolder对象中
            //成功认证后，UserDetails用于构建存储在SecurityContextHolder的Authentication对象。
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof AuthUser) {
                AuthUser authUser = (AuthUser) principal;
                return authUser.getToken(); //如果登录，返回token
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("用户名或密码错误");
        }
        return null;
    }
      /*
      用户名搜索
       */
    @Override
    public SysUser findByName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        List<SysUser> list = userDao.selectList(queryWrapper);
        return list.size() > 0 ? list.get(0) : null;
    }

    //注册的实现
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUser sysUser) {
        sysUser.setAvatar("/cache/default.jpg");
        sysUser.setIdentity("普通用户");
        SysUser user = this.findByName(sysUser.getUsername());
        if (user != null) {
            throw new GlobalException("该用户名已存在");
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        userDao.insert(sysUser);  //加密后保存数据库
    }

    /*
         更新用户信息实现
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser sysUser) {
        sysUser.setPassword(null);
        userDao.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    /*
     用户管理界面     查询所有用户
     */
    @Override
    public IPage<SysUser> list(SysUser sysUser, QueryPage queryPage) {
        IPage<SysUser> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotBlank(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername());
        queryWrapper.orderByDesc(SysUser::getId);
        return userDao.selectPage(page, queryWrapper);
    }

    @Override
    public void forgot(SysUser sysUser) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        queryWrapper.eq(SysUser::getTelephone, sysUser.getTelephone());
        List<SysUser> list = userDao.selectList(queryWrapper);
        if (list.size() > 0) {
            //存在，重置密码
            SysUser user = list.get(0);
            user.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            userDao.updateById(user);
        } else {
            throw new GlobalException("该用户不存在");
        }
    }
}
