package com.app.engine;

import java.awt.Graphics;

import com.app.engine.eventSystem.gEvent;
import com.app.engine.eventSystem.gEventGraphics;

public interface schedulerSystemI {
    interface gSchedulerSystem {
        void doEvents(long gameTime);  // execute all events schedule to execute on or before this time
        void doEventsGraphics(long gameTime, Graphics g);  // execute all events schedule to execute on or before this time
        void addEvent(Long key, gEvent event);
        void addEventGraphics(long doAtTimeMillis, gEventGraphics event, long timeToLiveMillis);
        void clearEvents();
        void clearEventsGraphics();
    }
}
