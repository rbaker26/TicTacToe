package cs4b.proj1.observer;

import java.util.Objects;


/**
 * This is a stupid observer class which just stores whatever it recieves.
 * Meant mostly for testing, but could be useful elsewhere.
 *
 * @param <T> The type which the observer accepts. Other types are ignored.
 */
public class EventContainer<T> implements IObserver {
    private T eventInfo;
    private Class<T> eventType;

    /**
     * Due to limitations of java, when constructing one of these,
     * you MUST pass in the T.class, which is an object of type Class<T>.
     * This is needed so that we can typecast the event we recieve.
     *
     * @param eventType
     */
    public EventContainer(Class<T> eventType) {
        this.eventType = eventType;
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
            this.eventInfo = eventType.cast(eventInfo);
        }
    }

    public T getEventInfo() {
        return eventInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventContainer<?> that = (EventContainer<?>) o;
        return Objects.equals(getEventInfo(), that.getEventInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventInfo());
    }

    @Override
    public String toString() {
        return "EventContainer{" +
                "eventInfo=" + eventInfo +
                '}';
    }
}
