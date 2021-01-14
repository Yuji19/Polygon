package com.yuji.polygon.service;

import com.yuji.polygon.entity.Operation;
import com.yuji.polygon.mapper.OperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: OperationService
 * @description: TODO
 * @author: yuji
 * @create: 2020-12-15 17:04:18
 */

@Service
@CacheConfig(cacheNames = "operation_cache")
public class OperationService {

    @Autowired
    OperationMapper operationMapper;

    public int insertOperation(Operation operation){
        return operationMapper.insertOperation(operation);
    }

    public int deleteOperationById(int id){
        return operationMapper.deleteOperationById(id);
    }

    @Cacheable
    public List<Operation> getAllOperation(){
        return operationMapper.getAllOperation();
    }

    public List<Operation> getOperationByRid(int rid){
        return operationMapper.getOperationByRid(rid);
    }

    @Cacheable
    public List<Operation> getOperationByRoleNames(String[] roleNames) {
        return  operationMapper.getOperationByRoleNames(roleNames);
    }
}
