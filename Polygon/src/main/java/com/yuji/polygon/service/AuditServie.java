package com.yuji.polygon.service;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.AuditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: AuditServie
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:18:00
 */

@Service
public class AuditServie {

    @Autowired
    AuditMapper auditMapper;


    public ResultVO insertAudit(Audit audit) {
        return (auditMapper.insertAudit(audit) > 0 ? new ResultVO("创建审批记录成功") : new ResultVO(ResultCode.FAILED,"创建审批记录失败"));
    }


    public ResultVO updateAudit(Audit audit) {
        return (auditMapper.updateAudit(audit) > 0 ? new ResultVO("更新审批记录成功") : new ResultVO(ResultCode.FAILED,"更新审批记录成功"));
    }


    public ResultVO<Audit> findAuditByEmpolyeeNo(String employeeNo) {
        Audit audit = auditMapper.findAuditByEmployeeNo(employeeNo);
        return new ResultVO<Audit>(audit);
    }
}
