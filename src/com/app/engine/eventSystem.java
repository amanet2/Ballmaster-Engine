package com.app.engine;

import com.app.engine.utils.gBounds;

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
