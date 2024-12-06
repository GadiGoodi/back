package com.gagoo.thiscoding.global.s3.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gagoo.thiscoding.global.s3.infrastructure.SystemUuidHolder;
import com.gagoo.thiscoding.global.s3.controller.port.S3Service;
import com.gagoo.thiscoding.global.s3.domain.Images;
import com.gagoo.thiscoding.global.s3.service.port.S3Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Bucket s3Bucket;
    private final SystemUuidHolder UUID;

    /**
     *
     * @param image 올리려는 이미지 (하나의 이미지만 가능)
     * @param path s3의 하위 버킷(클라이언트 입장에선 카테고리가 될 수 있음)
     * @return s3에서 지원하는 퍼블릭 url
     */
    @Override
    public Images uploadImage(MultipartFile image, String path) throws IOException {

        String fileName = getFileName(image, path);
        ObjectMetadata objectMetadata = s3Bucket.getObjectMetadata(image);

        return s3Bucket.putObject(image, fileName, objectMetadata);
    }

    /**
     * 파일 이름이 같을 수도 있으므로 랜덤 값 추가
     */
    private String getFileName(MultipartFile image, String imagePath) {
        String originalFileName = image.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        return imagePath + "/" + UUID.random() + extension;
    }

}
