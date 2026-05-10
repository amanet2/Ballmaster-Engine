package com.app.engine;

public interface schedulerSystemI {
    interface gSchedulerSystem {
        void doEvents(long gameTime);  // execute all events schedule to execute on or before this time
        void addEvent(Long key, event event);
        void clearEvents();
    }
}
