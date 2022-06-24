package com.example.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.biz.dao.ArticleDao;
import com.example.biz.entity.SysArticle;
import com.example.biz.service.ArticleService;
import com.example.common.utils.LongUtil;
import com.example.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, SysArticle> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<SysArticle> getNew() {
        /**
         *  LambdaQueryWrapper是Mybatis一个插件，
         *  LambdaQueryWrapper 的条件构造器方法对应Sql
         *  Wrapper ： 条件构造抽象类，根据语法动态生成sql
         *   LambdaQueryWrapper用于Lambda语法使用的查询Wrapper，Lambda表达式
         *   管理员文章管理界面
         */
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(
                SysArticle::getId,
                SysArticle::getTitle,
                SysArticle::getDes,
                SysArticle::getImg,
                SysArticle::getType,
                SysArticle::getTags);
        queryWrapper.orderByDesc(SysArticle::getId);  //降序
        return articleDao.selectPage(new Page<>(1, 10), queryWrapper).getRecords();
    }

     /*
     会员文章查看界面
      */
    @Override
    public IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage) {
        IPage<SysArticle> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        // 根据 Wrapper 条件，查询全部记录  eq对应等于  like对应模糊查询
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LongUtil.isNotBlank(sysArticle.getUid()), SysArticle::getUid, sysArticle.getUid());
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getTitle()), SysArticle::getTitle, sysArticle.getTitle());
        queryWrapper.orderByDesc(SysArticle::getId);
        return articleDao.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysArticle sysArticle) {
        articleDao.updateById(sysArticle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        articleDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysArticle sysArticle) {
        articleDao.insert(sysArticle);
    }
}
