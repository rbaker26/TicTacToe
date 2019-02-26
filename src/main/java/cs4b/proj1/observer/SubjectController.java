package cs4b.proj1.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a master-class which handles subjects, etc.
 */
final public class SubjectController {

    static private HashMap<ISubject, Set<IObserver>> subjMap = new HashMap<>();

    private SubjectController() {
        /* Do not delete */
    }

    /**
     * Adds the observer to the list associated with the subject.
     * The observer will begin recieving updates from the subject.
     * @param subject
     * @param observer
     */
    static public void addObserver(ISubject subject, IObserver observer) {

        if(subject == null) {
            throw new NullPointerException("Subject is null");
        }
        else if (observer == null) {
            throw new NullPointerException("Observer is null");
        }

        Set<IObserver> specificObservers = subjMap.get(subject);
        if(specificObservers == null) {
            specificObservers = createObserverSet();
            subjMap.put(subject, specificObservers);
        }

        specificObservers.add(observer);
    }

    /**
     * Remove an observer from the list associated with the subject.
     * The observer will no longer recieve updates from the subject.
     * @param subject Subject which is currently observed.
     * @param observer Observer which is being observed.
     */
    static public void removeObserver(ISubject subject, IObserver observer) {

        if(subject == null) {
            throw new NullPointerException("Subject is null");
        }
        else if (observer == null) {
            throw new NullPointerException("Observer is null");
        }

        Set<IObserver> specificObservers = subjMap.get(subject);
        if(specificObservers != null) {
            specificObservers.remove(observer);

            // If this was the last observer, we want to DROP the reference
            // to the subject. Otherwise, we'll potentially leak memory.
            if(specificObservers.isEmpty()) {
                subjMap.remove(subject);
            }
        }
    }

    /**
     * Sends out an event to all observers listening to the subject.
     * @param subject The subject being observed.
     * @param eventInfo The event to send to observers.
     */
    static public void triggerUpdate(ISubject subject, Object eventInfo) {
        if(subject == null) {
            throw new NullPointerException("Subject is null");
        }

        Set<IObserver> specificObservers = subjMap.get(subject);
        if(specificObservers != null) {
            for(IObserver obs : specificObservers) {
                obs.update(eventInfo);
            }
        }
    }

    /**
     * Removes all observers from a subject. All those observers
     * will stop recieving updates from the subject. This is meant
     * mostly for cleanup.
     * @param subject Subject to remove observers from.
     */
    static public void clearObservers(ISubject subject) {
        if(subject == null) {
            throw new NullPointerException("Subject is null");
        }

        subjMap.remove(subject);
    }

    /**
     * Checks if the subject has any observers
     * @param subject Subject to check
     * @return Returns true if the subject has observers; false otherwise.
     */
    static public boolean hasObservers(ISubject subject) {
        if(subject == null) {
            throw new NullPointerException("Subject is null");
        }

        Set<IObserver> specificObservers = subjMap.get(subject);
        return specificObservers != null && !specificObservers.isEmpty();
    }

    /**
     * The purpose of this method is to have a dedicated location for
     * creating new sets of observers. If we need to create this set at
     * any point, this should be the method which gets called.
     * @return The new set. This is NOT added to subjMap.
     */
    static private Set<IObserver> createObserverSet() {
        return new HashSet<IObserver>();
    }

}
