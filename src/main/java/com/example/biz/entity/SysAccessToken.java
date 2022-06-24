package com.example.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_access_token")
public class SysAccessToken {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String token;

    @TableField(value = "user_name")
    private String userName;

    public SysAccessToken() {
    }

    public SysAccessToken(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }
}
