package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.FlowNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: FlowNodeMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:29:00
 */

@Mapper
public interface FlowNodeMapper {

    int insertFlowNode(FlowNode flowNode);
}
