package cs4b.proj1;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Signal<T> implements Cloneable {
    private ArrayList<Consumer<T>> slots;
    private ArrayList<Runnable> generalSlots;

    //***************************************************************************
    /**
     * Creates a signal object without any slots connected to it.
     * @author Daniel Edwards
     */
    public Signal() {
        slots = new ArrayList<>();
        generalSlots = new ArrayList<>();
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Registers the consumer function to the slot. This added to the list of
     * slots which will be called when the signal is emitted.
     * @param newSlot Slot to be triggered when the signal is emitted.
     * @author Daniel Edwards
     */
    public void connect(Consumer<T> newSlot) {
        if(!slots.contains(newSlot)) {
            slots.add(newSlot);
        }
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Registers the consumer function to the slot. This added to the list of
     * slots which will be called when the signal is emitted.
     * @param newSlot Slot to be triggered when the signal is emitted.
     * @author Daniel Edwards
     */
    public void connect(Runnable newSlot) {
        if(!generalSlots.contains(newSlot)) {
            generalSlots.add(newSlot);
        }
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Unregisters the slot so that the function no longer gets calle.d
     * @param oldSlot Slot to be removed. It will no longer be called upon emit.
     * @author Daniel Edwards
     */
    public void disconnect(Consumer<T> oldSlot) {
        slots.remove(oldSlot);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Unregisters the slot so that the function no longer gets calle.d
     * @param oldSlot Slot to be removed. It will no longer be called upon emit.
     * @author Daniel Edwards
     */
    public void disconnect(Runnable oldSlot) {
        generalSlots.remove(oldSlot);
    }
    //***************************************************************************

    //***************************************************************************
    /**
     * Emits the signal, calling every connected slot.
     * @param eventInfo Whatever info is relevent to the slot.
     * @author Daniel Edwards
     */
    public void emit(T eventInfo) {
        for(Consumer<T> slot : slots) {
            slot.accept(eventInfo);
        }

        for(Runnable slot : generalSlots) {
            slot.run();
        }
    }
    //***************************************************************************
}
