package com.app.engine;

public interface fileSystemI {
    interface gFile {
        String getFullPath();
    }

    interface gDirectory {
        gFile[] getFiles();
        String[] getFileNames();
    }
}
