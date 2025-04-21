package com.gagoo.thiscoding.domain.file.controller;


import com.gagoo.thiscoding.domain.file.controller.port.FileService;
import com.gagoo.thiscoding.domain.file.controller.response.UploadImageResponse;
import com.gagoo.thiscoding.domain.file.controller.validator.ValidateImagePath;
import com.gagoo.thiscoding.domain.file.controller.validator.ImagePath;
import com.gagoo.thiscoding.domain.file.domain.Images;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/images")
    @AuthorizationRequired(value = {Role.USER, Role.ADMIN})
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "path") @ValidateImagePath(enumClass = ImagePath.class) String path
    ) throws IOException {
        Images images = fileService.uploadImage(image, path);

        return ResponseEntity.ok()
                .body(UploadImageResponse.from(images));
    }

//    @DeleteMapping("/images")
//    public ResponseEntity<Void> deleteImage(@RequestBody @Valid DeleteImageRequest imageRequest) {
//        s3Service.deleteImage(imageRequest.getName());
//        return ResponseEntity.ok().build();
//    }
}
