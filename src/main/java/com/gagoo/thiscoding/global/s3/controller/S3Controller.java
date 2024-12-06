package com.gagoo.thiscoding.global.s3.controller;


import com.gagoo.thiscoding.global.s3.controller.port.S3Service;
import com.gagoo.thiscoding.global.s3.controller.response.UploadImageResponse;
import com.gagoo.thiscoding.global.s3.controller.validator.ImagePath;
import com.gagoo.thiscoding.global.s3.controller.validator.ValidateImagePath;
import com.gagoo.thiscoding.global.s3.domain.Images;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/images")
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "path") @ValidateImagePath(enumClass = ImagePath.class) String path
    ) throws IOException {
        Images images = s3Service.uploadImage(image, path);

        return ResponseEntity.ok()
                .body(UploadImageResponse.from(images));
    }

//    @DeleteMapping("/images")
//    public ResponseEntity<Void> deleteImage(@RequestBody @Valid DeleteImageRequest imageRequest) {
//        s3Service.deleteImage(imageRequest.getName());
//        return ResponseEntity.ok().build();
//    }
}
