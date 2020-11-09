package com.yuji.polygon.service;

import com.yuji.polygon.entity.Audit;
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


    public int insertAudit(Audit audit) {
        return auditMapper.insertAudit(audit);
    }


    public int updateAudit(Audit audit) {
        return auditMapper.updateAudit(audit);
    }


    public Audit findAuditByEmpolyeeNo(String employeeNo) {
        return auditMapper.findAuditByEmployeeNo(employeeNo);
    }

    public int deleteAuditByBusinessNo(int businessNo) {
        return auditMapper.deleteAuditByBusinessNo(businessNo);
    }
}
