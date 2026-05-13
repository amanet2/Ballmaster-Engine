package com.app.engine;

import com.app.engine.utils.gBounds;

public interface eventSystemI {
    interface gEvent {
        void doEvent();
        void setParentEventTrigger(eventSystem.gEventTrigger eventTrigger);
        eventSystem.gEventTrigger getParentEventTrigger();
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
