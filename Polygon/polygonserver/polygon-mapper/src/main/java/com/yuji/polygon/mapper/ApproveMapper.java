package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Approve;
import com.yuji.polygon.entity.ApproveVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @interface: AuditMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:09:00
 */

@Mapper
public interface ApproveMapper {

    int insertApprove(Approve approve);

    int updateApprove(Approve approve);

    ApproveVO getApproveByApproveNo(String approveNo);

    List<ApproveVO> getApproveByFlowNo(String flowNo);

    int deleteApproveByBusinessNo(int businessNo);
}
