package com.app.engine;

import java.util.Collection;

public interface dictI {
    /*
    * TODO: update this to use some sort of cvar-like class as value
    *  So that we can leverage onUpdate() and onChange() functionality
    * */
    void put(String key, String value);

    String get(String key);

    boolean contains(String key);

    Collection<String> keys();

    String toString();
}
