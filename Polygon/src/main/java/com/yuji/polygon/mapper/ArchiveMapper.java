package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Archive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName ArchiveMapper
 * @Description 数据库操作
 * @Author yuji
 * @Date 2020-08-11 10:53
 * @Version 1.0
 */

@Mapper
public interface ArchiveMapper {
    int insertArchive(Archive archive);

    int countTotal(Map<String, Object> map);

    List<Archive> listArchive(Map<String, Object> map);

    int updateArchive(Archive archive);

    int deleteArchiveById(@Param("aids") Long[] aids);

    Archive findArchiveById(Integer id);

    Archive findArchiveByFileNo(String fileNo);
}
