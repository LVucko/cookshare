package com.lvucko.cookshare.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {
    public String saveFile(MultipartFile file){
        try{
            byte[] bytes = file.getBytes();
            String folder = "C:/Users/lvucko1/Documents/React/cookshare/public/uploads/";
            String shortFolder = "uploads/";
            String filename = UUID.randomUUID() + file.getOriginalFilename();
            File newFile = new File(folder + filename);
            file.transferTo(newFile);
            return shortFolder + filename;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
