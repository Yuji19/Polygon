package com.yuji.polygon.service;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.ResultVO;

/**
 * @interface: AuditService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:17:00
 */
public interface AuditService {

    ResultVO insertAudit(Audit audit);
}
