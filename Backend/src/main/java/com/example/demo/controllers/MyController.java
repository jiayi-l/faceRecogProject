package com.example.demo.controllers;

import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CustomVisionStorage;
import com.example.demo.service.Storage;

@RestController
@CrossOrigin(origins = "${FRONTEND_HOST:*}")

public class MyController {
    @Autowired
    @Qualifier("customVisionStorage")
    Storage storage;

    @GetMapping("/hello")
    String hello() {
        return "Hello World";
    }

    @PostMapping("/images")
    public ResponseEntity<Object> uploadImage(@RequestBody String data) throws Exception {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] rawBytes = Base64.getDecoder().decode(base64);
        String imageName = UUID.randomUUID() + ".png";
        storage.save("Joey", rawBytes);
        return new ResponseEntity<>("Successfully uploaded image", HttpStatus.OK);
    }

     @PostMapping("/validate")
     public ResponseEntity<String> validate(@RequestBody String data) throws Exception {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] rawBytes = Base64.getDecoder().decode(base64);
        return CustomVisionStorage.validate(rawBytes);
    }
}
