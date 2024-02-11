package org.ymail.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    byte[] getImg(String id);
    void upload(MultipartFile file);
    void uploadAttach(MultipartFile file);
}
