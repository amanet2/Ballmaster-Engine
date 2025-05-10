package com.app.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class utils {

    public static int scaleInt(int inputInt, int nativeScale, int gameHeight) {
        return (int) ((((double) inputInt / (double) nativeScale) * (double) gameHeight));
    }

    public static int unscaleInt(int inputInt, int nativeScale, int gameHeight) {
        return (int) ((((double) inputInt * (double) nativeScale) / (double) gameHeight));
    }

    public static int roundTo(int val, int snap) {
        return Math.round(val/snap) * snap;
    }

    public static String createId() {
        return Integer.toString(ThreadLocalRandom.current().nextInt(11111111, 100000000));
    }

    public static String getTimeStringFromLong(Long l) {
        Date date = new Date(l);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(date);
    }
}

