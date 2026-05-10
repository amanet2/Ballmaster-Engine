package com.app.engine;

import com.app.engine.eventSystem.gEvent;

public interface schedulerSystemI {
    interface gSchedulerSystem {
        void doEvents(long gameTime);  // execute all events schedule to execute on or before this time
        void addEvent(Long key, gEvent event);
        void clearEvents();
    }
}
