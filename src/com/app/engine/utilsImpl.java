package com.app.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class utilsImpl implements utils {
    public utilsImpl() {

    }

    public int scaleInt(int inputInt, int nativeScale, int gameHeight) {
        return (int) ((((double) inputInt / (double) nativeScale) * (double) gameHeight));
    }

    public int unscaleInt(int inputInt, int nativeScale, int gameHeight) {
        return (int) ((((double) inputInt * (double) nativeScale) / (double) gameHeight));
    }

    public int roundToNearest(int val, int nearest) {
        return Math.round((float) val /nearest) * nearest;
    }

    public String createId() {
        return Integer.toString(ThreadLocalRandom.current().nextInt(11111111, 100000000));
    }

    public String getTimeStringFromLong(Long l) {
        Date date = new Date(l);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(date);
    }
}

