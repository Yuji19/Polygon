package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.mapper.FlowNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowNodeService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:42:00
 */

@Service
public class FlowNodeService {

    @Autowired
    FlowNodeMapper flowNodeMapper;

    public int insertFlowNode(FlowNode flowNode) {
        return flowNodeMapper.insertFlowNode(flowNode);
    }

    public FlowNode getFlowNodeById(int id) {
        return flowNodeMapper.getFlowNodeById(id);
    }

    public FlowNode getFlowNodeByFlowNoAndFlowNodeName(String flowNo, String flowNodeName){
        return flowNodeMapper.getFlowNodeByFlowNoAndFlowNodeName(flowNo,flowNodeName);
    }

    public int deleteFlowNodeByFlowNo(String flowNo) {
        return flowNodeMapper.deleteFlowNodeByFlowNo(flowNo);
    }
}
