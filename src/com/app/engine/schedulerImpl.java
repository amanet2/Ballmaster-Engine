package com.app.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class schedulerImpl implements scheduler {
    ConcurrentHashMap<String, Queue<doableImpl>> events;
    ConcurrentLinkedQueue<doableImpl> doNowEventsQueue;

    public schedulerImpl() {
        events = new ConcurrentHashMap<>();
        doNowEventsQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void doEvents(long gameTime) {
        long gtime = gameTime;
        ArrayList<String> toRemoveIds = new ArrayList<>();
        for (String eventDoAtTime : events.keySet()) {
            if (Long.parseLong(eventDoAtTime) > gtime)
                continue;
            doNowEventsQueue.addAll(events.get(eventDoAtTime));
            toRemoveIds.add(eventDoAtTime);
        }
        for(String timeStampKey : toRemoveIds) {
            events.remove(timeStampKey);
        }
        while (!doNowEventsQueue.isEmpty()) { //TODO: crash here when queue.remove() is null
            doableImpl o = doNowEventsQueue.remove();
            if(o != null)
                o.doCommand();
        }
    }

    public synchronized void putEvent(String eventDoAtTime, doableImpl event) {
        events.putIfAbsent(eventDoAtTime, new LinkedList<>());
        events.get(eventDoAtTime).add(event);
    }

    public synchronized void clearEvents() {
        events.clear();
        doNowEventsQueue.clear();
    }
}
