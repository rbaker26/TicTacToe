package cs4b.proj1;

import cs4b.proj1.observer.*;

public class HPCLocal implements PlayerBehavior, ISubject<HPCLocal.SubjectMode> {

    //region ISubject *************************************************************
    private SubjectAssistant<HPCLocal.SubjectMode> subjAssist;

    public enum SubjectMode {
        SelectedMove,
        AwaitingMove
    }

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
     * @param mode     The subject-specific mode.
     * @author Daniel Edwards
     */
    @Override
    public void subscribe(IObserver observer, SubjectMode mode) {
        // TODO Maybe needed for serializable?
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant<>();
        }

        subjAssist.subscribe(observer, mode);
    }

    /**
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @param mode     The subject-specific mode.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribe(IObserver observer, SubjectMode mode) {
        // TODO Maybe needed for serializable?
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant<>();
        }

        subjAssist.unsubscribe(observer, mode);
    }

    /**
     * Unsubcribes the given observer entirely, causing them to no longer
     * recieve any updates from the subject.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribeAll(IObserver observer) {
        // TODO Maybe needed for serializable?
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant<>();
        }

        subjAssist.unsubscribeAll(observer);
    }
    //endregion ISubject ***********************************************************

    public HPCLocal() {
        subjAssist = new SubjectAssistant<>();
    }

    @Override
    public void getMove(Board b, char token) {
        //subjAssist.triggerUpdate();
    }
}
