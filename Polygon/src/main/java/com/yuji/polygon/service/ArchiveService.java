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
    public ResultVO insertArchive(Archive archive, MultipartFile file);

    public ResultVO downloadArchive(String fileNo, HttpServletResponse response);

    public Page<Archive> ListArchive(Archive archive, Integer pageNum, Integer pageSize);

    public ResultVO updateArchive(Archive archive);

    public ResultVO deleteArchive(Long[] aids);
}
