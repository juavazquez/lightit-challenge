package com.lightit.challenge.service.impl.storage;

import java.io.File;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lightit.challenge.service.storage.IStorageAdapter;

@Service
public class CloudinaryStorageAdapter implements IStorageAdapter {

    private Cloudinary cloudinary;

    public CloudinaryStorageAdapter() { // TODO sacar estas properties de aca
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dymfyz1w8",
                "api_key", "517513871488316",
                "api_secret", "<your_api_secret>",
                "secure", true));
    }

    @Override
    public boolean store(File file, String dir) {
        try {
            cloudinary.uploader().upload(file, ObjectUtils.asMap("dir", dir)); // TODO revisar si funciona el DIR
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
