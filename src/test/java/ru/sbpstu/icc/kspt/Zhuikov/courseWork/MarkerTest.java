package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;

import ru.spbstu.icc.kspt.zhuikov.qouridor.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Field;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Player;
import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.BarrierPosition;

import static org.junit.Assert.assertEquals;

public class MarkerTest {

    @Test
    public void testInitialCoordinates() {

        Field field = new Field(9);
        Player player1 = Player.TOP;
        Player player2 = Player.BOTTOM;
        player1.createPlayer(field);
        player2.createPlayer(field);

        assertEquals(new Coordinates(0, 8), player1.getMarker().getCoordinates());
        assertEquals(new Coordinates(16, 8), player2.getMarker().getCoordinates());
    }

    @Test
    public void testMoving() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(14, 8);

        assertEquals(new Coordinates(14, 8), player.getMarker().getCoordinates());

    }

    @Test (expected = FieldCoordinatesException.class)
    public void testMovingOutOfBounds() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(18, 8);

    }

    @Test (expected = TooLongDistanceException.class)
    public void testFarMoving() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(14, 6);
    }

    @Test (expected = ImpossibleToSetException.class)
    public void testImpossibleMoving() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(15, 8);
    }

    @Test (expected = SetToSameCellException.class)
    public void testMovingToMarkerCell() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(16, 8);
    }

    @Test (expected = ImpossibleToSetException.class)
    public void testJumpOverBarrier() throws FieldItemException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        Barrier barrier = new Barrier(15, 8, BarrierPosition.HORIZONTAL);
        field.setItem(barrier);

        player.makeMove(14, 8);
    }
}
