package com.example.biz.controller;

import com.example.biz.entity.SysArticle;
import com.example.biz.entity.SysUser;
import com.example.biz.service.ArticleService;
import com.example.biz.service.UserService;
import com.example.common.config.AuthUser;
import com.example.common.controller.BaseController;
import com.example.common.utils.QueryPage;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/api/article")
@RestController
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 查询所有
     *  R类型是统一返回值格式
     * @return
     */
    @GetMapping("/anon/all")
    public R getAll() {
        return new R<>(articleService.list());
    }

    /**
     * 查询最新的10条数据
     * @return
     */
    @GetMapping("/anon/new")
    public R getNew() {
        return new R<>(articleService.getNew());
    }

    /**
     * 分页查询
     */
    @PostMapping("/anon/list")
    public R list(@RequestBody SysArticle sysArticle, QueryPage queryPage) {
        return new R<>(super.getData(articleService.list(sysArticle, queryPage)));
    }

    /**
     * 根据ID查询  首先PathVariable接收传过来的id
     * 需要验证登录身份
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(articleService.getById(id));
    }

    /**
     * 更新
     * @return
     */
    @PutMapping
    public R update(@RequestBody SysArticle sysArticle) {
        articleService.update(sysArticle);
        return new R<>();
    }

    /**
     * 文章新增
     * 首先获取已登录用户信息
     * Security中一旦用户登录系统，用户的数据就存储在SecurityContextHolder这个对象中，
     * 这个判断就是，从SecurityContextHolder对象中获取当前用户的信息，
     * 判断获取的Object对象是不是User对象，如果是了也就可以getter 获取对象中的数据，
     * 然后设置到文章Article对象中
     * instanceof用来测试一个对象是否为一个类的实例
     */
    @PostMapping
    public R add(@RequestBody SysArticle sysArticle) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthUser) {
            AuthUser authUser = (AuthUser) principal;
            SysUser user = userService.findByName(authUser.getUsername());
            sysArticle.setAuthor(user.getUsername());  //get取出
            sysArticle.setUid(user.getId());
            sysArticle.setAvatar(user.getAvatar());
            sysArticle.setJob(user.getJob());
            sysArticle.setCreateTime(new Date());
        }
        articleService.add(sysArticle);
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
        articleService.delete(id);
        return new R();
    }
}
