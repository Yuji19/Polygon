package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Leave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface: LeaveMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 18:52:00
 */

@Mapper
public interface LeaveMapper {

    int insertLeave(Leave leave);

    int updateLeave(Leave leave);

    Leave getLeaveById(int id);

    int countTotal(Leave leave);

    List<Leave> listLeave(@Param("leave") Leave leave, @Param("startIndex") int startIndex,
                          @Param("pageSize") int pageSize);

    int deleteLeaveById(int id);

}
