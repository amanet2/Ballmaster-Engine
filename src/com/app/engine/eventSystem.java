package com.app.engine;

import com.app.engine.utils.gBounds;

import java.awt.Graphics;

public class eventSystem {
    public static class gEvent implements eventSystemI.gEvent {
        private gEventTrigger parentEventTrigger;

        public void doEvent() {
            // to be overwritten
        }

        public void setParentEventTrigger(gEventTrigger eventTrigger) {
            this.parentEventTrigger = eventTrigger;
        }

        public gEventTrigger getParentEventTrigger() {
            return this.parentEventTrigger;
        }
    }

    public static class gEventGraphics extends gEvent implements eventSystemI.gEventGraphics {
        private long doAtTimeMillis;
        private long timeToLiveMillis;

        public long getDoAtTimeMillis() {
            return this.doAtTimeMillis;
        }

        public void setDoAtTimeMillis(long doAtTimeMillis) {
            this.doAtTimeMillis = doAtTimeMillis;
        }

        public void setTimeToLiveMillis(long timeToLive) {
            this.timeToLiveMillis = timeToLive;
        }

        public long getTimeToLiveMillis() {
            return this.timeToLiveMillis;
        }

        public void doEvent(Graphics g) {
            // to be overwritten
        }
    }

    public static class gEventTrigger implements eventSystemI.gEventTrigger {
        private boolean isTriggered = false;
        private gEvent event;
        private gEventTriggerBounds parentEventTriggerBounds;

        public gEventTrigger(gEvent event) {
            this.event = event;
        }

        public void doEvent() {
            this.event.doEvent();
            this.isTriggered = true;
        }

        public boolean isTriggered() {
            return this.isTriggered;
        }

        public void setParentEventTriggerBounds(gEventTriggerBounds parentEventTriggerBounds) {
            this.parentEventTriggerBounds = parentEventTriggerBounds;
        }

        public gEventTriggerBounds getParentEventTriggerBounds() {
            return this.parentEventTriggerBounds;
        }
    }

    public static class gEventTriggerBounds implements eventSystemI.gEventTriggerBounds {
        private gBounds bounds;
        private gEventTrigger eventTrigger;

        public gEventTriggerBounds(gEventTrigger eventTrigger) {
            this.eventTrigger = eventTrigger;
        }

        public void setBounds(gBounds bounds) {
            this.bounds = bounds;
        }

        public gBounds getBounds() {
            return this.bounds;
        }

        public void doTrigger() {
            if (this.eventTrigger.isTriggered) return;

            this.eventTrigger.doEvent();
        }
    }
}
