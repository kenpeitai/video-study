package com.example.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.biz.dao.CourseDao;
import com.example.biz.entity.SysArticle;
import com.example.biz.entity.SysCourse;
import com.example.biz.service.CourseService;
import com.example.common.utils.LongUtil;
import com.example.common.utils.QueryPage;
import com.example.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, SysCourse> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    /*
      首页课程查询
     */
    @Override
    public List<SysCourse> getNew(int limit) {
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(
                SysCourse::getId,
                SysCourse::getTitle,
                SysCourse::getDes,
                SysCourse::getImg,
                SysCourse::getType,
                SysCourse::getRank,
                SysCourse::getTags);  //select设置要查询的字段
        queryWrapper.orderByDesc(SysCourse::getId);
        return courseDao.selectPage(new Page<>(1, limit), queryWrapper).getRecords();
    }

    /*
       课程管理，查出所有课程
     */
    @Override
    public IPage<SysCourse> list(SysCourse sysCourse, QueryPage queryPage) {

        IPage<SysCourse> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LongUtil.isNotBlank(sysCourse.getUid()), SysCourse::getUid, sysCourse.getUid());
        queryWrapper.orderByDesc(SysCourse::getId);
        queryWrapper.like(StringUtil.isNotBlank(sysCourse.getTitle()), SysCourse::getTitle, sysCourse.getTitle());
        queryWrapper.like(StringUtil.isNotBlank(sysCourse.getTags()), SysCourse::getTags, sysCourse.getTags());
        queryWrapper.like(StringUtil.isNotBlank(sysCourse.getRank()), SysCourse::getRank, sysCourse.getRank());
        return courseDao.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysCourse sysCourse) {
        courseDao.updateById(sysCourse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        courseDao.deleteById(id);
    }

    @Override
    public List<Object> findTagByTagTitle(String tagTitle) {
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCourse::getTagTitle, tagTitle);
        queryWrapper.select(SysCourse::getTags);
        return courseDao.selectObjs(queryWrapper);
    }

    @Override
    public List<SysCourse> findCourseByRank(String rank) {
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCourse::getRank, rank);
        return courseDao.selectPage(new Page<>(1, 10), queryWrapper).getRecords();
    }

    @Override
    public List<SysCourse> limitFindByTagTitle(int limit, String tagTitle) {
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCourse::getTagTitle, tagTitle);
        return courseDao.selectPage(new Page<>(1, limit), queryWrapper).getRecords();
    }

    @Override
    public IPage<SysCourse> getCourseByFilter(String tag, String title, String rank, QueryPage queryPage) {
        IPage<SysCourse> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotBlank(tag), SysCourse::getTags, tag);
        queryWrapper.like(StringUtil.isNotBlank(title), SysCourse::getTitle, title);
        queryWrapper.eq(StringUtil.isNotBlank(rank), SysCourse::getRank, rank);
        queryWrapper.orderByDesc(SysCourse::getId);
        return courseDao.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysCourse sysCourse) {
        courseDao.insert(sysCourse);
    }
}
