package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/upload")
public class UploadController {
    private final UploadService uploadService;
    @PostMapping
    public ResponseEntity<Long> uploadImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(uploadService.saveFile(file));
    }
    @GetMapping
    public ResponseEntity<List<Picture>> getAllImages(){
        return ResponseEntity.ok(uploadService.getAllPictures());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id") Long id){
        uploadService.setPictureToDefault(id);
        return ResponseEntity.ok().build();
    }
}
