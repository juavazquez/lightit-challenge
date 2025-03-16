package com.lightit.challenge.service.impl.storage;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        String filename = FILENAME_PREFIX + userId;
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            logger.error("Failed to upload document " + filename + " due to null content type");
            return false;
        }
        String postfix = contentType.split("/")[1];

        try {
            // ask JVM to ask operating system to create temp file
            File tempFile = File.createTempFile(filename, postfix);

            // ask JVM to delete it upon JVM exit if you forgot / can't delete due exception
            tempFile.deleteOnExit();

            // transfer MultipartFile to File
            multipartFile.transferTo(tempFile);

            // Store the file
            storageService.store(tempFile, DOCUMENT_DIRECTORY);

            tempFile.delete();

        } catch (Exception e) {
            logger.error("Failed to upload document -> " + filename, e);
            return false;
        }
        return true;
    }

}
