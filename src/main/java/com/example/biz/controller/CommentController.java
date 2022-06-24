package com.example.biz.controller;

import com.example.biz.entity.SysComment;
import com.example.biz.service.CommentService;
import com.example.common.controller.BaseController;
import com.example.common.exception.GlobalException;
import com.example.common.utils.QueryPage;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/findByTypeId")//data:null
    public R findByTypeId(@RequestParam(required = true) String type,
                          @RequestParam(required = true) String id) {
        return new R<>(commentService.findByTypeId(type, id));
    }

    @PostMapping("/list")  //rows:[]
    public R list(@RequestBody SysComment comment, QueryPage queryPage) {
        return new R<>(super.getData(commentService.list(comment, queryPage)));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(commentService.getById(id));
    }

    /*
    发布评论
     */
    @PostMapping("/anon")
    public R save(@RequestBody SysComment comment) {
        try {
            commentService.add(comment);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    public R update(@RequestBody SysComment comment) {
        try {
            commentService.update(comment);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
    @PostMapping("/update/{id}")
    public R updateById(@PathVariable Long id,@RequestBody SysComment comment){
        try {
            comment.setId(id);
            commentService.updateById(comment);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
