package com.gagoo.thiscoding.global.s3.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gagoo.thiscoding.global.s3.domain.Images;
import com.gagoo.thiscoding.global.s3.service.port.S3Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3BucketImpl implements S3Bucket {

    private final AmazonS3Client amazonS3Client;
    private final S3Properties s3Properties;

    /**
     * 이미지 업로드 후 퍼블릭 url 반환
     */
    @Override
    @Transactional
    public Images putObject(MultipartFile image, String fileName, ObjectMetadata objectMetadata) throws IOException {

        amazonS3Client.putObject(s3Properties.getBucket(), fileName, image.getInputStream(), objectMetadata);

        return Images.create(s3Properties.getHostUrl(), fileName);
    }

    /**
     * 이미지 메타데이터 생성
     */
    public ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        return objectMetadata;
    }
}
