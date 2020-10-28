package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.AuditMapper;
import com.yuji.polygon.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: AuditServieImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:18:00
 */

@Service
public class AuditServieImpl implements AuditService {

    @Autowired
    AuditMapper auditMapper;

    @Override
    public ResultVO insertAudit(Audit audit) {
        return (auditMapper.insertAudit(audit) > 0 ? new ResultVO("添加成功") : new ResultVO(ResultCode.FAILED,"添加失败"));
    }
}
