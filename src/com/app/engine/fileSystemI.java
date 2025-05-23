package com.app.engine;

import java.io.File;

public interface fileSystemI {
    interface gFile {
        String getName();
        gDirectory getParentDirectory();
        File getFile();
        String[] getFileLines();
    }

    interface gDirectory {
        String getName();
        gDirectory getParentDirectory();
        gFile[] getFiles();
        gDirectory[] getSubDirectories();
    }

    interface gFileSystem {
        gDirectory getRootDirectory();
    }
}
