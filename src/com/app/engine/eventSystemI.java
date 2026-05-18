package com.app.engine;

import java.awt.Graphics;

import com.app.engine.utils.gBounds;

public interface eventSystemI {
    interface gEvent {
        void doEvent();
        void setParentEventTrigger(eventSystem.gEventTrigger eventTrigger);
        eventSystem.gEventTrigger getParentEventTrigger();
    }

    interface gEventGraphics {
        void doEvent(Graphics g);
        long getDoAtTimeMillis();
        void setDoAtTimeMillis(long doAtTimeMillis);
        long getTimeToLiveMillis();
        void setTimeToLiveMillis(long ttl);
    }

    interface gEventTrigger {
        void doEvent();
        boolean isTriggered();
        void setParentEventTriggerBounds(eventSystem.gEventTriggerBounds eventTriggerBounds);
        eventSystem.gEventTriggerBounds getParentEventTriggerBounds();
    }

    interface gEventTriggerBounds {
        gBounds getBounds();
        void setBounds(gBounds bounds);
        void doTrigger();
    }
}
