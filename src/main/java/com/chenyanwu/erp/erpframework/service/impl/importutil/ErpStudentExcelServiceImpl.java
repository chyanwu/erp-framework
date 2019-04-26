package com.chenyanwu.erp.erpframework.service.impl.importutil;

import com.chenyanwu.erp.erpframework.entity.importutil.ErpStudentExcel;
import com.chenyanwu.erp.erpframework.dao.importutil.ErpStudentExcelMapper;
import com.chenyanwu.erp.erpframework.service.importutil.ErpStudentExcelService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author chenyanwu
* @date 2019-04-26 15:26:52
* @version
*/
@Service
public class ErpStudentExcelServiceImpl extends BaseServiceImpl<ErpStudentExcel, Object>
    implements ErpStudentExcelService {

@Autowired
private ErpStudentExcelMapper erpstudentexcelMapper;

@Autowired
public void setMapper() {
super.setMapper(erpstudentexcelMapper);
}
}
