package com.chenyanwu.erp.erpframework.service.impl.importutil;

import com.chenyanwu.erp.erpframework.entity.importutil.ErpSFamilyMember;
import com.chenyanwu.erp.erpframework.dao.importutil.ErpSFamilyMemberMapper;
import com.chenyanwu.erp.erpframework.service.importutil.ErpSFamilyMemberService;
import com.chenyanwu.erp.erpframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenyanwu
 * @date 2019-04-29 11:37:43
 */
@Service
public class ErpSFamilyMemberServiceImpl extends BaseServiceImpl<ErpSFamilyMember, Object>
        implements ErpSFamilyMemberService {

    @Autowired
    private ErpSFamilyMemberMapper erpsfamilymemberMapper;

    @Autowired
    public void setMapper() {
        super.setMapper(erpsfamilymemberMapper);
    }

    @Override
    public List<ErpSFamilyMember> findFamilyMemberByStuId(String stuId) {
        Example example = new Example(ErpSFamilyMember.class);
        example.createCriteria().andEqualTo("studId", stuId);
        return erpsfamilymemberMapper.selectByExample(example);
    }

    @Override
    public int insertList(List<ErpSFamilyMember> list) {
        return erpsfamilymemberMapper.insertList(list);
    }

}
