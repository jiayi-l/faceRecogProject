package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Component
public class CloudStorage implements Storage {

    @Override
    public void save(String fileName, byte[] data) throws Exception {
        String key = "DefaultEndpointsProtocol=https;AccountName=joeyproject;AccountKey=QXpjsYqgmplUhEEXotXTGouIVr1EGabN9qYSMa/LeK7S7j/W0UoseLWXVefB5e+gg+zjCzJFnapd+AStZih0Cw==;EndpointSuffix=core.windows.net";
        // Create a BlobServiceClient object using a connection string
        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(key).buildClient();

        // Create a unique name for the container
        String containerName = "images";

        // Create the container and return a container client object
        BlobContainerClient blobContainerClient = client.createBlobContainerIfNotExists(containerName);

        // Get a reference to a blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        // Upload the blob
        // blobClient.uploadFromFile(localPath + fileName);
        InputStream targetStream = new ByteArrayInputStream(data);
        blobClient.upload(targetStream);
    }

    
    
}
