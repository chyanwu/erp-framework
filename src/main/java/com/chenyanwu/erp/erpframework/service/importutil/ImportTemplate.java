package com.chenyanwu.erp.erpframework.service.importutil;

import java.util.List;

/**
 * @Auther: chenyanwu
 * @Date: 2019/4/26 10:57
 * @Description: 使用模板模型，定义导入的通用类
 * @Version 1.0
 */
public abstract class ImportTemplate<T> {
    /**
     * 导入功能需要返回结果，例如校验数据后，返回
     * @param list
     * @return
     */
    public Object importDataReturn(List<T> list) {
        return new Object();
    }

    /**
     * 导入功能不需要返回结果
     * @param list
     */
    public void importDataNoReturn(List<T> list) {

    }
}
