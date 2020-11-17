package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Archive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @calssName: ArchiveMapper
 * @description: 数据库操作
 * @author: yuji
 * @create: 2020-08-11 10:53
 */

@Mapper
public interface ArchiveMapper {
    int insertArchive(Archive archive);

    int countTotal(Archive archive);

    List<Archive> listArchives(Map<String, Object> map);

    int updateArchive(Archive archive);

    int deleteArchiveById(@Param("aids") Long[] aids);

    Archive getArchiveById(Integer id);

    Archive getArchiveByFileNo(String fileNo);
}
