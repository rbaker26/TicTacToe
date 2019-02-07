package cs4b.proj1.observer;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

public class SubjectAssistant<T extends Enum<T>> {

    // TODO Try EnumMap again
    private HashMap<T, ArrayList<IObserver>> observers;

    public SubjectAssistant(EnumSet<?> enums) {
        observers = new HashMap<>();
        enums.forEach((Object val) -> observers.put((T)val, new ArrayList<>()));
    }

    public void triggerUpdate(T mode, Object eventInfo) {
        observers.get(mode).forEach((IObserver o) -> o.update(eventInfo));
    }

    public void subscribe(IObserver newObserver, T mode) {
        ArrayList list = observers.get(mode);

        if(!list.contains(newObserver)) {
            list.add(newObserver);
        }
    }

    public void unsubscribe(IObserver oldObserver, T mode) {
        observers.get(mode).remove(oldObserver);
    }

    public void unsubscribeAll(IObserver oldObserver) {
        observers.forEach(
                (T mode, ArrayList<IObserver> set) -> set.remove(oldObserver)
        );
    }
}
