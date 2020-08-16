package com.yuji.polygon.util;

import com.yuji.polygon.entity.APIException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @className: FileUtils
 * @description: 文件操作工具类
 * @author: yuji
 * @create: 2020-08-16 16:07
 **/

public class FileUtils {

    private static volatile FileUtils fileUtils = null;

    //单例
    public static FileUtils getInstance(){
        if (fileUtils == null){
            synchronized (FileUtils.class){
                if (fileUtils == null){
                    fileUtils = new FileUtils();
                }
            }
        }
        return fileUtils;
    }

    public String save(String path, MultipartFile file){

        //获取文件名称（包括后缀）
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        path+=suffix;
        File newFile = new File(path);
        if (!newFile.getParentFile().exists()){
            //创建目录
            newFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(newFile);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException("文件保存失败");
        }
    }
}
