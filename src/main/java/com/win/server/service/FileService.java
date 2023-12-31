package com.win.server.service;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

import com.win.server.exception.myexception.FileGeneralException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public void saveFile(MultipartFile file, String filename, String path) {
        try {
            File parentFolder = new File(path);
            boolean isExist = parentFolder.mkdirs();
            File myFile = new File(path + '/' + filename);
            FileOutputStream out = new FileOutputStream(myFile);
            out.write(file.getBytes());
        } catch (Exception ex) {
            throw new FileGeneralException();
        }
    }

    public byte[] loadAvatar(String userId) {
        try {
            String path = "src/main/resources/public/user/" + userId + "/avatar.png";
            File defaultAvatar = new File(path); //Personal avatar
            FileInputStream in;
            if (!defaultAvatar.exists())   // User's avatar not exsit => default avatar
                in = new FileInputStream("src/main/resources/public/static/general/default_avatar.png");
            else
                in = new FileInputStream(path);
            return in.readAllBytes();
        } catch (Exception ex) {
            throw new FileGeneralException();
        }
    }

    public byte[] loadPublicFile(String image_name)  {
        try {
            String path = "src/main/resources/public/file/" + image_name;
            FileInputStream in = new FileInputStream(path);
            return in.readAllBytes();
        } catch (Exception ex)  {throw new FileGeneralException(); }
    }

    public byte[] loadPublicGeneralFile(String file_name) throws IOException{
        try {
            String path = "src/main/resources/public/static/general/" + file_name;
            FileInputStream in = new FileInputStream(path);
            return in.readAllBytes();
        } catch (Exception ex) {
            throw new FileGeneralException();
        }

    }

    public String savePublicFile(MultipartFile file) {
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String fileName = UUID.randomUUID().toString().replace("-","") + "." + extension;
        String publicImagePath = "src/main/resources/public/file/";
        saveFile(file, fileName, publicImagePath);
        return fileName;
    }
}