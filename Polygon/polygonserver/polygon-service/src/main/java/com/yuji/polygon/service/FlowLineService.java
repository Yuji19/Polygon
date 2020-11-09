package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowLine;
import com.yuji.polygon.mapper.FlowLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowLineService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:51:00
 */

@Service
public class FlowLineService {

    @Autowired
    FlowLineMapper flowLineMapper;

    public int insertFlowLine(FlowLine flowLine) {
        return flowLineMapper.insertFlowLine(flowLine);
    }

    public FlowLine findFlowLineByPreNode(int preNode) {
        return flowLineMapper.findFlowLineByPreNode(preNode);
    }

    public int deleteFlowLineByFlowNo(String flowNo) {
        return flowLineMapper.deleteFlowLineByFlowNo(flowNo);
    }
}
