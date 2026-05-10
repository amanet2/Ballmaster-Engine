package com.app.engine;

import com.app.engine.utils.gBounds;

public interface eventSystemI {
    interface gEvent {
        void doEvent();
    }

    interface gEventTrigger {
        void doEvent();
        boolean isTriggered();
    }

    interface gEventTriggerBounds {
        gBounds getBounds();
        void setBounds(gBounds bounds);
        void doTrigger();
    }
}
