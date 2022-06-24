package com.example.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class  Tree<T> {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 父节点ID
     */
    private Long pId;

    /**
     * 文章ID
     */
    private Long dId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人
     */
    private String username;

    /**
     * 给谁评论
     */
    private String rname;

    /**
     * 评论时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 所有子级回复、评论列表
     */
    private List<Tree<T>> children = new ArrayList<>();
}
