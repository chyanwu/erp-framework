package com.chenyanwu.erp.erpframework.service.importutil;

import com.chenyanwu.erp.erpframework.entity.importutil.ErpStudent;
import com.chenyanwu.erp.erpframework.entity.importutil.StudentExcelImport;
import com.chenyanwu.erp.erpframework.service.BaseService;

import java.util.List;

/**
* <p>
    *  服务类
    * </p>
*
* @author chenyanwu
* @date 2019-04-26 12:37:18
* @version
*/
public interface ErpStudentService extends BaseService<ErpStudent, Object>{
    List<ErpStudent> selectByExample(Object var1);

    List<StudentExcelImport> findStudentErrorExcel();

    int importDataByForkJoin(List<StudentExcelImport> list);
}
