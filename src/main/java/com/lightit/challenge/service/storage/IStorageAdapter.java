package com.lightit.challenge.service.storage;

import java.io.File;

public interface IStorageAdapter {

    boolean store(File file, String dir);

}
