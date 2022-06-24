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
@TableName(value = "tb_course")
public class SysCourse implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    private String author;

    private String avatar;

    private String job;

    @TableField(value = "video_url")
    private String videoUrl;

    @TableField(value = "video_type")
    private String videoType;

    private String title;

    private String img;

    private String des;

    @TableField(value = "tag_title")
    private String tagTitle;

    private String tags;

    @TableField(value = "`rank`")
    private String rank;

    private String type;

    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
