package com.app.engine;

import java.io.File;
import java.util.Arrays;

public class fileMgr implements fileMgrI {
    private String dir;
    private String[] fileSelection;

    public fileMgr(String dirPath) {
        this.dir = dirPath;
        this.fileSelection = new String[]{};
        File fp = new File(this.dir);
        File[] fpContents = fp.listFiles();
        for(File ffp : fpContents) {
            if(ffp.isFile()) {
                this.fileSelection = Arrays.copyOf(this.fileSelection,this.fileSelection.length+1);
                this.fileSelection[this.fileSelection.length-1] = ffp.getName();
            }
        }
    }

    public String[] getFileSelection() {
        String[] selectionArray = new String[]{};

        return selectionArray;
    }

    public String dir() {
        return this.dir;
    }
}
