package com.yuji.polygon.service;

import com.yuji.polygon.entity.Archive;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @className: ArchiveService
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-11 11:29
 */

public interface ArchiveService {
    public String insertArchive(Archive archive, MultipartFile file);

    public String downloadArchive(String fileNo, HttpServletResponse response);
}
