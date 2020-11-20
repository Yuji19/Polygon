package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.FileSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @interface: FileSignMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-02 18:40:00
 */

@Mapper
public interface FileSignMapper {

    int insertFileSign(FileSign fileSign);

    int updateFileSign(FileSign fileSign);

    FileSign getFileSignById(int id);

    int countTotal(FileSign fileSign);

    List<FileSign> listFileSign(@Param("fileSIgn") FileSign fileSign,
                                @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int deleteFileSignById(int id);
}
