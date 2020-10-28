package com.yuji.polygon.service;

import com.yuji.polygon.entity.Flow;
import com.yuji.polygon.entity.ResultVO;

/**
 * @interface: FlowService
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:30:00
 */

public interface FlowService {
    ResultVO insertFlow(Flow flow);
}
