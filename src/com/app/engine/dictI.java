package com.app.engine;

import java.util.Collection;

public interface dictI {
    void put(String key, String value);

    String get(String key);

    boolean contains(String key);

    Collection<String> keys();

    String toString();
}
