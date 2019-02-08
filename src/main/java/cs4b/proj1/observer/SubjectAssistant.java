package cs4b.proj1.observer;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    private HashMap<T, List<IObserver>> observers;

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
        getSubscriberList(mode).forEach((IObserver o) -> o.update(eventInfo));
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
        List<IObserver> list = getSubscriberList(mode);

        if(!list.contains(newObserver)) {
            list.add(newObserver);
        }
    }
    //***************************************************************************

    //***************************************************************************
    @Override
    public void unsubscribe(IObserver oldObserver, T mode) {
        getSubscriberList(mode).remove(oldObserver);
    }
    //***************************************************************************

    //***************************************************************************
    @Override
    public void unsubscribeAll(IObserver oldObserver) {
        // We want to run through every list we have so that we can
        // prune all references to oldObserver.
        observers.forEach(
                (T mode, List<IObserver> obsList) -> obsList.remove(oldObserver)
        );
    }
    //***************************************************************************


    //***************************************************************************
    private List<IObserver> getSubscriberList(T mode) {
        List<IObserver> obsList = observers.get(mode);

        if(obsList == null) {
            obsList = observers.put(mode, new LinkedList<>());
        }

        return obsList;
    }
    //***************************************************************************
}
