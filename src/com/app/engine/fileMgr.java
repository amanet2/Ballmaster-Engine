package com.app.engine;

import java.io.File;
import java.util.Arrays;

public class fileMgr implements fileMgrI {
    public fileMgr() {

    }

    @Override
    public String[] getFilesInDirectory(String directoryPath) {
        String[] selectionArray = new String[]{};
        File fp = new File(directoryPath);
        File[] fpContents = fp.listFiles();
        for(File ffp : fpContents) {
            if(ffp.isFile()) {
                selectionArray = Arrays.copyOf(selectionArray,selectionArray.length+1);
                selectionArray[selectionArray.length-1] = ffp.getName();
            }
        }
        return selectionArray;
    }
}
