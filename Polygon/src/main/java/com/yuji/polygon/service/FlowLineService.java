package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowLine;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
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
public class FlowLineService  {

    @Autowired
    FlowLineMapper flowLineMapper;

    public ResultVO insertFlowLine(FlowLine flowLine) {
        return (flowLineMapper.insertFlowLine(flowLine) > 0 ? new ResultVO("创建流程线成功") : new ResultVO(ResultCode.FAILED,"创建流程线失败"));
    }

    public ResultVO<FlowLine> findFlowLineByPreNode(int preNode){
        return new ResultVO<>(flowLineMapper.findFlowLineByPreNode(preNode));
    }
}
