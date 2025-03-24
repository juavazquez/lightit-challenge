package com.lightit.challenge.service.impl.storage;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.lightit.challenge.service.storage.IStorageAdapter;

import jakarta.annotation.PostConstruct;

@Service
public class CloudinaryStorage implements IStorageAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryStorage.class);

    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name}")
    private String CLOUD_NAME;
    @Value("${cloudinary.api-key}")
    private String API_KEY;
    @Value("${cloudinary.api-secret}")
    private String API_SECRET;

    public CloudinaryStorage() {
    }

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET,
                "secure", true));
    }

    @Override
    public boolean store(File file, String dir) {
        try {
            logger.info("Uploading document to cloudinary -> " + file.getName() + " to directory -> " + dir);
            cloudinary.uploader().upload(file, buildParams(file.getName(), dir));
            return true;
        } catch (Exception e) {
            logger.error("Failed to upload document -> " + file.getName(), e);
            return false;
        }
    }

    @Override
    public String retrieve(String filename) {
        try {
            logger.info("Retrieving document from Cloudinary -> " + filename);
            ApiResponse apiResponse = cloudinary.api().resource(filename, ObjectUtils.emptyMap());

            // Get the secure URL of the file
            return apiResponse.get("secure_url").toString();
        } catch (Exception e) {
            logger.error("Failed to retrieve document -> " + filename, e);
            return null;
        }
    }

    private Map<String, String> buildParams(String filename, String dir) {
        return Map.of(
                "asset_folder", dir,
                "public_id", filename.split("\\.")[0]); // save name without extension
    }

}
