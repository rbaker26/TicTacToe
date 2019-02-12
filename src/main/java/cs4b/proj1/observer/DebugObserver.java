package cs4b.proj1.observer;

/**
 * This is a dummy observer. Whenever it recieves an event, prints the
 * object's toString to the console.
 */
public final class DebugObserver implements IObserver {

    String observerName;

    public DebugObserver() {
        this("unnamed observer");
    }

    public DebugObserver(String name) {
        observerName = name;
    }

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    @Override
    public void update(Object eventInfo) {
        if(eventInfo == null) {
            System.out.println(observerName + ": Subject sent us null");
        }
        else {
            System.out.println(observerName + ": recieved " + eventInfo.toString());
        }
    }
}
