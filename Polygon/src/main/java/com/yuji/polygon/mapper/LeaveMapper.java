package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Leave;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: LeaveMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 18:52:00
 */

@Mapper
public interface LeaveMapper {

    int insertLeave(Leave leave);
}
