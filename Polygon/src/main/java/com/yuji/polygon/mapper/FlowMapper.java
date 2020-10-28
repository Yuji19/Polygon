package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Flow;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: FlowMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:29:00
 */

@Mapper
public interface FlowMapper {

    int insertFlow(Flow flow);
}
