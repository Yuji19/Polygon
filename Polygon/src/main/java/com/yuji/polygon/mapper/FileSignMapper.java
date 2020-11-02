package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.FileSign;
import org.apache.ibatis.annotations.Mapper;

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

    FileSign findFileSignById(int id);
}
