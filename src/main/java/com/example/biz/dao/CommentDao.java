package com.example.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.biz.entity.SysComment;
import com.example.common.utils.QueryPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao extends BaseMapper<SysComment> {

    List<SysComment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}
