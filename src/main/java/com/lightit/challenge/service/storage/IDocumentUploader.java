package com.lightit.challenge.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IDocumentUploader {

    boolean upload(MultipartFile file, String userId);

}
