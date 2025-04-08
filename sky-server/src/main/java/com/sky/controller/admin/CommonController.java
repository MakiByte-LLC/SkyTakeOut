package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common/")
@Slf4j
public class CommonController {
    private final AliOssUtil aliOssUtil;

    public CommonController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * upload file
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("upload file:{}",file);
        try {
            //original file name
            String originalFilename = file.getOriginalFilename();
            //extract the suffix
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;
            String filePath=aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        }
        catch (IOException e) {
            log.error("upload file error:{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
