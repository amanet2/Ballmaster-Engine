package com.app.engine;

public interface utils {
    int scaleInt(int inputInt, int nativeScale, int gameHeight);

    int unscaleInt(int inputInt, int nativeScale, int gameHeight);

    int roundToNearest(int val, int nearest);

    String createId();

    String getTimeStringFromLong(Long l);
}
