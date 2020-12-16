package com.yuji.polygon.service;

import com.yuji.polygon.entity.Approve;
import com.yuji.polygon.mapper.ApproveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: AuditServie
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:18:00
 */

@Service
public class ApproveService {

    @Autowired
    ApproveMapper approveMapper;


    public int insertApprove(Approve approve) {
        return approveMapper.insertApprove(approve);
    }


    public int updateApprove(Approve approve) {
        return approveMapper.updateApprove(approve);
    }


    public Approve findApproveByApproveNo(String approveNo) {
        return approveMapper.findApproveByApproveNo(approveNo);
    }

    public int deleteApproveByBusinessNo(int businessNo) {
        return approveMapper.deleteApproveByBusinessNo(businessNo);
    }
}
