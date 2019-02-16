package cs4b.proj1.observer;


import java.util.*;

/**
 * Helps objects implement ISubject. This provides functions which fulfill the
 * ISubject interface, and then a helper function to call the update functions
 * on each of the subscribed observers.
 * <p>
 * This has been marked final as it makes no sense to inherit from it. It can
 * be treated as a subject itself, so the owner could observe it, but that
 * seems overly convoluted.
 *
 * @author Daniel Edwards
 */
public final class SubjectAssistant implements ISubject {

    /**
     * Do not reference this directly; use the private getObserverSet method.
     */
    private Set<IObserver> observers;

    //***************************************************************************
    /**
     * Construct a new assistant.
     *
     * @author Daniel Edwards
     */
    public SubjectAssistant() {
        observers = new HashSet<>();
        //enums.forEach((Object val) -> observers.put((T)val, new ArrayList<>()));
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Calls the update function on each of the observers which have subscribed
     * using the given mode.
     *
     * @param eventInfo Info object to send to the event. Can be null.
     * @author Daniel Edwards
     */
    public void triggerUpdate(Object eventInfo) {
        observers.forEach((IObserver o) -> o.update(eventInfo));
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Adds a new observer to the list. Duplicates are ignored, and null
     * observers aren't allowed.
     *
     * @param newObserver Observer to be added.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    @Override
    public void subscribe(IObserver newObserver) {
        if(newObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        observers.add(newObserver);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Removes an old observer. Does nothing if the observer isn't already
     * subscribed for the current mode. The oldObserver must not be null.
     *
     * @param oldObserver Observer to unsubscribe.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribe(IObserver oldObserver) {
        if(oldObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        observers.remove(oldObserver);
    }
    //***************************************************************************


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectAssistant that = (SubjectAssistant) o;
        return Objects.equals(observers, that.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observers);
    }

    @Override
    public String toString() {
        return "SubjectAssistant{" +
                observers.size() + " observers" +
                '}';
    }
}
