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
        public static char escapeCharacter = '\\';
        private ArrayList<String> parsingTokens = new ArrayList<>();

        public gDict() {
            this.internalMap = new HashMap<>();
        }

        public gDict(String dictString) {
            parsingTokens = lexDictString(dictString);
            this.internalMap = (HashMap<String, Object>) parse();
        }

        private ArrayList<String> lexDictString(String dictString) {
            ArrayList<String> lexedDictStringTokens = new ArrayList<>();

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < dictString.length(); i++) {
                char charAtPrevIndex = i > 0 ? dictString.charAt(i-1) : 'f';
                char charAtIndex = dictString.charAt(i);

                if(charAtIndex == escapeCharacter) {
                    // do nothing
                }
                else if(charAtPrevIndex != escapeCharacter && (charAtIndex == '{' || charAtIndex == '=' || charAtIndex == ',' || charAtIndex == '}')) {
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

        private HashMap<String, Object> parseObject() {
            HashMap<String, Object> map = new HashMap<>();

            String token = parsingTokens.getFirst();

            if(token.equals("}")) {
                iterateParsing();
                return map;
            }

            while(true) {
                String key = parsingTokens.getFirst();

                iterateParsing();
                iterateParsing();

                Object value = parse();

                map.put(key, value);

                token = parsingTokens.getFirst();

                if(token.equals("}")) {
                    iterateParsing();
                    return map;
                }

                iterateParsing();
            }
        }

        private Object parse() {
            String token = parsingTokens.getFirst();

            if(token.equals("{")) {
                iterateParsing();
                return parseObject();
            }
            else {
                iterateParsing();
                return token;
            }
        }

        private void iterateParsing() {
            parsingTokens = new ArrayList<String>(parsingTokens.subList(1, parsingTokens.size()));
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
