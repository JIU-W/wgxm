package com.itjn.common.enums;

/**
 * 业务操作类型（AOP+注解操作日志）
 *
 */
public enum BusinessType {

    /**
     * 其他类型
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 查询
     */
    QUERY,

    /**
     * 删除
     */
    DELETE,

    /**
     * 更新状态
     */
    STATUS,

    /**
     * 授权
     */
    ASSIGN,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 注册
     */
    REGISTER,

}
