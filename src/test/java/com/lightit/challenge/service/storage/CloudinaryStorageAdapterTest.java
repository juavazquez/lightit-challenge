package com.lightit.challenge.service.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.api.ApiResponse;
import com.lightit.challenge.service.impl.storage.CloudinaryStorageAdapter;
import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CloudinaryStorageAdapterTest {

  @Mock Cloudinary cloudinary;

  @InjectMocks CloudinaryStorageAdapter cloudinaryStorageAdapter;

  @Test
  public void testStoreSuccess() throws Exception {
    File file = new File("test.jpg");

    when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
    when(cloudinary.uploader().upload(any(File.class), anyMap())).thenReturn(new HashMap<>());

    boolean result = cloudinaryStorageAdapter.store(file, "testDir");

    assertTrue(result);
  }

  @Test
  public void testStoreFailure() throws Exception {
    File file = new File("test.jpg");

    when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
    when(cloudinary.uploader().upload(any(File.class), anyMap()))
        .thenThrow(new RuntimeException("Upload failed"));

    boolean result = cloudinaryStorageAdapter.store(file, "testDir");

    assertFalse(result);
  }

  @Test
  public void testRetrieveSuccess() throws Exception {
    String filename = "test.txt";

    ApiResponse apiResponse = mock(ApiResponse.class);
    when(apiResponse.get("secure_url")).thenReturn("http://example.com/test.txt");

    when(cloudinary.api()).thenReturn(mock(Api.class));
    when(cloudinary.api().resource(any(String.class), anyMap())).thenReturn(apiResponse);

    String result = cloudinaryStorageAdapter.retrieve(filename);

    assertEquals("http://example.com/test.txt", result);
  }

  @Test
  public void testRetrieveFailure() throws Exception {
    String filename = "test.txt";

    when(cloudinary.api()).thenReturn(mock(Api.class));
    when(cloudinary.api().resource(any(String.class), anyMap()))
        .thenThrow(new RuntimeException("Retrieve failed"));

    String result = cloudinaryStorageAdapter.retrieve(filename);

    assertEquals(null, result);
  }
}
