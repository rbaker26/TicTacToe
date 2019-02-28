package cs4b.proj1.observer;

import org.junit.Test;
import static org.junit.Assert.*;

public class SubjectControllerTest {


    private enum Modes {
        mode1, mode2;
    }

    private static class TestObs implements IObserver {

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

    private static class TestSubject implements ISubject {

    }

    @Test
    public void testSubscribe() {
        TestSubject subj = new TestSubject();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);
        TestObs o3 = new TestObs(-4);

        SubjectController.addObserver(subj, o1);
        SubjectController.addObserver(subj, o2);

        SubjectController.triggerUpdate(subj, "Hi");
        assertEquals(o1.val, 0);
        assertEquals(o2.val, 2);
        assertEquals(o3.val, -4);

        SubjectController.triggerUpdate(subj, 10);
        assertEquals(o1.val, 10);
        assertEquals(o2.val, 12);
        assertEquals(o3.val, -4);

    }

    @Test
    public void testDuplicateSubscribe() {
        TestSubject subj = new TestSubject();
        TestObs o1 = new TestObs(0);

        SubjectController.addObserver(subj, o1);
        SubjectController.addObserver(subj, o1);

        SubjectController.triggerUpdate(subj,10);
        assertEquals(o1.val, 10);
    }

    @Test
    public void testUnsubscribe() {
        TestSubject subj = new TestSubject();
        TestObs o1 = new TestObs(0);
        TestObs o2 = new TestObs(2);

        SubjectController.addObserver(subj, o1);
        SubjectController.addObserver(subj, o2);

        SubjectController.triggerUpdate(subj, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 7);

        SubjectController.removeObserver(subj, o1);
        SubjectController.triggerUpdate(subj, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 12);

        SubjectController.removeObserver(subj, o2);
        SubjectController.triggerUpdate(subj, 5);
        assertEquals(o1.val, 5);
        assertEquals(o2.val, 12);


    }

    @Test
    public void subscribeNull() {
        TestSubject subj = new TestSubject();
        TestObs nullObs = null;

        try {
            SubjectController.addObserver(subj, nullObs);

            // This should NOT happen. It should throw an exception and skip past this.
            assertTrue(false);
        }
        catch(NullPointerException ex) {
            // All is good!
        }

        // This should not crash...
        SubjectController.triggerUpdate(subj, 10);

        try {
            SubjectController.removeObserver(subj, nullObs);

            // This should NOT happen. It should throw an exception and skip past this.
            assertTrue(false);
        }
        catch(NullPointerException ex) {
            // All is good!
        }

    }
}
