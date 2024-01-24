package com.example.demo.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomVisionStorage implements Storage{
    final static String PROJECT_ID = "7260bfa6-07d9-40a9-8d5f-7f3adb431d5c";
    final static String TRAINING_ENDPOINT = "https://joeylu.cognitiveservices.azure.com";
    final static String PREDICTION_ENDPOINT = "https://joeylu-prediction.cognitiveservices.azure.com/customvision/v3.0/Prediction/7260bfa6-07d9-40a9-8d5f-7f3adb431d5c/classify/iterations/face/image";
    final static String API_KEY = "745b1ff6e47a40b68040d3813c2af122";

    static RestTemplate restTemplate = new RestTemplate();

    // Maps each tag to its Custom Vision tag ID
    final static public Map<String, String> tags = new HashMap<>() {{
        put("Joey", "655116da-e67f-45ec-9023-adb25f1b17e1");
        put("Others", "8ac954cc-0c10-411e-b4da-569b68860e6d");
    }};

    /** Creates a new tag to categorize data in Custom Vision */
    public static void createTag(String tagName) throws JSONException {
        // Add params to URI
        Map<String, String> params = new HashMap<>();
        params.put("endpoint", TRAINING_ENDPOINT);
        params.put("projectId", PROJECT_ID);
        params.put("name", tagName);

        String url = "{endpoint}/customvision/v3.3/Training/projects/{projectId}/tags?name={name}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", API_KEY);

        // Make request and print result of success
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());

        System.out.println(jsonObject.getString("id") + ": " + tagName);
    }

    /** Uploads a new image to the training set and tags it accordingly */
    public static void uploadImage(String tagId, byte[] fileData) throws JSONException {
        // Add params to URI
        Map<String, String> params = new HashMap<>();
        params.put("endpoint", TRAINING_ENDPOINT);
        params.put("projectId", PROJECT_ID);
        params.put("tagIds", tagId);

        String url = "{endpoint}/customvision/v3.3/training/projects/{projectId}/images?tagIds={tagIds}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Training-key", API_KEY);

        // Make request and print result of success
        HttpEntity<byte[]> request = new HttpEntity<>(fileData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        System.out.println(response.getBody());
    }

    /** Sends a prediction request to Custom Vision endpoint and returns the result of validation */
    public static ResponseEntity<String> validate(byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Prediction-Key", "219e536a5bff49cdaed738dcee78a40a");

        HttpEntity<byte[]> entity = new HttpEntity<>(data, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(PREDICTION_ENDPOINT, entity, String.class);
        return result;
    }

    public static void main(String args[]) throws JSONException {
        createTag("Joey");
        createTag("Others");
    }

    @Override
    public void save(String fileName, byte[] data) throws Exception {
        uploadImage(tags.get(fileName), data);
    }
}
