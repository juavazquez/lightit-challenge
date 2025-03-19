package com.lightit.challenge.service.impl.storage;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lightit.challenge.service.storage.IDocumentUploader;
import com.lightit.challenge.service.storage.IStorageService;

import jakarta.validation.constraints.NotNull;

@Service
public class DocumentUploader implements IDocumentUploader {

    private static final Logger logger = LoggerFactory.getLogger(DocumentUploader.class);
    private static final String DOCUMENT_DIRECTORY = "documents";
    private static final String FILENAME_PREFIX = "document-";
    private final IStorageService storageService;

    public DocumentUploader(IStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public boolean upload(@NotNull MultipartFile multipartFile, String userId) {
        logger.info("Uploading document for user -> " + userId);

        // Save the file with a unique name so that it can be retrieved later
        String filename = FILENAME_PREFIX + userId;

        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            logger.error("Failed to upload document " + filename + " due to null content type");
            return false;
        }

        String postfix = "." + contentType.split("/")[1];

        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir"), filename + postfix);

            // Ensure the file does not already exist
            if (tempFile.exists()) {
                tempFile.delete(); // Delete if it exists
            }

            tempFile.createNewFile(); // Explicitly create the file

            // ask JVM to delete it upon JVM exit if you forgot / can't delete due exception
            tempFile.deleteOnExit();

            // transfer MultipartFile to File
            multipartFile.transferTo(tempFile);

            // Call service to store the file
            storageService.store(tempFile, DOCUMENT_DIRECTORY);

            // Delete the temp file
            tempFile.delete();

        } catch (Exception e) {
            logger.error("Failed to process file -> " + filename, e);
            return false;
        }
        return true;
    }

    @Override
    public String retrieve(String userId) {
        return storageService.retrieve(FILENAME_PREFIX + userId);
    }

}
