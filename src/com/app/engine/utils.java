package com.app.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        private HashMap<String, Object> internalMap;  // values can be String or gDict
        public static char escapeCharacter = '^';  // TODO: handle escape chars in dict strings

        public gDict() {
            this.internalMap = new HashMap<>();
        }

        public gDict(String dictString) {
            this.internalMap = new HashMap<>();

            ArrayList<String> lexedDictStringTokens = lexDictString(dictString);

            parseLexedDictStringTokens(lexedDictStringTokens);
        }

        private ArrayList<String> lexDictString(String dictString) {
            ArrayList<String> lexedDictStringTokens = new ArrayList<>();

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < dictString.length(); i++) {
                char charAtIndex = dictString.charAt(i);

                if(charAtIndex == '{' || charAtIndex == '=' || charAtIndex == ',' || charAtIndex == '}') {
                    if(!stringBuilder.isEmpty()) {
                        lexedDictStringTokens.add(stringBuilder.toString().trim());
                        stringBuilder = new StringBuilder();
                    }

                    lexedDictStringTokens.add(Character.toString(charAtIndex));
                }
                else
                    stringBuilder.append(dictString.charAt(i));
            }

            return lexedDictStringTokens;
        }

        private void parseLexedDictStringTokens(ArrayList<String> lexedDictStringTokens) {
            Stack<gDict> workingDicts = new Stack<>();
            Stack<String> workingKeys = new Stack<>();
            boolean expectValue = false;

            for (String token : lexedDictStringTokens) {
                switch (token) {
                    case "{" -> {
                        if(expectValue) {
                            workingDicts.push(new gDict());
                            expectValue = false;
                        }
                        else
                            workingDicts.push(new gDict());
                    }
                    case "}" -> {
                        while(!workingKeys.isEmpty()) {
                            gDict popped = workingDicts.pop();
                            workingDicts.getLast().put(workingKeys.pop(), popped);
                        }
                    }
                    case "=" -> expectValue = true;
                    case "," -> {}
                    default -> {
                        // handle various string values
                        if (expectValue) {
                            workingDicts.getLast().put(workingKeys.pop(), token);
                            expectValue = false;
                        } else
                            workingKeys.push(token);
                    }
                }
            }
            this.internalMap = workingDicts.getLast().internalMap;
        }

        public void put(String key, Object value) {
            internalMap.put(key, value);
        }

        public Object get(String key) {
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
        public static double scaleDoubleToWindowHeight(double input, double scale, int windowHeight) {
            return (input / scale) * (double) windowHeight;
        }

        public static double unscaleDoubleToWindowHeight(double input, double scale, int windowHeight) {
            return (input * scale) / (double) windowHeight;
        }

        public static int roundToNearest(int val, int nearest) {
            return Math.round((float) val /nearest) * nearest;
        }
    }
}
