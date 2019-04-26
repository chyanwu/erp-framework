package com.chenyanwu.erp.erpframework.service.importutil;

import java.util.List;

/**
 * @Auther: chenyanwu
 * @Date: 2019/4/26 10:57
 * @Description: 使用模板模型，定义导入的通用类
 * @Version 1.0
 */
public interface ImportTemplate<T, E> {
    /**
     * 导入功能需要返回结果，例如校验数据后，返回
     * @param list
     * @return
     */
    T importDataReturn(List<E> list);

    /**
     * 导入功能不需要返回结果
     * @param list
     */
    void importDataNoReturn(List<E> list);
}
