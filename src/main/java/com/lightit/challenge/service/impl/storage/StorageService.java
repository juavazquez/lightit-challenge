package com.lightit.challenge.service.impl.storage;

import com.lightit.challenge.service.storage.IStorageAdapter;
import com.lightit.challenge.service.storage.IStorageService;
import java.io.File;
import org.springframework.stereotype.Service;

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

  @Override
  public String retrieve(String filename) {
    return storageAdapter.retrieve(filename);
  }
}
