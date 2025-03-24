package com.lightit.challenge.service.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lightit.challenge.service.impl.storage.DocumentUploader;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class DocumentUploaderTest {

  @Mock IStorageService storageService;

  @InjectMocks DocumentUploader documentUploader;

  @Test
  public void testUpload_Success() throws Exception {
    String userId = "123";
    String contentType = "image/jpg";
    MultipartFile multipartFile = mock(MultipartFile.class);

    when(multipartFile.getContentType()).thenReturn(contentType);

    boolean result = documentUploader.upload(multipartFile, userId);

    verify(multipartFile).transferTo(any(File.class));
    verify(storageService).store(any(File.class), eq("documents"));

    assertTrue(result);
  }

  @Test
  public void testUploadFailureDueToNullContentType() {
    String userId = "123";
    MultipartFile multipartFile = mock(MultipartFile.class);

    when(multipartFile.getContentType()).thenReturn(null);

    boolean result = documentUploader.upload(multipartFile, userId);

    assertFalse(result);
  }

  @Test
  public void testUploadFailureDueToException() throws Exception {
    String userId = "123";
    String contentType = "application/jpg";
    MultipartFile multipartFile = mock(MultipartFile.class);

    when(multipartFile.getContentType()).thenReturn(contentType);
    doThrow(new RuntimeException()).when(multipartFile).transferTo(any(File.class));

    boolean result = documentUploader.upload(multipartFile, userId);

    assertFalse(result);
  }

  @Test
  public void testRetrieve() {
    String userId = "123";
    String expectedUrl = "url/to/document";

    when(storageService.retrieve("document-" + userId)).thenReturn(expectedUrl);

    String result = documentUploader.retrieve(userId);

    assertEquals(expectedUrl, result);
  }
}
