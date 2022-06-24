package com.example.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页信息
 * 参数封装到对象里面
 * /前端需要的数据类型是QueryPage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage implements Serializable {

    /**
     * 当前页
     */
        private int page;

    /**
     * 每页的记录数
     */
    private int limit;
}
