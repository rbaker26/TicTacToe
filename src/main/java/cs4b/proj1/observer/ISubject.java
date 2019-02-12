package cs4b.proj1.observer;

/**
 * Implementors of this can be observed by those who implement the IObserver class.
 *
 * @param <T> Some means of identifying types of events which can be fired.
 *            Usually an enum but not necessarily one.
 * @see IObserver
 * @author Daniel Edwards
 */
public interface ISubject<T> {

    /**
     * Subscribes the given observer, causing its update function to be called
     * for the given event. As there can be a variety of modes, subjects are
     * expected to implement some kind of object (e.g. an enum) to allow
     * subscribers to select what kind of events they are interested in.
     * <p>
     * If an observer attempts to subscribe itself more than once, the first
     * subscription should be replaced. (Unless they are with differenct
     * modes, of course.)
     *
     * @param observer The observer which will be subscribed.
     * @param mode The subject-specific mode.
     * @author Daniel Edwards
     */
    void subscribe(IObserver observer, T mode);

    /**
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @param mode The subject-specific mode.
     * @author Daniel Edwards
     */
    void unsubscribe(IObserver observer, T mode);

    /**
     * Unsubcribes the given observer entirely, causing them to no longer
     * recieve any updates from the subject.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    void unsubscribeAll(IObserver observer);

}
