package com.yuji.polygon.service.utils;

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

    public boolean copyFile(String oldPath, String newPath){


        File newFile = new File(newPath);
        if(!newFile.exists()){
            //创建目录
            newFile.getParentFile().mkdirs();
        }
        boolean flag = false;
        byte[] buffer = new byte[1024];
        FileInputStream oldFis = null;
        BufferedInputStream oldBis = null;
        FileOutputStream newFos = null;
        BufferedOutputStream newBos = null;

        int len = 0;
        try {
            oldFis = new FileInputStream(oldPath);
            oldBis = new BufferedInputStream(oldFis);
            newFos = new FileOutputStream(newPath);
            newBos = new BufferedOutputStream(newFos);

            while ((len = oldBis.read(buffer)) != -1) {
                newBos.write(buffer, 0, len);
            }

            flag = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            if(oldBis != null){
                try {
                    oldBis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(newBos != null){
                try {
                    newBos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }

    public boolean copyFolder(String oldPath, String newPath){
        try {
            System.out.println(newPath);
            File oldFolder = new File(oldPath);
            //新文件夹不存在，创建文件夹
            File newFolder = new File(newPath);
            if(!newFolder.exists()){
                newFolder.mkdirs();
            }
            String[] childFiles = oldFolder.list();

            File temp = null;
            for (int i = 0; i < childFiles.length; i++){
                temp = new File(new File(oldPath),childFiles[i]);
                boolean isCopy;
                //如果是文件夹
                if (temp.isDirectory()){
                    //递归调用，继续遍历
                    isCopy = copyFolder(oldPath + File.separator + childFiles[i], newPath + File.separator + childFiles[i]);
                    if(!isCopy){
                        return false;
                    }
                }

                //如果是文件
                if (temp.isFile()){
                    isCopy = copyFile(oldPath+File.separator+childFiles[i],newPath+File.separator+childFiles[i]);
                    if(!isCopy){
                        return false;
                    }
                }
            }

            return true;
        }catch (Exception e){

            return false;
        }
    }

    public boolean deleteFile(File file){

        if (file.isDirectory()){
            String[] childFiles = file.list();

            for (int i = 0; i < childFiles.length; i++){
                //System.out.println(childFiles[i]);
                boolean isDelete = deleteFile(new File(file,childFiles[i]));
                if (!isDelete){
                    break;
                }
            }

        }
        return file.delete();
    }

    //移动文件或文件夹   file systems不一致也可以使用
    public boolean moveFolder(String oldPath, String newPath){
        File old = new File(oldPath);
        boolean isCopy = false;
        if (old.isFile()){
            isCopy = copyFile(oldPath,newPath);
        }
        if (old.isDirectory()){
            isCopy = copyFolder(oldPath,newPath);
        }

        boolean isDelete = false;
        //保证复制成功后才可以删除源文件
        if (isCopy){
            isDelete = deleteFile(old);
        }
        return isCopy && isDelete;
    }


    /**
     * 重命名
     */
    public boolean renameFile(String oldPath,String newName){
        File oldFile = new File(oldPath);
        if(!oldFile.exists()){
            return false;
        }
        String newPath = oldFile.getParent() + File.separator + newName;
        int index = oldPath.lastIndexOf(".");
        if (index > -1){
            String suffix = oldPath.substring(index);
            newPath+=suffix;
        }
        File newFile = new File(newPath);
        return oldFile.renameTo(newFile);
    }
}
