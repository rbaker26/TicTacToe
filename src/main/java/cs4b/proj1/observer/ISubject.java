package cs4b.proj1.observer;

public interface ISubject<T> {

    /** subscribe
     * Subscribes the given observer, causing its update function to be called
     * for the given event. As there can be a variety of modes, subjects are
     * expected to implement some kind of object (e.g. an enum) to allow
     * subscribers to select what kind of events they are interested in.
     *
     * If an observer attempts to subscribe itself more than once, the first
     * subscription should be replaced. (Unless they are with differenct
     * modes, of course.)
     *
     * @param observer The observer which will be subscribed.
     * @param mode The subject-specific mode.
     * @author Daniel Edwards
     */
    void subscribe(IObserver observer, T mode);

    /** unsubscribe
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @param mode The subject-specific mode.
     * @author Daniel Edwards
     */
    void unsubscribe(IObserver observer, T mode);

    /** unsubscribeAll
     * Unsubcribes the given observer entirely, causing them to no longer
     * recieve any updates from the subject.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    void unsubscribeAll(IObserver observer);
}
