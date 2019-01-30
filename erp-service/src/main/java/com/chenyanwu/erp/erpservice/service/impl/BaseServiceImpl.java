package com.chenyanwu.erp.erpservice.service.impl;

import com.chenyanwu.erp.erpservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName BaseServiceImpl
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 15:33
 * @Version 1.0
 */
public class BaseServiceImpl<T, Id> implements BaseService<T, Id> {

    protected Mapper<T> mapper;

    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    @Transactional
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Id id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public T selectByPrimaryKey(Id id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
