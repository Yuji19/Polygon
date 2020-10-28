package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowLine;
import com.yuji.polygon.entity.ResultVO;

/**
 * @interface: FlowLineService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:51:00
 */
public interface FlowLineService {

    ResultVO insertFlowLine(FlowLine flowLine);
}
