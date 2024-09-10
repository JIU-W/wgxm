package com.itjn.common.context;

//线程上下文  BaseContext类使用 ThreadLocal来存储当前用户 ID
public class BaseContext {

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }

    public static Integer getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
