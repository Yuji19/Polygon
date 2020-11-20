package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Archive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Archive> listArchives(@Param("archive") Archive archive,
                               @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int updateArchive(Archive archive);

    int deleteArchiveById(@Param("aids") Long[] aids);

    Archive getArchiveById(Integer id);

    Archive getArchiveByFileNo(String fileNo);
}
