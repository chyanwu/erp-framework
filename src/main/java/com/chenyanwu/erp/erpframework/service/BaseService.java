package com.chenyanwu.erp.erpframework.service;

import java.util.List;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 17:09
 * @Version 1.0
 */
public interface BaseService<T, Id> {
    int insertSelective(T t);

    int delete(T t);

    int deleteByPrimaryKey(Id id);

    int deleteByExample(Object o);

    int updateByPrimaryKey(T t);

    int updateByPrimaryKeySelective(T t);

    T selectByPrimaryKey(Id id);

    List<T> selectAll();
}
