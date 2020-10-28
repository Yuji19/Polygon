package com.yuji.polygon.service;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.Leave;
import com.yuji.polygon.entity.ResultVO;

/**
 * @interface: LeaveService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:05:00
 */
public interface LeaveService {

    ResultVO insertLeave(Leave leave, Audit audit);
}
