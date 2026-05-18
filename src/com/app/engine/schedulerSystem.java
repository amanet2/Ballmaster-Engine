package com.app.engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.app.engine.eventSystem.gEvent;
import com.app.engine.eventSystem.gEventGraphics;

public class schedulerSystem implements schedulerSystemI {
    public static class gSchedulerSystem implements schedulerSystemI.gSchedulerSystem {
        private HashMap<Long, Queue<gEvent>> events;
        private ArrayList<gEventGraphics> eventsGraphics;
        private Queue<gEvent> doNowEventsQueue;

        public gSchedulerSystem() {
            this.events = new HashMap<>();
            this.eventsGraphics = new ArrayList<>();
            this.doNowEventsQueue = new LinkedList<>();
        }

        public synchronized void doEvents(long gameTime) {
            ArrayList<Long> toRemoveIds = new ArrayList<>();
            for (Long eventDoAtTime : this.events.keySet()) {
                if (eventDoAtTime > gameTime) continue;
                this.doNowEventsQueue.addAll(this.events.get(eventDoAtTime));
                toRemoveIds.add(eventDoAtTime);
            }
            for(Long timeStampKey : toRemoveIds) {
                this.events.remove(timeStampKey);
            }
            while (!this.doNowEventsQueue.isEmpty()) {
                gEvent event = this.doNowEventsQueue.remove();
                if(event != null) event.doEvent();
            }
        }

        public void doEventsGraphics(long gameTime, Graphics g) {
            ArrayList<Integer> toDoEventIndexes = new ArrayList<>();
            ArrayList<Integer> toRemoveEventsIndexes = new ArrayList<>();

            long currentTimeMillis = System.currentTimeMillis();

            for(int i = 0; i < eventsGraphics.size(); i++) {
                gEventGraphics event = eventsGraphics.get(i);
                if (currentTimeMillis > event.getDoAtTimeMillis() + event.getTimeToLiveMillis())
                    toRemoveEventsIndexes.add(i);
                if(currentTimeMillis > event.getDoAtTimeMillis()) event.doEvent(g);
            }

            for(int i = 0; i < toRemoveEventsIndexes.size(); i++) {
                eventsGraphics.remove(i);
            }
        }

        public synchronized void addEvent(Long eventDoAtTime, gEvent event) {
            this.events.putIfAbsent(eventDoAtTime, new LinkedList<>());
            this.events.get(eventDoAtTime).add(event);
        }

        public void addEventGraphics(long doAtTimeMillis, gEventGraphics event, long timeToLiveMillis) {
            event.setDoAtTimeMillis(doAtTimeMillis);
            event.setTimeToLiveMillis(timeToLiveMillis);
            this.eventsGraphics.add(event);
        }

        public synchronized void clearEvents() {
            this.events.clear();
            this.doNowEventsQueue.clear();
        }

        public void clearEventsGraphics() {
            this.eventsGraphics.clear();
        }
    }
}
