package com.app.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class schedulerSystem implements schedulerSystemI {
    public class gSchedulerEvent implements schedulerSystemI.gSchedulerEvent {
        public gSchedulerEvent() {

        }

        public void doEvent() {

        }
    }

    public class gSchedulerSystem implements schedulerSystemI.gSchedulerSystem {
        private ConcurrentHashMap<Long, Queue<gSchedulerEvent>> events;
        private ConcurrentLinkedQueue<gSchedulerEvent> doNowEventsQueue;

        public gSchedulerSystem() {
            this.events = new ConcurrentHashMap<>();
            this.doNowEventsQueue = new ConcurrentLinkedQueue<>();
        }

        public synchronized void doEvents(long gameTime) {
            ArrayList<Long> toRemoveIds = new ArrayList<>();
            for (Long eventDoAtTime : this.events.keySet()) {
                if (eventDoAtTime > gameTime)
                    continue;
                this.doNowEventsQueue.addAll(this.events.get(eventDoAtTime));
                toRemoveIds.add(eventDoAtTime);
            }
            for(Long timeStampKey : toRemoveIds) {
                this.events.remove(timeStampKey);
            }
            while (!this.doNowEventsQueue.isEmpty()) {
                gSchedulerEvent event = this.doNowEventsQueue.remove();
                if(event != null)
                    event.doEvent();
            }
        }

        public synchronized void addEvent(Long eventDoAtTime, gSchedulerEvent event) {
            this.events.putIfAbsent(eventDoAtTime, new LinkedList<>());
            this.events.get(eventDoAtTime).add(event);
        }

        public synchronized void clearEvents() {
            this.events.clear();
            this.doNowEventsQueue.clear();
        }
    }
}
