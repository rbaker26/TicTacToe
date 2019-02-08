package cs4b.proj1.observer;


import java.util.*;

/** SubjectAssistant
 * Helps objects implement ISubject. This provides functions which fulfill the
 * ISubject interface, and then a helper function to call the update functions
 * on each of the subscribed observers.
 *
 * This has been marked final as it makes no sense to inherit from it. It can
 * be treated as a subject itself, so the owner could observe it, but that
 * seems overly convoluted.
 *
 * @param <T> The enum type which will be used to select event modes.
 * @author Daniel Edwards
 */
public final class SubjectAssistant<T extends Enum<T>> implements ISubject<T> {

    // TODO Try EnumMap again
    // We are NOT using a Set because the sets require the
    private HashMap<T, Set<IObserver>> observers;

    //***************************************************************************
    /** SubjectAssistant
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
    /** triggerUpdate
     * Calls the update function on each of the observers which have subscribed
     * using the given mode.
     *
     * @param mode Mode which to limit for sending events.
     * @param eventInfo Info object to send to the event. Can be null.
     * @author Daniel Edwards
     */
    public void triggerUpdate(T mode, Object eventInfo) {
        getSubscribers(mode).forEach((IObserver o) -> o.update(eventInfo));
    }
    //***************************************************************************

    //***************************************************************************
    /** subscribe
     * Adds a new observer to the list.
     *
     * @param newObserver
     * @param mode The subject-specific mode.
     */
    @Override
    public void subscribe(IObserver newObserver, T mode) {
        //Set<IObserver> list = getSubscribers(mode);
        getSubscribers(mode).add(newObserver);

        //if(!list.contains(newObserver)) {
        //}
    }
    //***************************************************************************

    //***************************************************************************
    @Override
    public void unsubscribe(IObserver oldObserver, T mode) {
        getSubscribers(mode).remove(oldObserver);
    }
    //***************************************************************************

    //***************************************************************************
    @Override
    public void unsubscribeAll(IObserver oldObserver) {
        // We want to run through every list we have so that we can
        // prune all references to oldObserver.
        observers.forEach(
                (T mode, Collection<IObserver> obsList) -> obsList.remove(oldObserver)
        );
    }
    //***************************************************************************


    //***************************************************************************
    private Set<IObserver> getSubscribers(T mode) {
        Set<IObserver> observerSet = observers.get(mode);

        if(observerSet == null) {
            observerSet = new HashSet<>();
            observers.put(mode, observerSet);
        }

        return observerSet;
    }
    //***************************************************************************
}
