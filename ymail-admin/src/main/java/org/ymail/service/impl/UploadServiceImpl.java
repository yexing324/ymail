package org.ymail.service.impl;

import org.springframework.stereotype.Service;
import org.ymail.service.UploadService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadServiceImpl implements UploadService {
    private String imgAddress="D:\\upload\\img\\";
    private String fileAddress="D:\\upload\\file\\";
    @Override
    public byte[] getImg(String id) {
        String path = imgAddress + id + ".jpg";
        File file = new File(path);
        System.out.println(path);
        if (!file.isFile()) {
            file = new File(imgAddress + "default_img.jpg");
        }
        try {
            InputStream in = Files.newInputStream(file.toPath());
            byte[] buf = new byte[(int) file.length()];
            int len = in.read(buf);
            in.close();
            return buf;
        } catch (Exception e) {
            System.out.println("图片读写异常");
            return new byte[0];
        }
    }




    @Override
    public void upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File localFile = new File(imgAddress + fileName);
        try {
            file.transferTo(localFile);
        } catch (Exception e) {
            System.out.println("触发图片读写异常");
            e.printStackTrace();
        }
    }

    @Override
    public void uploadAttach(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File localFile = new File(fileAddress + fileName);
        try {
            file.transferTo(localFile);
        } catch (Exception e) {
            System.out.println("触发文件读写异常");
            e.printStackTrace();
        }
    }
}
