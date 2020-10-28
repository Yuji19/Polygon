package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.Leave;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.LeaveMapper;
import com.yuji.polygon.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: LeaveSericeImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:08:00
 */

@Service
public class LeaveSericeImpl implements LeaveService {

    @Autowired
    LeaveMapper leaveMapper;


    @Override
    public ResultVO insertLeave(Leave leave, Audit audit) {
        return null;
    }
}
