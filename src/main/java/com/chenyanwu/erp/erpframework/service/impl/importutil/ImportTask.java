package com.chenyanwu.erp.erpframework.service.impl.importutil;

import com.chenyanwu.erp.erpframework.common.Constants;
import com.chenyanwu.erp.erpframework.common.util.SpringContextHolder;
import com.chenyanwu.erp.erpframework.service.importutil.ImportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @Auther: chenyanwu
 * @Date: 2019/4/28 12:23
 * @Description:
 * @Version 1.0
 */
@Component
public class ImportTask extends RecursiveTask<Integer> {

    @Autowired
    private ImportTemplate importTemplate;

    private List list;
    private int fromIndex;
    private int toIndex;

    public ImportTask() {

    }

    public ImportTask(List list, int fromIndex, int toIndex) {
        this.list = list;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        if(toIndex - fromIndex < Constants.THRESHOLD) {
            if(importTemplate == null) {
                importTemplate = SpringContextHolder.getBean("erpStudentServiceImpl");
            }
            return (Integer) importTemplate.importDataReturn(list, fromIndex, toIndex);
        } else {
            int mid = (fromIndex + toIndex) / 2;
            ImportTask left = new ImportTask(list, fromIndex, mid);
            ImportTask right = new ImportTask(list, mid+1, toIndex);
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }
}
