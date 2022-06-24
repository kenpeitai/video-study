package com.example.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.biz.dao.CommentDao;
import com.example.biz.entity.SysComment;
import com.example.biz.service.CommentService;
import com.example.common.utils.QueryPage;
import com.example.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, SysComment> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<SysComment> findByTypeId(String type, String id) {
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysComment::getType, type);
        queryWrapper.eq(SysComment::getTid, id);
        return commentDao.selectList(queryWrapper);
    }

    /*
    评论管理界面
     */
    @Override
    public IPage<SysComment> list(SysComment comment, QueryPage queryPage) {
        IPage<SysComment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotBlank(comment.getContent()), SysComment::getContent, comment.getContent());
        queryWrapper.orderByDesc(SysComment::getId);
        return commentDao.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysComment comment) {
        comment.setCreateTime(new Date());
        commentDao.insert(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysComment comment) {
        commentDao.updateById(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        commentDao.deleteById(id);
    }

}
