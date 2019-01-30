package com.chenyanwu.erp.erpdao.dao.erp;

import java.util.List;
import com.chenyanwu.erp.erpdao.entity.erp.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
* <p>
    * </p>
*
* @author Ken
* @date 2019-01-15 19:17:41
* @version
*/
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User>,InsertListMapper<User> {


}