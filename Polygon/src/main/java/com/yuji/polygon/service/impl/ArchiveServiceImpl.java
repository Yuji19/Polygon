package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.mapper.ArchiveMapper;
import com.yuji.polygon.service.ArchiveService;
import com.yuji.polygon.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @className: ArchiveServiceImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-11 11:40
 */

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArchiveMapper archiveMapper;

    @Value("${custom.path}")
    String customPath;

    public String insertArchive(Archive archive, MultipartFile file){
        Archive oldArchive = archiveMapper.findArchiveByFileNo(archive.getFileNo());
        Optional.ofNullable(oldArchive)
                .ifPresent(o -> {
                    throw new APIException("文档编号已存在");
                });

        //保存文件
        String year = archive.getIssueDate().substring(0,4);
        String fileType = archive.getFileType();
        String fileNo = archive.getFileNo();
        String fileName = archive.getFileName();
        String path =customPath+"/"+year+"/"+fileType+"/"+fileNo+"/"+fileName;
        String newPath = FileUtils.getInstance().save(path,file);
        archive.setFilePath(newPath);
        if(Optional.ofNullable(archiveMapper.insertArchive(archive)).isPresent()){
            return "添加文档成功";
        }else {
            throw new APIException("添加文档失败");
        }

    }
}
