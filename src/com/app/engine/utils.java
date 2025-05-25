package com.app.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        private HashMap<String, Object> internalMap;  // value can be String or gDict
        public static char tokenOpenBracket = '{';
        public static char tokenAssigner = '=';
        public static char tokenSeparator = ',';
        public static char tokenCloseBracket = '}';
        public static char escapeCharacter = '^';  // TODO: handle escape chars in strings

        public gDict() {
            internalMap = new HashMap<>();
        }

        public gDict(String dictString) {
            // e.g. {foo=bar, baz={qaz=yaz}}
            internalMap = new HashMap<>();

            ArrayList<String> lexedDictStringTokens = lexDictString(dictString);
            gDict parsedLexedDictStringTokens = parseLexedDictStringTokens(lexedDictStringTokens);

            System.out.println("DICT LEXED TOKENS: " + lexedDictStringTokens);
        }

        private ArrayList<String> lexDictString(String dictString) {
            ArrayList<String> lexedDictStringTokens = new ArrayList<>();

            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < dictString.length(); i++) {
                char charAtIndex = dictString.charAt(i);

                if(charAtIndex == tokenOpenBracket
                        || charAtIndex == tokenAssigner
                        || charAtIndex == tokenSeparator
                        || charAtIndex == tokenCloseBracket) {
                    if(!stringBuilder.isEmpty()) {
//                        lexedDictStringTokens.add("'" + stringBuilder.toString().trim() +"'");  // for debugging
                        lexedDictStringTokens.add(stringBuilder.toString().trim());
                        stringBuilder = new StringBuilder();
                    }

//                    lexedDictStringTokens.add("'" + Character.toString(charAtIndex) + "'");  // for debugging
                    lexedDictStringTokens.add(Character.toString(charAtIndex));
                }
                else
                    stringBuilder.append(dictString.charAt(i));
            }

            return lexedDictStringTokens;
        }

        private gDict parseLexedDictStringTokens(ArrayList<String> lexedDictStringTokens) {
            gDict parsedDict = new gDict();

//            for(int i = 0; i < lexedDictStringTokens.size(); i++) {
//                if(lexedDictStringTokens.equals(Character.toString(tokenOpenBracket)))
//            }

            return parsedDict;
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
            // TODO: implement escape chars
//            for(char reservedChar : new char[]{gDict.tokenOpenBracket, gDict.tokenAssigner, gDict.tokenSeparator, gDict.escapeCharacter}) {
//                kValue = kValue.replace(Character.toString(reservedChar), "^"+reservedChar);
//            }
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
