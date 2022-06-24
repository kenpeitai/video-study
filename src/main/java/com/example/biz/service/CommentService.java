package com.example.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.biz.entity.SysComment;
import com.example.common.utils.QueryPage;

import java.util.List;

public interface CommentService extends IService<SysComment> {

    List<SysComment> findByTypeId(String type, String id);

    /**
     * 分页查询
     */
    IPage<SysComment> list(SysComment comment, QueryPage queryPage);

    /**
     * 新增
     */
    void add(SysComment comment);

    /**
     * 更新
     */
    void update(SysComment comment);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id更新
     */
    boolean updateById(SysComment comment);
}
