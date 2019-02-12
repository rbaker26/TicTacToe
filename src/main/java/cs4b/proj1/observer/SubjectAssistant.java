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
 * @param <T> The enum type which will be used to select event modes.
 * @author Daniel Edwards
 */
public final class SubjectAssistant<T extends Enum<T>> implements ISubject<T> {

    // TODO Try EnumMap again
    /**
     * Do not reference this directly; use the private getObserverSet method.
     */
    private HashMap<T, Set<IObserver>> observers;

    //***************************************************************************
    /**
     * Construct a new assistant.
     *
     * @author Daniel Edwards
     */
    public SubjectAssistant() {
        observers = new HashMap<>();
        //enums.forEach((Object val) -> observers.put((T)val, new ArrayList<>()));
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Calls the update function on each of the observers which have subscribed
     * using the given mode.
     *
     * @param mode Mode which to limit for sending events.
     * @param eventInfo Info object to send to the event. Can be null.
     * @author Daniel Edwards
     */
    public void triggerUpdate(T mode, Object eventInfo) {
        getObserverSet(mode).forEach((IObserver o) -> o.update(eventInfo));
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Adds a new observer to the list. Duplicates are ignored, and null
     * observers aren't allowed.
     *
     * @param newObserver Observer to be added.
     * @param mode The subject-specific mode.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    @Override
    public void subscribe(IObserver newObserver, T mode) {
        if(newObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        getObserverSet(mode).add(newObserver);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Removes an old observer. Does nothing if the observer isn't already
     * subscribed for the current mode. The oldObserver must not be null.
     *
     * @param oldObserver Observer to unsubscribe.
     * @param mode The subject-specific mode.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribe(IObserver oldObserver, T mode) {
        if(oldObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        getObserverSet(mode).remove(oldObserver);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Removes an old observer from all update modes. Does nothing if the
     * observer is unsubscribed. The oldObserver must not be null.
     *
     * @param oldObserver Observer to unsubscribe.
     * @throws NullPointerException If newObserver is null.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribeAll(IObserver oldObserver) {

        if(oldObserver == null) {
            throw new NullPointerException("Can't have a null observer.");
        }

        // We want to run through every list we have so that we can
        // prune all references to oldObserver.
        observers.forEach(
                (T mode, Collection<IObserver> obsList) -> obsList.remove(oldObserver)
        );
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Gets the set of observers associated with the given mode. The set
     * within the observers HashMap created if it doesn't already exist.
     *
     * @param mode The mode to get the set from.
     * @return The set of observers listening on the given mode.
     * @author Daniel Edwards
     */
    private Set<IObserver> getObserverSet(T mode) {
        Set<IObserver> observerSet = observers.get(mode);

        if(observerSet == null) {
            observerSet = new HashSet<>();
            observers.put(mode, observerSet);
        }

        return observerSet;
    }
    //***************************************************************************
}
