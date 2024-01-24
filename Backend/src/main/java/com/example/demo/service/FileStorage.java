package com.example.demo.service;

import java.io.File;
import java.nio.file.Files;

import org.springframework.stereotype.Component;
@Component
public class FileStorage implements Storage{

    @Override
    public void save(String fileName, byte[] data) throws Exception {
        File path = new File("./images/");
        if (!path.exists()) {
            path.mkdir();
        }
        Files.write(new File("./images/" + fileName).toPath(), data);
    }
    
}
