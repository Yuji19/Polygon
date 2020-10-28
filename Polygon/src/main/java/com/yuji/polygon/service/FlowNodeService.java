package com.yuji.polygon.service;

import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.ResultVO;

/**
 * @interface: FlowNodeService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:41:00
 */


public interface FlowNodeService {

    ResultVO insertFlowNode(FlowNode flowNode);
}
