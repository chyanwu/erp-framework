package com.chenyanwu.erp.erpservice.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 17:09
 * @Version 1.0
 */
public interface BaseService<T, Id> {
    public int insertSelective(T t);

    public int delete(T t);

    public int deleteByPrimaryKey(Id id);

    public int updateByPrimaryKey(T t);

    public T selectByPrimaryKey(Id id);

    public List<T> selectAll();
}
