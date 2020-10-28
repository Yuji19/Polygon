package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.FlowNodeMapper;
import com.yuji.polygon.service.FlowNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowNodeServiceImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:42:00
 */

@Service
public class FlowNodeServiceImpl implements FlowNodeService {

    @Autowired
    FlowNodeMapper flowNodeMapper;

    @Override
    public ResultVO insertFlowNode(FlowNode flowNode) {
        return (flowNodeMapper.insertFlowNode(flowNode) > 0 ? new ResultVO("添加成功") : new ResultVO(ResultCode.FAILED,"添加失败"));
    }
}
