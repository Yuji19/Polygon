package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
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

    public ResultVO insertFlowNode(FlowNode flowNode) {
        return (flowNodeMapper.insertFlowNode(flowNode) > 0 ? new ResultVO("创建流程节点成功") : new ResultVO(ResultCode.FAILED,"创建流程节点失败"));
    }

    public ResultVO<FlowNode> findFlowNode(int id){
        return new ResultVO<>(flowNodeMapper.findFlowNodeById(id));
    }
}
