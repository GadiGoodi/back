package com.gagoo.thiscoding.global.s3.service.port;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gagoo.thiscoding.global.s3.domain.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Bucket {
    Images putObject(MultipartFile image, String fileName, ObjectMetadata objectMetadata) throws IOException;

    ObjectMetadata getObjectMetadata(MultipartFile file);

}
