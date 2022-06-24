package com.example.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.biz.entity.SysCourse;
import com.example.common.utils.QueryPage;

import java.util.List;

public interface CourseService extends IService<SysCourse> {

    List<SysCourse> getNew(int limit);

    IPage<SysCourse> list(SysCourse sysCourse, QueryPage queryPage);

    void update(SysCourse sysCourse);

    void delete(Long id);

    List<Object> findTagByTagTitle(String tagTitle);

    List<SysCourse> findCourseByRank(String rank);

    List<SysCourse> limitFindByTagTitle(int limit, String tagTitle);

    IPage<SysCourse> getCourseByFilter(String tag, String title, String rank, QueryPage queryPage);

    void add(SysCourse sysCourse);
}
