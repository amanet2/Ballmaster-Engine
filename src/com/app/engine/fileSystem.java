package com.app.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class fileSystem implements fileSystemI {
    public static class gFile implements fileSystemI.gFile {
        private File file;
        private String name;
        private gDirectory parentDirectory;

        public gFile(gDirectory parentDirectory, String name) {
            this.file = new File(name);
            this.parentDirectory = parentDirectory;
            this.name = name;
        }

        public String[] getFileLines() {
            try {
                return Files.readAllLines(this.file.toPath()).toArray(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String[0];
        }

        public String getName() {
            return this.name;
        }

        public gDirectory getParentDirectory() {
            return this.parentDirectory;
        }
    }

    public static class gDirectory implements fileSystemI.gDirectory {
        private String name;
        private gDirectory parentDirectory;
        private gDirectory[] subDirectories;
        private gFile[] files;

        public gDirectory(gDirectory parentDirectory, String name) {
            this.name = name;
            this.parentDirectory = parentDirectory;
            this.subDirectories = new gDirectory[]{};
            this.files = new gFile[0];

            File fp = new File(this.name);
            File[] fpContents = fp.listFiles();

            for(File ffp : fpContents) {
                String path = ffp.getPath().replace('\\', '/');
                if(ffp.isFile()) {
                    this.files = Arrays.copyOf(this.files,this.files.length+1);
                    this.files[this.files.length - 1] = new gFile(this, path);
                }
                else if(ffp.isDirectory()) {
                    this.subDirectories = Arrays.copyOf(this.subDirectories,this.subDirectories.length+1);
                    this.subDirectories[this.subDirectories.length - 1] = new gDirectory(this, path);
                }
            }
        }

        public String getName() {
            return this.name;
        }

        public gDirectory getParentDirectory() {
            return this.parentDirectory;
        }

        public gFile[] getFiles() {
            return this.files;
        }

        public gFile getFile(String path) {
            for(gFile f : this.getFiles()) {
                if(f.getName().equals(path))
                    return f;
            }
            for(gDirectory d : this.getSubDirectories()) {
                gFile df = d.getFile(path);
                if(df != null) return df;
            }
            return null;
        }

        public gDirectory[] getSubDirectories() {
            return this.subDirectories;
        }
    }

    public static class gFileSystem implements fileSystemI.gFileSystem {
        private gDirectory rootDirectory;

        public gDirectory getRootDirectory() {
            return this.rootDirectory;
        }

        public gFileSystem(String path) {
            this.rootDirectory = new gDirectory(null, path);
        }
    }

    public static class gBaseFileSystem implements fileSystemI.gBaseFileSystem {
        private static String pathBase = "base";
        private static String pathConfig = getPath("config");
        private static String pathScripts = getPath("scripts");
        private static String pathSprites = getPath("data");

        private static gFileSystem fileSystemConfig;
        private static gFileSystem fileSystemScripts;
        private static gFileSystem fileSystemSprites;

        public gBaseFileSystem() {
            fileSystemConfig = new gFileSystem(pathConfig);
            fileSystemScripts = new gFileSystem(pathScripts);
            fileSystemSprites = new gFileSystem(pathSprites);
        }

        public static String getPath(String path) {
            return "%s/%s".formatted(pathBase, path);
        }

        public gFileSystem getFileSystemConfig() {
            return fileSystemConfig;
        }

        public gFileSystem getFileSystemScripts() {
            return fileSystemScripts;
        }

        public gFileSystem getFileSystemSprites() {
            return fileSystemSprites;
        }

    }
}
