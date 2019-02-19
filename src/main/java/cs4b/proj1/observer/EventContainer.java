package cs4b.proj1.observer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * This is a stupid observer class which just stores whatever it recieves.
 * Meant mostly for testing, but could be useful elsewhere.
 *
 * @param <T> The type which the observer accepts. Other types are ignored.
 */
public class EventContainer<T> implements IObserver {
    /**
     * This is needed. Because Java.
     *
     * It's dumb. Don't ask any questions.
     */
    private Class<T> eventType;

    /**
     * We are explicitly marking this LinkedList because we use some of
     * its methods. List will NOT work.
     */
    private LinkedList<T> allEvents;

    /**
     * Due to limitations of java, when constructing one of these,
     * you MUST pass in the T.class, which is an object of type Class<T>.
     * This is needed so that we can typecast the event we recieve.
     *
     * @param eventType
     */
    public EventContainer(Class<T> eventType) {
        this.eventType = eventType;
        this.allEvents = new LinkedList<>();
    }

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    @Override
    public void update(Object eventInfo) {

        if(eventType.isInstance(eventInfo)) {
            //this.eventInfo = eventType.cast(eventInfo);
            this.allEvents.add(eventType.cast(eventInfo));
        }
    }

    /**
     * Gets the most recent event which the observer recieved.
     * @return The most recent event. Returns null if nothing has been recieved yet.
     */
    public T getEventInfo() {
        if(allEvents.size() > 0) {
            return allEvents.getLast();
        }
        else {
            return null;
        }
    }

    /**
     * Gets the list of all events. Is never null, and cannot be
     * modified.
     * @return All of the events which this observer has recieved.
     */
    public List<T> getAllEvents() {
        return Collections.unmodifiableList(allEvents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventContainer<?> that = (EventContainer<?>) o;
        return Objects.equals(eventType, that.eventType) &&
                Objects.equals(getAllEvents(), that.getAllEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, getAllEvents());
    }

    @Override
    public String toString() {
        return "EventContainer{" +
                "eventType=" + eventType +
                ", allEvents=" + allEvents +
                '}';
    }
}
