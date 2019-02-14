package cs4b.proj1;

import cs4b.proj1.observer.*;

public class HPCLocal implements PlayerBehavior, ISubject, IObserver {

    //region ISubject *************************************************************
    private SubjectAssistant subjAssist;

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
     * @author Daniel Edwards
     */
    @Override
    public void subscribe(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.subscribe(observer);
    }

    /**
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribe(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.unsubscribe(observer);
    }

    //endregion ISubject ***********************************************************

    public HPCLocal() {
    }

    @Override
    public void getMove(Board b, char token) {
        //subjAssist.triggerUpdate();
    }

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    @Override
    public void update(Object eventInfo) {
        if(eventInfo instanceof BoardGUI.SelectedSpaceInfo) {
            BoardGUI.SelectedSpaceInfo info = (BoardGUI.SelectedSpaceInfo) eventInfo;

            System.out.println(info.toString());
        }

    }
}
