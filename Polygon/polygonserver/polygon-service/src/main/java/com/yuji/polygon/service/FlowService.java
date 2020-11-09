package com.yuji.polygon.service;

import com.yuji.polygon.entity.Flow;
import com.yuji.polygon.mapper.FlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:30:00
 */

@Service
public class FlowService {

    @Autowired
    FlowMapper flowMapper;

    public int insertFlow(Flow flow) {
        return flowMapper.insertFlow(flow);
    }

    public int deleteFlowByFlowNo(String flowNo) {
        return flowMapper.deleteFlowByFlowNo(flowNo);
    }
}
