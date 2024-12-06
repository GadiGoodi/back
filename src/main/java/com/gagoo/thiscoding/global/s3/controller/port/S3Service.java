package com.gagoo.thiscoding.global.s3.controller.port;

import com.gagoo.thiscoding.global.s3.domain.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    Images uploadImage(MultipartFile image, String path) throws IOException;
}
