package com.yuji.polygon.service;

import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.mapper.ArchiveMapper;

import com.yuji.polygon.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @className: ArchiveService
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-11 11:40
 */

@Service
public class ArchiveService {

    @Autowired
    ArchiveMapper archiveMapper;

    @Value("${custom.path}")
    String customPath;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public int insertArchive(Archive archive, MultipartFile file) {
        Archive oldArchive = archiveMapper.getArchiveByFileNo(archive.getFileNo());
        Optional.ofNullable(oldArchive)
                .ifPresent(o -> {
                    throw new APIException("文档编号已存在");
                });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(archive.getIssueDate());
        //保存文件
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String fileType = archive.getFileType();
        String fileNo = archive.getFileNo();
        String fileName = archive.getFileName();
        String path = customPath + "/" + year + "/" + fileType + "/" + fileNo + "/" + fileName;
        String newPath = FileUtil.getInstance().save(path, file);

        if (newPath != null) {
            //插入记录
            archive.setFilePath(newPath);
            String oldFileNo = archive.getOldFileNo();
            oldArchive = null;
            //旧版文件编号不为空，查找是否存在旧版记录
            boolean isnull = "".equals(oldFileNo);
            if (!isnull) {
                oldArchive = archiveMapper.getArchiveByFileNo(oldFileNo);
            }
            //存在旧版记录证明是换版
            if (oldArchive != null) {

                //换版 更新旧版记录，受控转为替代报废
                oldArchive.setStatus("替代报废");

                //更新旧版
                updateArchive(oldArchive);

            }
            //不存在该旧版编号
            if (!isnull && oldArchive == null) {
                return 0;
            }
        }
        if (Optional.ofNullable(archiveMapper.insertArchive(archive)).isPresent()) {
            return 1;
        } else {
            throw new APIException("添加文档失败");
        }

    }


    public void downloadArchive(String fileNo, HttpServletResponse response) {
        Optional<Archive> optional = Optional.ofNullable(archiveMapper.getArchiveByFileNo(fileNo));
        if (!optional.isPresent()) {
            throw new APIException("文档不存在");
        }
        FileUtil.getInstance().download(optional.get().getFilePath(), response);

    }

    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'ArchiveService.getAllArchive_'+#achive+#pageNum+#pageSize")
    public Page<Archive> getAllArchive(Archive archive, Integer pageNum, Integer pageSize) {
        int startIndex = (pageNum-1)*pageSize;
        //总记录数
        int totalCount = archiveMapper.countTotal(archive);
        //结果集
        List<Archive> records = archiveMapper.listArchives(archive,startIndex,pageSize);

        return new Page(pageNum, totalCount, records);
    }


    public int updateArchive(Archive archive) {

        //若发放日期年份、文档类型更改
        Archive oldArchive = archiveMapper.getArchiveById(archive.getId());
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTime(oldArchive.getIssueDate());
        String oldYear = String.valueOf(oldCalendar.get(Calendar.YEAR));

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(archive.getIssueDate());
        String newYear = String.valueOf(newCalendar.get(Calendar.YEAR));
        String oldType = oldArchive.getFileType();
        String newType = archive.getFileType();
        String fileNo_original = oldArchive.getFileNo();
        String fileNo = archive.getFileNo();
        String fileName_original = oldArchive.getFileName();
        String fileName = archive.getFileName();
        String status = archive.getStatus();
        if (!oldYear.equals(newYear) || !oldType.equals(newType) ||
                !fileName_original.equals(fileName) ||
                !fileNo_original.equals(fileNo) || "替代报废".equals(status)) {
            String oldPath = oldArchive.getFilePath();
            String originalFilename = oldPath.substring(oldPath.lastIndexOf("/") + 1);

            //文件路径更改
            String newPath = customPath + newYear + "/" + newType + "/" + originalFilename;
            boolean repetition = !fileName_original.equals(fileName) || !fileNo_original.equals(fileNo);
            boolean result = false;
            if ("替代报废".equals(status)) {
                newPath = customPath + newYear + "/替代报废/" + originalFilename;
                //替代报废，即使新版文档名称与旧版不一样，也不修改旧版的名称
                archive.setFileName(oldArchive.getFileName());
                result = FileUtil.getInstance().moveFolder(oldPath, newPath);
            } else {
                //文档编号或文档名称更改，重命名文件
                if (repetition) {
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String newName = fileNo + " " + fileName;
                    newPath = customPath + "/" + newYear + "/" + newType + "/" + newName + suffix;
                    result = FileUtil.getInstance().renameFile(oldPath, newName);
                } else {

                    result = FileUtil.getInstance().copyFile(oldPath, newPath);
                }
            }

            if (result) {
                archive.setFilePath(newPath);
            } else {
                throw new APIException("文件操作失败");
            }


        }
        //update操作返回受影响的行数
        return archiveMapper.updateArchive(archive);
    }


    @CacheEvict(allEntries = true)
    public int deleteArchive(Long[] aids) {
        return archiveMapper.deleteArchiveById(aids);
    }
}
