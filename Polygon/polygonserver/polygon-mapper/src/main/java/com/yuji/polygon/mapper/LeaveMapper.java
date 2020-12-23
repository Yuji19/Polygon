package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.FlowVO;
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

    int deleteLeaveById(int id);

    int countTotalLeaveFlow(@Param("flowNo") String flowNo, @Param("approveNo") String approveNo);

    List<FlowVO> getLeaveFlowPage(@Param("flowNo") String flowNO, @Param("approveNo") String approveNo,
                               @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int countTotalMineLeaveFlow(@Param("flowNo") String flowNo, @Param("employeeNo") String employeeNo);

    List<FlowVO> getMineLeaveFlowPage(@Param("flowNo") String flowNO, @Param("employeeNo") String employeeNo,
                                  @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

}
