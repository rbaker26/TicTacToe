package cs4b.proj1.observer;

/**
 * Implementors are able to observer ISubject objects.
 *
 * @see ISubject
 * @author Daniel Edwards
 */
public interface IObserver {

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    void update(Object eventInfo);
}
