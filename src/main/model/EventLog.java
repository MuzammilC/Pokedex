package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of Pokedex events
public class EventLog implements Iterable<Event> {

    private static EventLog theLog;
    private Collection<Event> events;

    // Effects: Creates an event log with events. It also does not allow external construction.
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // Effects: Returns the instance of the eventlog if it does not exit already.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // Effects: Adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // Effects: Clears the event log and adds the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
