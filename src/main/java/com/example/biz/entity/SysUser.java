package com.example.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "tb_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String telephone;

    private String sex;

    private String avatar;

    private String job;

    private String identity;

    public SysUser(Long id, String username, String password, String telephone, String sex, String avatar, String job, String identity) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.sex = sex;
        this.avatar = avatar;
        this.job = job;
        this.identity = identity;
    }
}
