package com.app.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.TimeZone;

public class utils {
    public static class gDate {
        public static String getTimerString(long milliseconds) {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timeString = format.format(milliseconds);
            return timeString.startsWith("00") ? timeString.substring(3) : timeString;
        }
    }

    public static class gDict {
        private HashMap<String, String> internalMap;

        public gDict() {
            internalMap = new HashMap<>();
        }

        public gDict(String stateString) {
            // e.g. {foo=bar, baz=qaz}
            internalMap = new HashMap<>();
            stateString = stateString.trim();
            String trimmedStateString = stateString.substring(1, stateString.length()-1);  // remove surrounding {}
            for(String kvPair : trimmedStateString.split(",")) {
                String[] kvPairTokens = kvPair.split("=");
                String key = kvPairTokens[0].trim();
                String value = kvPairTokens.length > 1 ? kvPairTokens[1].trim() : "";  // accomodate {foo=,bar=baz}
                internalMap.put(key, value);
            }
        }

        public void put(String key, String value) {
            internalMap.put(key, value);
        }

        public String get(String key) {
            return internalMap.get(key);
        }

        public boolean contains(String key) {
            return internalMap.containsKey(key);
        }

        public Collection<String> keys() {
            return internalMap.keySet();
        }

        public String toString() {
            return internalMap.toString();
        }
    }

    public static class gMath {
        public static int scaleIntToWindowHeight(int inputInt, int scale, int windowHeight) {
            return (int) ((((double) inputInt / (double) scale) * (double) windowHeight));
        }

        public static int unscaleIntToWindowHeight(int inputInt, int scale, int windowHeight) {
            return (int) ((((double) inputInt * (double) scale) / (double) windowHeight));
        }

        public static int roundToNearest(int val, int nearest) {
            return Math.round((float) val /nearest) * nearest;
        }
    }
}
