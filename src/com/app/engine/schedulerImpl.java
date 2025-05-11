package com.app.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class schedulerImpl implements scheduler {
    //TODO: handle overlap of events that share the timestamp key in events in the toRemove list
    ConcurrentHashMap<Long, Queue<doableImpl>> events;
    ConcurrentLinkedQueue<doableImpl> doNowEventsQueue;

    public schedulerImpl() {
        events = new ConcurrentHashMap<>();
        doNowEventsQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void doEvents(long gameTime) {
        ArrayList<Long> toRemoveIds = new ArrayList<>();
        for (Long eventDoAtTime : events.keySet()) {
            if (eventDoAtTime > gameTime)
                continue;
            doNowEventsQueue.addAll(events.get(eventDoAtTime));
            toRemoveIds.add(eventDoAtTime);
        }
        for(Long timeStampKey : toRemoveIds) {
            events.remove(timeStampKey);
        }
        while (!doNowEventsQueue.isEmpty()) { //TODO: crash here when queue.remove() is null
            doableImpl o = doNowEventsQueue.remove();
            if(o != null)
                o.doCommand();
        }
    }

    public synchronized void putEvent(Long eventDoAtTime, doableImpl event) {
        events.putIfAbsent(eventDoAtTime, new LinkedList<>());
        events.get(eventDoAtTime).add(event);
    }

    public synchronized void clearEvents() {
        events.clear();
        doNowEventsQueue.clear();
    }
}
