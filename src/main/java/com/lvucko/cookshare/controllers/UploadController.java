package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/upload")
public class UploadController {
    private final UploadService uploadService;
    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(uploadService.saveFile(file));
    }
}
