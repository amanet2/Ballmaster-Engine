package com.app.engine;

import java.io.File;
import java.util.Arrays;

public class fileSystem implements fileSystemI {
    public class gFile implements fileSystemI.gFile {
        private String fileName;
        private String parentDirectory;
        private String fullPath;

        public String getFullPath() {
            return this.fullPath;
        }

        public gFile(String parentDirectory, String fileName) {
            this.parentDirectory = parentDirectory;
            this.fileName = fileName;
            this.fullPath = String.format("%s/%s", this.parentDirectory, this.fileName);
        }
    }

    public class gDirectory implements fileSystemI.gDirectory {
        private String path;
        private gFile[] files;
        private String[] fileNames;

        public gDirectory(String path) {
            this.path = path;
            this.files = new gFile[]{};
            this.fileNames = new String[]{};

            File fp = new File(this.path);
            File[] fpContents = fp.listFiles();
            for(File ffp : fpContents) {
                if(ffp.isFile()) {
                    this.files = Arrays.copyOf(this.files,this.files.length+1);
                    this.fileNames = Arrays.copyOf(this.fileNames,this.fileNames.length+1);
                    this.files[this.files.length - 1] = new gFile(this.path, ffp.getName());
                    this.fileNames[this.fileNames.length - 1] = ffp.getName();
                }
            }
        }

        public String getPath() {
            return this.path;
        }

        public gFile[] getFiles() {
            return this.files;
        }

        public String[] getFileNames() {
            return this.fileNames;
        }
    }
}
