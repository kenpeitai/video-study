package com.example.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.biz.entity.SysAccessToken;

public interface AccessTokenService extends IService<SysAccessToken> {

    SysAccessToken findByToken(String token);

    void add(SysAccessToken accessToken);

    void delete(Long id);
}
