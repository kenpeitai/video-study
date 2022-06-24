package com.example.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.biz.dao.AccessTokenDao;
import com.example.biz.entity.SysAccessToken;
import com.example.biz.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenDao, SysAccessToken> implements AccessTokenService {

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Override
    public SysAccessToken findByToken(String token) {
        LambdaQueryWrapper<SysAccessToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccessToken::getToken, token);
        return accessTokenDao.selectOne(queryWrapper);
    }

    public boolean isExist(SysAccessToken accessToken) {
        LambdaQueryWrapper<SysAccessToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccessToken::getUserName, accessToken.getUserName());
        queryWrapper.eq(SysAccessToken::getToken, accessToken.getToken());
        List<SysAccessToken> list = accessTokenDao.selectList(queryWrapper);
        return list.size() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysAccessToken accessToken) {
        if (!this.isExist(accessToken)) {
            accessTokenDao.insert(accessToken);
        }
    }

    //注销
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        accessTokenDao.deleteById(id);
    }
}
