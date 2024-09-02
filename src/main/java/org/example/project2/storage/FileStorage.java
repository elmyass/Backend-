package org.example.project2.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorage {
    String saveFile(MultipartFile file) throws IOException;


    public byte[] getFile(String fileName) throws IOException;
}
