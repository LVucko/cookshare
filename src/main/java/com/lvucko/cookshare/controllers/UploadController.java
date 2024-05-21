package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected");
        }
        //Nema smisla vracat putanju, bolje bi bilo vratiti id slike i onda koristiti taj id. Ako bude trebalo obrisati sliku ili preuzeti na primjer sve je lakše s id-em
        return ResponseEntity.ok(uploadService.saveFile(file));
    }
}
