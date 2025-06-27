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

        @SuppressWarnings("unchecked")
        public gDict(String dictString) {
            parsingTokens = lex(dictString);
            this.internalMap = (HashMap<String, Object>) parse();
        }

        private ArrayList<String> lex(String dictString) {
            ArrayList<Character> importantChars = new ArrayList<>(List.of(new Character[]{'{', '}', '=', ',', '[', ']'}));
            ArrayList<String> lexedDictStringTokens = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < dictString.length(); i++) {
                char charAtPrevIndex = i > 0 ? dictString.charAt(i-1) : 'f';
                char charAtIndex = dictString.charAt(i);

                if(charAtPrevIndex != escapeCharacter && (importantChars.contains(charAtIndex))) {
                    if(!stringBuilder.isEmpty()) {
                        lexedDictStringTokens.add(stringBuilder.toString().trim());
                        stringBuilder = new StringBuilder();
                    }
                    lexedDictStringTokens.add(Character.toString(charAtIndex));
                }
                else if(charAtIndex != escapeCharacter)
                    stringBuilder.append(dictString.charAt(i));
            }

            return lexedDictStringTokens;
        }

        private ArrayList<Object> parseArray() {
            ArrayList<Object> list = new ArrayList<>();

            String token = getParsingToken();
            if(token.equals("]")) { // empty list case
                advanceParsingToken();
                return list;
            }

            while(true) {
                System.out.println("---");
                System.out.println(parsingTokens);

                Object value = parse();
                list.add(value);

                token = getParsingToken();
                if(token.equals("]")) {
                    advanceParsingToken();
                    return list;
                }
                else
                    advanceParsingToken();
            }
        }

        private HashMap<String, Object> parseObject() {
            HashMap<String, Object> map = new HashMap<>();

            String token = getParsingToken();

            if(token.equals("}")) {  // empty map case
                advanceParsingToken();
                return map;
            }

            while(true) {
                String key = getParsingToken();

                advanceParsingToken(); // go to equal sign
                advanceParsingToken(); // skip equal sign

                Object value = parse(); // get value as string or new dict

                map.put(key, value); // assign

                token = getParsingToken(); // check if closing the obj or a comma

                if(token.equals("}")) {
                    advanceParsingToken();
                    return map;
                }

                advanceParsingToken();  // skip comma
            }
        }

        private Object parse() {
            String token = getParsingToken();

            if(token.equals("{")) {
                advanceParsingToken();
                return parseObject();
            }
            else if(token.equals("[")) {
                advanceParsingToken();
                return parseArray();
            }
            else {
                advanceParsingToken();
                return token;
            }
        }

        private String getParsingToken() {
            return parsingTokens.getFirst();
        }

        private void advanceParsingToken() {
            parsingTokens = new ArrayList<>(parsingTokens.subList(1, parsingTokens.size()));
        }

        public void put(String key, Object value) {
            this.internalMap.put(key, value);
        }

        public Object get(String key) {
            return this.internalMap.get(key);
        }

        public boolean contains(String key) {
            return this.internalMap.containsKey(key);
        }

        public Collection<String> keys() {
            return this.internalMap.keySet();
        }

        public String toString() {
            return this.internalMap.toString();
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
