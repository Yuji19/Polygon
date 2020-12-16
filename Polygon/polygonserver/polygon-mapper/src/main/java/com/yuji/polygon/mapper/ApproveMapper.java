package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Approve;
import org.apache.ibatis.annotations.Mapper;

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

    Approve findApproveByApproveNo(String approveNo);

    int deleteApproveByBusinessNo(int businessNo);
}
