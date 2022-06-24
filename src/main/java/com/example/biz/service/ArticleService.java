package com.example.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.biz.entity.SysArticle;
import com.example.common.utils.QueryPage;

import java.util.List;

public interface ArticleService extends IService<SysArticle> {
    //ctrl+o可查看所有通用mapper方法
    List<SysArticle> getNew();

    IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage);

    void update(SysArticle sysArticle);

    void delete(Long id);

    void add(SysArticle sysArticle);
}
