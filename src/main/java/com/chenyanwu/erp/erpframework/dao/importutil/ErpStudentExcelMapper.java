package com.chenyanwu.erp.erpframework.dao.importutil;

import java.util.List;
import com.chenyanwu.erp.erpframework.entity.importutil.ErpStudentExcel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
* <p>
    * </p>
*
* @author chenyanwu
* @date 2019-04-26 15:26:52
* @version
*/
public interface ErpStudentExcelMapper extends Mapper<ErpStudentExcel>,InsertListMapper<ErpStudentExcel> {

    void batchDel(@Param("list") List<ErpStudentExcel> erpStudentExcels);
}