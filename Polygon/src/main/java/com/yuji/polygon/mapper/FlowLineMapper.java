package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.FlowLine;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: FlowLineMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:47:00
 */

@Mapper
public interface FlowLineMapper {

    int insertFlowLine(FlowLine flowLine);

    FlowLine findFlowLineByPreNode(int preNode);
}
