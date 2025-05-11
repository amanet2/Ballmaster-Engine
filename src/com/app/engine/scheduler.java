package com.app.engine;

public interface scheduler {
    void doEvents(long gameTime);

    void putEvent(Long key, doableImpl event);

    void clearEvents();
}
