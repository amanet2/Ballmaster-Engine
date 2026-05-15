package com.app.engine;

public interface fileSystemI {
    interface gFile {
        String getName();
        gDirectory getParentDirectory();
        String[] getFileLines();
        String getFileString();
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

    // TODO: these should not be hard-coded in the engine
    interface gBaseFileSystem {
        gFileSystem getFileSystemConfig();


        gFileSystem getFileSystemSprites();

        gFileSystem getFileSystemMaps();

        gFileSystem getFileSystemTextures();
    }
}
