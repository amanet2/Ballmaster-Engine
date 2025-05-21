package com.app.engine;

public interface fileSystemI {
    interface gFile {
        String getFullPath();
    }

    interface gDirectory {
        gFile[] getFiles();
        String[] getFileNames();
    }

    // TODO: need ability to refresh/read directories multiple times

    /**
     * e.g. directory /foo contains files [ bar.c, baz.c ]
     * gDirectory.readDirectory("/foo");  // returns String[]{"bar.c", "baz.c"}
     * // qaz.c gets written to /foo by mapmaker or savefile system
     * gDirectory.readDirectory("/foo");  // returns String[]{"bar.c", "baz.c", "qaz.c"}
     */
    interface gFileSystem {
        gDirectory directory(String path);
        gFile file(String file);
    }
}
