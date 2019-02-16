package cs4b.proj1;

import cs4b.proj1.observer.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test public void testMakeMove() {
        Player testPlayer = new Player('X', "Nameless", new NPCEasy());
        EventContainer<Player.MoveInfo> container = new EventContainer<>(Player.MoveInfo.class);

        Board b = new Board();

        testPlayer.addSubscriber(container);
        testPlayer.makeMove(b);
        assertEquals(container.getEventInfo().getSource(), testPlayer);
        //System.out.println(container.getEventInfo());

    }
}
