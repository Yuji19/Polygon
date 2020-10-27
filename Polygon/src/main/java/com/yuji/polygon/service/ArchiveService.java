package com.yuji.polygon.service;

import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.entity.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @className: ArchiveService
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-11 11:29
 */

public interface ArchiveService {
    ResultVO insertArchive(Archive archive, MultipartFile file);

    ResultVO downloadArchive(String fileNo, HttpServletResponse response);

    Page<Archive> ListArchive(Archive archive, Integer pageNum, Integer pageSize);

    ResultVO updateArchive(Archive archive);

    ResultVO deleteArchive(Long[] aids);
}
