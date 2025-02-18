package com.gagoo.thiscoding.domain.file.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gagoo.thiscoding.domain.file.controller.port.FileService;
import com.gagoo.thiscoding.domain.file.domain.Images;
import com.gagoo.thiscoding.domain.file.service.port.S3Bucket;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    private final S3Bucket s3Bucket;
    private final UuidHolder UUID;

    /**
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
