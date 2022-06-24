package com.example.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller层公共方法提取
 */
public class BaseController {

    public Map<String, Object> getData(IPage<?> page) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }

}
