package com.example;

import com.alibaba.fastjson.JSONObject;
import com.example.biz.entity.SysComment;

public class TestMain {
    public static void main(String[] args) {
       // SysUser sysUser = new SysUser((long)100001,"test","123456","123456789","nan","","","");
        SysComment sysComment = new SysComment();
        sysComment.setAuthor("null");
        sysComment.setAvatar("null");
        sysComment.setContent("null");
        sysComment.setCreateTime(null);
        sysComment.setUid(0L);
        sysComment.setId(0L);
        sysComment.setRname("null");
        sysComment.setTid(0L);
        sysComment.setType(null);
        String jsonStr= JSONObject.toJSONString(sysComment);
        System.out.println(jsonStr);
    }
}
