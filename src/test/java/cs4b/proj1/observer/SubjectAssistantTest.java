package cs4b.proj1.observer;

import org.junit.Test;
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
        SubjectAssistant sa = new SubjectAssistant();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);
        TestObs o3 = new TestObs(-4);

        sa.addSubscriber(o1);
        sa.addSubscriber(o2);

        sa.triggerUpdate("Hi");
        assertEquals(o1.val, 0);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, -4);

        sa.triggerUpdate(10);
        assertEquals(o1.val, 10);
        assertEquals(o2.val, 12);
        assertEquals(o3.val, -4);

    }

    @Test
    public void testDuplicateSubscribe() {
        SubjectAssistant sa = new SubjectAssistant();
        TestObs o1 = new TestObs(0);

        sa.addSubscriber(o1);
        sa.addSubscriber(o1);

        sa.triggerUpdate(10);
        assertEquals(o1.val, 10);
    }

    @Test
    public void testUnsubscribe() {
        SubjectAssistant sa = new SubjectAssistant();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);

        sa.addSubscriber(o1);
        sa.addSubscriber(o2);

        sa.triggerUpdate(5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 7);

        sa.removeSubscriber(o1);
        sa.triggerUpdate(5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 12);

        sa.removeSubscriber(o2);
        sa.triggerUpdate(5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 12);


    }

    @Test
    public void subscribeNull() {
        SubjectAssistant sa = new SubjectAssistant();
        TestObs nullObs = null;

        try {
            sa.addSubscriber(nullObs);

            // This should NOT happen. It should throw an exception and skip past this.
            assertTrue(false);
        }
        catch(NullPointerException ex) {
            // All is good!
        }

        // This should not crash...
        sa.triggerUpdate(10);

        try {
            sa.removeSubscriber(nullObs);

            // This should NOT happen. It should throw an exception and skip past this.
            assertTrue(false);
        }
        catch(NullPointerException ex) {
            // All is good!
        }

    }
}
