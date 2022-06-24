package com.example.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.biz.entity.SysCourse;
import com.example.biz.entity.SysUser;
import com.example.biz.service.CourseService;
import com.example.biz.service.UserService;
import com.example.common.config.AuthUser;
import com.example.common.constants.CommonConstant;
import com.example.common.controller.BaseController;
import com.example.common.utils.QueryPage;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/anon/all")
    public R getAll() {
        return new R<>(courseService.list());
    }

    /**
     * 查询最新的10条数据
     *
     * @return
     */
    @GetMapping("/anon/new")
    public R getNew() {
        return new R<>(courseService.getNew(10));
    }

    /**
     * 分页查询
     *
     * @param sysCourse
     * @param queryPage
     * @return
     */
    @PostMapping("/anon/list")
    public R list(@RequestBody SysCourse sysCourse, QueryPage queryPage) {

        return new R<>(super.getData(courseService.list(sysCourse, queryPage)));
    }

    /**
     * 获取首页课程标签列表
     *
     * @return
     */
    @GetMapping("/anon/nav")
    public R getHomeNav() {
        List<Object> list = new ArrayList<>();
        for (String item : CommonConstant.COURSETAGTITLES) {
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> d = new HashMap<>();
            Map<String, Object> t = new HashMap<>();
            t.put("list", courseService.findTagByTagTitle(item));
            t.put("subtitle", item);
            d.put("tags", t);
            d.put("course", courseService.limitFindByTagTitle(4, item));
            data.put("data", d);
            data.put("title", item);
            list.add(data);
        }
        return new R<>(list);
    }

    /**
     * 获取首页课程推荐列表
     *
     * @return
     */
    @GetMapping("/anon/rank")
    public R getHomeRank() {
        Map<String, Object> map = new HashMap<>();
        for (String rank : CommonConstant.RANKS) {
            map.put(rank, courseService.findCourseByRank(rank));
        }
        return new R<>(map);
    }

    /**
     * 入门课程页面
     * 分类条件查询的课程列表
     */
    @GetMapping("/anon/list")
    public R getCourseByFilter(@RequestParam(required = false) String tag,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String rank,
                               QueryPage queryPage) {
        IPage<SysCourse> page = courseService.getCourseByFilter(tag, title, rank, queryPage);
        return new R<>(super.getData(page));
    }


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(courseService.getById(id));
    }

    /**
     * 更新
     *
     * @return
     */
    @PutMapping
    public R update(@RequestBody SysCourse sysCourse) {
        courseService.update(sysCourse);
        return new R<>();
    }

    /**
     * 添加
     */
    @PostMapping
    public R add(@RequestBody SysCourse sysCourse) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthUser) {
            AuthUser authUser = (AuthUser) principal;
            SysUser user = userService.findByName(authUser.getUsername());
            sysCourse.setAuthor(user.getUsername());
            sysCourse.setUid(user.getId());
            sysCourse.setAvatar(user.getAvatar());
            sysCourse.setJob(user.getJob());
            sysCourse.setCreateTime(new Date());
        }
        courseService.add(sysCourse);
        return new R<>();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        courseService.delete(id);
        return new R();
    }

}
