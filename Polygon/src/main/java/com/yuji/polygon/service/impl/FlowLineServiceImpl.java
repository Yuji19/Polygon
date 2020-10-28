package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.FlowLine;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.mapper.FlowLineMapper;
import com.yuji.polygon.service.FlowLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: FlowLineServiceImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:51:00
 */

@Service
public class FlowLineServiceImpl implements FlowLineService {

    @Autowired
    FlowLineMapper flowLineMapper;

    @Override
    public ResultVO insertFlowLine(FlowLine flowLine) {
        return (flowLineMapper.insertFlowLine(flowLine) > 0 ? new ResultVO("添加成功") : new ResultVO(ResultCode.FAILED,"添加失败"));
    }
}
