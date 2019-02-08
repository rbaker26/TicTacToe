package cs4b.proj1.observer;

import javafx.util.Pair;
import org.junit.Test;

import cs4b.proj1.observer.*;
import static org.junit.Assert.*;

public class SubjectAssistantTest {

    private enum Modes {
        mode1, mode2;
    }

    private class TestObs implements IObserver {

        public int val;

        public TestObs(int start) {
            val = start;
        }

        @Override
        public void update(Object eventInfo) {
            if(eventInfo instanceof Integer) {
                val += (Integer) eventInfo;
            }
        }
    }

    @Test
    public void testSubscribe() {
        SubjectAssistant<Modes> sa = new SubjectAssistant<>();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);
        TestObs o3 = new TestObs(-4);

        sa.subscribe(o1, Modes.mode1);
        sa.subscribe(o1, Modes.mode2);
        sa.subscribe(o2, Modes.mode2);

        sa.triggerUpdate(Modes.mode1, "Hi");
        assertEquals(o1.val, 0);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, -4);

        sa.triggerUpdate(Modes.mode2, "Hi");
        assertEquals(o1.val, 0);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, -4);

        sa.triggerUpdate(Modes.mode1, 10);
        assertEquals(o1.val, 10);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, -4);

        sa.triggerUpdate(Modes.mode2, 20);
        assertEquals(o1.val, 30);
        assertEquals(o2.val, 22);
        assertEquals(o3.val, -4);
    }

    @Test
    public void testDuplicateSubscribe() {
        SubjectAssistant<Modes> sa = new SubjectAssistant<>();
        TestObs o1 = new TestObs(0);

        sa.subscribe(o1, Modes.mode1);
        sa.subscribe(o1, Modes.mode1);

        sa.triggerUpdate(Modes.mode1, 10);
        assertEquals(o1.val, 10);
    }

    @Test
    public void testUnsubscribe() {
        SubjectAssistant<Modes> sa = new SubjectAssistant<>();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);
        TestObs o3 = new TestObs(-4);

        sa.subscribe(o1, Modes.mode1);
        sa.subscribe(o2, Modes.mode2);
        sa.subscribe(o3, Modes.mode1);
        sa.subscribe(o3, Modes.mode2);

        sa.triggerUpdate(Modes.mode1, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, 1);

        sa.unsubscribe(o1, Modes.mode2);    // Note that o1 is not subscribed to mode2!
        sa.triggerUpdate(Modes.mode2, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 7);
        assertEquals(o3.val, 6);

        sa.unsubscribe(o1, Modes.mode1);
        sa.triggerUpdate(Modes.mode1, 1);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 7);
        assertEquals(o3.val, 7);

        sa.unsubscribe(o3, Modes.mode1);
        sa.triggerUpdate(Modes.mode1, 1);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 7);
        assertEquals(o3.val, 7);
    }

    @Test
    public void testUnsubscribeAll() {
        SubjectAssistant<Modes> sa = new SubjectAssistant<>();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);
        TestObs o3 = new TestObs(-4);

        sa.subscribe(o1, Modes.mode1);
        sa.subscribe(o2, Modes.mode2);
        sa.subscribe(o3, Modes.mode1);
        sa.subscribe(o3, Modes.mode2);

        sa.triggerUpdate(Modes.mode1, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, 1);

        sa.unsubscribeAll(o3);
        sa.triggerUpdate(Modes.mode2, -2);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 0);
        assertEquals(o3.val, 1);

        sa.unsubscribeAll(o1);
        sa.triggerUpdate(Modes.mode1, 1);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 0);
        assertEquals(o3.val, 1);
    }
}
