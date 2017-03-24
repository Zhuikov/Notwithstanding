package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.HumanPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.game.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;

import static org.junit.Assert.assertEquals;

public class MarkerTest {

    @Test
    public void testInitialCoordinates() {

        QuoridorField field = new QuoridorField(9);
        new HumanPlayer(field, PlayerPosition.TOP);
        new HumanPlayer(field, PlayerPosition.BOT);

        assertEquals(ItemType.MARKER, field.getItem(0, 8).getType());
        assertEquals(ItemType.MARKER, field.getItem(16, 8).getType());
    }

    @Test
    public void testMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);

        player.moveMarker(14, 8);

        assertEquals(ItemType.MARKER, field.getItem(14, 8).getType());

    }

    @Test (expected = FieldBoundsException.class)
    public void testMovingOutOfBounds() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);

        player.moveMarker(18, 8);

    }

    @Test (expected = TooLongDistanceException.class)
    public void testFarMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);

        player.moveMarker(14, 6);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);

        player.moveMarker(15, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testMovingToMarkerCell() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);

        player.moveMarker(16, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testJumpOverBarrier() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
        Barrier barrier = new Barrier(new Coordinates(15, 8), BarrierPosition.HORIZONTAL);
        field.setBarrier(barrier);

        player.moveMarker(14, 8);
    }

    @Test
    public void testJumpOverMarker_Forward() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
        field.setItem(new Marker(Owner.NOBODY), new Coordinates(14, 8));

        player.moveMarker(12, 8);
        assertEquals(ItemType.MARKER, field.getItem(12, 8).getType());
    }

    @Test
    public void testJumpOverMarker_Diagonal() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
        field.setItem(new Marker(Owner.NOBODY), new Coordinates(14, 8));

        player.moveMarker(14, 10);
        assertEquals(ItemType.MARKER, field.getItem(14, 10).getType());
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleJumpOverMarker_Forward() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
        field.setItem(new Marker(Owner.NOBODY), new Coordinates(14, 8));
        field.setBarrier(new Barrier(new Coordinates(13, 8), BarrierPosition.HORIZONTAL));

        player.moveMarker(12, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleJumpOverMarker_Diagonal() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
        field.setItem(new Marker(Owner.NOBODY), new Coordinates(14, 8));
        field.setBarrier(new Barrier(new Coordinates(15, 9), BarrierPosition.VERTICAL));

        player.moveMarker(14, 10);
    }
}
