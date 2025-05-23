package com.app.engine;

public interface fileSystemI {
    interface gFile {
        String getName();
        gDirectory getParentDirectory();
        String[] getLines();
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
