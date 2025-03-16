package com.lightit.challenge.service.impl.storage;

import java.io.File;

import org.springframework.stereotype.Service;
import com.lightit.challenge.service.storage.IStorageService;
import com.lightit.challenge.service.storage.IStorageAdapter;

@Service
public class StorageService implements IStorageService {

    private final IStorageAdapter storageAdapter;

    public StorageService(IStorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    @Override
    public boolean store(File file, String dir) {
        return storageAdapter.store(file, dir);
    }

}
