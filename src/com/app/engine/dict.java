package com.app.engine;

import java.util.Collection;
import java.util.HashMap;

public class dict implements dictI {
    /*
     * TODO: update this to use some sort of cvar-like class as value
     *  So that we can leverage onUpdate() and onChange() functionality
     * */
    private HashMap<String, String> internalMap;

    public dict() {
        internalMap = new HashMap<>();
    }

    public dict(String stateString) {
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
