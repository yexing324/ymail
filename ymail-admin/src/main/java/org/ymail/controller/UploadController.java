package org.ymail.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ymail.entity.UploadResp;
import org.ymail.entity.UserDo;
import org.ymail.filter.UserContext;
import org.ymail.mapper.UserMapper;
import org.ymail.service.UploadService;
import org.ymail.util.CodeContain;
import org.ymail.util.ThreadPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/upload")
public class UploadController {
    private final UploadService uploadService;
    private final UserMapper userMapper;

    @GetMapping(value = "/getImg", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getImg(String id) throws IOException {
        return ImageIO.read(new FileInputStream(new File("D:\\upload\\img\\" + id)));
    }

    @PostMapping("/setImg")
    public UploadResp upload(@RequestParam("file") MultipartFile file) {
        uploadService.upload(file);
        UploadResp uploadResp = new UploadResp();
        uploadResp.setName(file.getOriginalFilename());
        uploadResp.setUrl(CodeContain.uploadAddress + file.getOriginalFilename());
        return uploadResp;
    }

    /**
     * 为什么重新写一个
     * 因为白名单中有setImg，需要从线程上下文中获得用户，而setImg中拿不到
     */

    @PostMapping("/setAvaTarImg")
    public UploadResp setAvaTarImg(@RequestParam("file") MultipartFile file) {
        uploadService.upload(file);

        String mail = UserContext.getUserMail();
        UserDo user = new UserDo();
        user.setAvatarName(file.getOriginalFilename());
        ThreadPool.getThread(() -> {
            userMapper.update(user, new LambdaQueryWrapper<UserDo>()
                    .eq(UserDo::getMail, mail));
        });
        UploadResp uploadResp = new UploadResp();
        uploadResp.setName(file.getOriginalFilename());
        uploadResp.setUrl(CodeContain.uploadAddress + file.getOriginalFilename());
        return uploadResp;
    }

    @PostMapping("/attach")
    public UploadResp uploadAttach(@RequestParam("file") MultipartFile file) {
        uploadService.uploadAttach(file);
        UploadResp uploadResp = new UploadResp();
        uploadResp.setName(file.getOriginalFilename());
        uploadResp.setUrl(CodeContain.uploadAddress + file.getOriginalFilename());
        return uploadResp;
    }

}
