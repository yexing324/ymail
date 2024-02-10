package org.ymail.controller;

import com.alibaba.fastjson.JSON;
import io.micrometer.core.instrument.binder.BaseUnits;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ymail.entity.UploadResp;
import org.ymail.service.UploadService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/upload")
public class UploadController {
    private final UploadService uploadService;

    @GetMapping(value = "/getImg",produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getImg(String id) throws IOException {
            return ImageIO.read(new FileInputStream(new File("D:\\img\\"+id)));
//        byte[] img = uploadService.getImg(id);

    }

    @PostMapping("/setImg")
    public UploadResp upload(@RequestParam("file") MultipartFile file) throws IOException {
//        byte[] bytes = file.getBytes();
//        return uploadResp;
        uploadService.upload(file);
        UploadResp uploadResp = new UploadResp();
        uploadResp.setUrl("http://localhost/ymail/upload/getImg?id="+file.getOriginalFilename());
        return uploadResp;
    }
}
