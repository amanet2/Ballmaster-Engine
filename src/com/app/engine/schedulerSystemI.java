package com.app.engine;

public interface schedulerSystemI {
    interface gSchedulerEvent {
        void doEvent();
    }

    interface gSchedulerSystem {
        void doEvents(long gameTime);  // execute all events schedule to execute on or before this time
        void addEvent(Long key, schedulerSystem.gSchedulerEvent event);
        void clearEvents();
    }
}
