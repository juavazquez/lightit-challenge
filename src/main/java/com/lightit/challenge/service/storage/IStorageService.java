package com.lightit.challenge.service.storage;

import java.io.File;

public interface IStorageService {

    boolean store(File file, String dir);

    String retrieve(String filename);

}
