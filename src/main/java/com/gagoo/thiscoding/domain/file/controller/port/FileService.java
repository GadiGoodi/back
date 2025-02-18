package com.gagoo.thiscoding.domain.file.controller.port;

import com.gagoo.thiscoding.domain.file.domain.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    Images uploadImage(MultipartFile image, String path) throws IOException;
}
