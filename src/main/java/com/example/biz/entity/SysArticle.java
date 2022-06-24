package com.example.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "tb_article")
//使用通用mapper必须在实体上告诉是那个表
public class SysArticle implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    private String author;

    private String avatar;

    private String job;

    private String title;

    private String des;

    private String content;

    @TableField(value = "content_md")
    private String contentMd;

    private String tags;

    private String img;

    private String type;

    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
