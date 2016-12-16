package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Marker;

import static org.junit.Assert.assertEquals;

public class MarkerTest {

    @Test
    public void testInitialCoordinates() {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player1 = QuoridorPlayer.TOP;
        QuoridorPlayer player2 = QuoridorPlayer.BOTTOM;
        player1.createPlayer(field);
        player2.createPlayer(field);

        assertEquals(ItemType.MARKER, field.getItem(0, 8).getType());
        assertEquals(ItemType.MARKER, field.getItem(16, 8).getType());
    }

    @Test
    public void testMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);

        player.makeMove(14, 8);

        assertEquals(ItemType.MARKER, field.getItem(14, 8).getType());

    }

    @Test (expected = FieldBoundsException.class)
    public void testMovingOutOfBounds() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);

        player.makeMove(18, 8);

    }

    @Test (expected = TooLongDistanceException.class)
    public void testFarMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);

        player.makeMove(14, 6);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleMoving() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);

        player.makeMove(15, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testMovingToMarkerCell() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);

        player.makeMove(16, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testJumpOverBarrier() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);
        Barrier barrier = new Barrier(15, 8, BarrierPosition.HORIZONTAL);
        field.setItem(barrier);

        player.makeMove(14, 8);
    }

    @Test
    public void testJumpOverMarker_Forward() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);
        field.setItem(new Marker(14, 8));

        player.makeMove(12, 8);
        assertEquals(ItemType.MARKER, field.getItem(12, 8).getType());
    }

    @Test
    public void testJumpOverMarker_Diagonal() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);
        field.setItem(new Marker(14, 8));

        player.makeMove(14, 10);
        assertEquals(ItemType.MARKER, field.getItem(14, 10).getType());
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleJumpOverMarker_Forward() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);
        field.setItem(new Marker(14, 8));
        field.setItem(new Barrier(13, 8, BarrierPosition.HORIZONTAL));

        player.makeMove(12, 8);
    }

    @Test (expected = ImpossibleToSetItemException.class)
    public void testImpossibleJumpOverMarker_Diagonal() throws FieldItemException {

        QuoridorField field = new QuoridorField(9);
        QuoridorPlayer player = QuoridorPlayer.BOTTOM;
        player.createPlayer(field);
        field.setItem(new Marker(14, 8));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));

        player.makeMove(14, 10);
    }
}
