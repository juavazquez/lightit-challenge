package com.lightit.challenge.service.storage;

import static org.mockito.Mockito.verify;

import com.lightit.challenge.service.impl.storage.StorageService;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {

  @Mock IStorageAdapter storageAdapter;

  @InjectMocks StorageService storageService;

  @Test
  public void testStoreFile() {
    File file = new File("test.txt");
    String dir = "test";

    storageService.store(file, dir);

    verify(storageAdapter).store(file, dir);
  }

  @Test
  public void testRetrieveFileUrl() {
    String filename = "test.txt";

    storageService.retrieve(filename);

    verify(storageAdapter).retrieve(filename);
  }
}
