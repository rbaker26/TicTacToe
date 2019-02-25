package cs4b.proj1.observer;


import java.io.Serializable;
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
@Deprecated
public final class SubjectAssistant implements ISubject, Serializable {

    /**
     * Do not reference this directly; use the private getObserverSet method.
     */
    transient private Set<IObserver> observers;

    //***************************************************************************
    /**
     * Construct a new assistant.
     *
     * @author Daniel Edwards
     */
    public SubjectAssistant() {
    }
    //***************************************************************************

    private Set<IObserver> getObservers() {
        if(observers == null) {
            observers = new HashSet<>();
        }

        return observers;
    }

    //***************************************************************************
    /**
     * Calls the update function on each of the observers which have subscribed
     * using the given mode.
     *
     * @param eventInfo Info object to send to the event. Can be null.
     * @author Daniel Edwards
     */
    public void triggerUpdate(Object eventInfo) {
        getObservers().forEach((IObserver o) -> o.update(eventInfo));
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
    public void addSubscriber(IObserver newObserver) {

        if(newObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        getObservers().add(newObserver);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Removes an old observer. Does nothing if the observer isn't already
     * subscribed for the current mode. The oldObserver must not be null.
     *
     * @param oldObserver Observer to removeSubscriber.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    public void removeSubscriber(IObserver oldObserver) {

        if(oldObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        getObservers().remove(oldObserver);
    }
    //***************************************************************************


    public boolean hasSubscribers() {
        return getObservers().size() > 0;
    }

    // We do NOT want to override equals, toString, and hashCode for this class.
    // Weird things happen... such as stack overflows.


    @Override
    public String toString() {
        return "SubjectAssistant with " + getObservers().size() + " subscribers";
    }
}
