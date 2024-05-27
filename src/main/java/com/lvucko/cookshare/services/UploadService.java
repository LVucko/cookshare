package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.PictureDao;
import com.lvucko.cookshare.exceptions.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final PictureDao pictureDao;
    @Value("${imagesPath}")
    private String folder;
    @Value("${shortImagesPath}")
    private String shortFolder;

    public Long saveFile(MultipartFile file){
        if(file.isEmpty()){
            throw new FileUploadException("No file selected");
        }
        try{
            byte[] bytes = file.getBytes();
            String filename = UUID.randomUUID() + file.getOriginalFilename();
            File newFile = new File(folder + filename);
            file.transferTo(newFile);
            return pictureDao.addNewPicture(shortFolder + filename);
        }
        catch(IOException e){
            throw new FileUploadException(e.getMessage());
        }
    }

}
