package com.app.engine;

public interface schedulerI {
    void doEvents(long gameTime);

    void putEvent(Long key, doable event);

    void clearEvents();
}
