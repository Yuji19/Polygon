package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Operation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface: OperationMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 16:32:00
 */

@Mapper
public interface OperationMapper {

    int insertOperation(Operation operation);

    int deleteOperationById(int id);

    List<Operation> getAllOperation();

    List<Operation> getOperationByRid(int rid);

    List<Operation> getOperationByRoleNames(@Param("roleNames") String[] roleNames);
}
