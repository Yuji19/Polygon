package com.yuji.polygon.util;

import com.yuji.polygon.entity.APIException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @className: FileUtils
 * @description: 文件操作工具类
 * @author: yuji
 * @create: 2020-08-16 16:07
 **/

public class FileUtil {

    private static volatile FileUtil fileUtil = null;

    //单例
    public static FileUtil getInstance(){
        if (fileUtil == null){
            synchronized (FileUtil.class){
                if (fileUtil == null){
                    fileUtil = new FileUtil();
                }
            }
        }
        return fileUtil;
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

    public void download(String path, HttpServletResponse response) {
        File file = new File(path);
        String fileName = file.getName();
        if (file.exists()){
            //配置响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType("appliction/octet-stream");
            try {
                response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new APIException("文件下载失败");
            }

            //实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1){
                    os.write(buffer,0,i);
                    //读下一个长度的内容
                    i = bis.read(buffer);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new APIException("文件下载失败");
            } catch (IOException e) {
                e.printStackTrace();
                throw new APIException("文件下载失败");
            }finally {
                if (bis != null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            throw new APIException("文件不存在");
        }
    }
}
