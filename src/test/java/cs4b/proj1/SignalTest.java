package cs4b.proj1;

import org.junit.Test;
import java.util.function.Consumer;
import static org.junit.Assert.*;

public class SignalTest {

    private int c;

    public void addCount(Integer i) {
        c += i;
    }

    public void incrementCount() {
        c++;
    }

    @Test public void testEmit() {
        c = 0;

        Signal<Integer> testSignal = new Signal<>();
        //Consumer<Integer> slot1 = (Integer i)->counter += i;
        Consumer<Integer> slot1 = this::addCount;
        Runnable slot2 = this::incrementCount;

        testSignal.connect(slot1);
        testSignal.emit(5);
        assertEquals(c, 5);

        testSignal.connect(slot2);
        testSignal.emit(2); // Do c + 2 + 1 = 8
        assertEquals(c, 8);

        testSignal.disconnect(slot1);
        testSignal.emit(100);
        assertEquals(c, 9);

        testSignal.disconnect(slot2);
        testSignal.emit(100);
        assertEquals(c, 9);

    }


    @Test public void testDoubleSlot() {
        c = 0;

        Signal<Integer> testSignal = new Signal<>();
        Consumer<Integer> slot1 = this::addCount;

        testSignal.connect(slot1);
        testSignal.emit(-6);
        assertEquals(c, -6);

        testSignal.connect(slot1);
        testSignal.emit(10);
        assertEquals(c, 4);

        testSignal.disconnect(slot1);
        testSignal.emit(30);
        assertEquals(c, 4);

        testSignal.disconnect(slot1);
        testSignal.emit(30);
        assertEquals(c, 4);
    }


} // End class
