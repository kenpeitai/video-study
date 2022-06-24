package com.example.common.constants;

/**
 * 项目公共常量数据
 */
public interface CommonConstant {

    /**
     * 前后端分离，前端携带的Token标识
     */
    String AUTHORIZATION = "Authorization";

    String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    String NO_LOGIN = "用户未登录";
    String LOGIN_ERROR = "登录失败";
    String LOGIN_SUCCESS = "登录成功";

    /**
     * 成功标记
     */
    int SUCCESS = 200;

    /**
     * 错误标记
     */
    int ERROR = 500;

    /**
     * UTF-8编码
     */
    String UTF8 = "UTF-8";

    /**
     * 文章已发布状态标识：1
     */
    String DEFAULT_RELEASE_STATUS = "1";

    /**
     * 文章未发布状态标识：0
     */
    String DEFAULT_DRAFT_STATUS = "0";

    /**
     * User_Agent
     */
    String USER_AGENT = "User-Agent";

    String[] COURSETAGTITLES = {
            "前沿 / 区块链 / 人工智能",
            "前端 / 小程序 / JS",
            "后端 / JAVA / Python",
            "移动 / Android / iOS",
            "云计算 / 大数据 / 容器",
            "运维 / 测试 / 数据库",
            "UI设计 / 3D动画 / 游戏"
    };

    String[] RANKS = {
            "实战",
            "初级",
            "中级"
    };
}
