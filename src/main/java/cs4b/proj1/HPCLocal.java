package cs4b.proj1;

import cs4b.proj1.observer.*;
import java.io.Serializable;
import java.util.Objects;


public class HPCLocal implements PlayerBehavior, ISubject, IObserver {

    //region ISubject *************************************************************

    /**
     * Gets sent out to observers when it's this player's turn.
     */
    public static class TurnBegin {
    }
    //endregion ISubject ***********************************************************

    public HPCLocal() {
    }

    @Override
    public void getMove(Board b, char token) {
        //subjAssist.triggerUpdate();

        // It's up to whatever is in charge of input to deal with this.
        SubjectController.triggerUpdate(this, new TurnBegin());
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

            SubjectController.triggerUpdate(
                    this, new PlayerBehavior.MoveInfo(info.getX(), info.getY())
            );
        }
    }
}
