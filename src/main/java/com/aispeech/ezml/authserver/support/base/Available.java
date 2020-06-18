package com.aispeech.ezml.authserver.support.base;

/**
 * 获取某个具体对象接口
 *
 * @author ZhangXi
 */
public interface Available<T> {

    /**
     * 获取具体对象
     * @return T
     */
    T get();

}
