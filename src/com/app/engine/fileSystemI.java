package com.app.engine;

public interface fileSystemI {
    interface gFile {
        String getName();
        gDirectory getParentDirectory();
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
