package com.app.engine;

public interface scheduler {
    void doEvents(long gameTime);

    void putEvent(String key, doableImpl event);

    void clearEvents();
}
