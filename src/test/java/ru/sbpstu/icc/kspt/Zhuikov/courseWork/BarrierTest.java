package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Field;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Player;
import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.*;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.ItemType;

import static org.junit.Assert.assertEquals;

public class BarrierTest {

    @Test
    public void testBarrierSet() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(5, 3, BarrierPosition.VERTICAL);

        assertEquals(ItemType.BARRIER, field.getItem(5, 3).getType());
        assertEquals(ItemType.BARRIER, field.getItem(4, 3).getType());
        assertEquals(ItemType.BARRIER, field.getItem(6, 3).getType());

        player.makeMove(13, 11, BarrierPosition.HORIZONTAL);

        assertEquals(ItemType.BARRIER, field.getItem(13, 10).getType());
        assertEquals(ItemType.BARRIER, field.getItem(13, 11).getType());
        assertEquals(ItemType.BARRIER, field.getItem(13, 12).getType());
    }


    @Test(expected = ImpossibleToSetException.class)
    public void testBlackCellSet() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);
        player.makeMove(2, 8, BarrierPosition.HORIZONTAL);
    }

    @Test(expected = ImpossibleToSetException.class)
    public void testSetBetweenBlackCells() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);
        player.makeMove(7, 12, BarrierPosition.VERTICAL);
    }

    @Test(expected = FieldCoordinatesException.class)
    public void testWrongCoordinates() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);
        player.makeMove(0, 1, BarrierPosition.VERTICAL);
    }

    @Test(expected = CellIsNotEmptyException.class)
    public void testImpossibleSet() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.BOTTOM;
        player.createPlayer(field);

        player.makeMove(5, 3, BarrierPosition.VERTICAL);
        player.makeMove(5, 2, BarrierPosition.HORIZONTAL);
    }

    @Test (expected = NoBarriersException.class)
    public void testNoBarriers() throws FieldItemException, NoBarriersException {

        Field field = new Field(9);
        Player player = Player.TOP;
        player.createPlayer(field);

        for (int i = 1; i < 16; i+=2) {
            player.makeMove(1, i, BarrierPosition.VERTICAL); // 8 barriers
        }
        player.makeMove(5, 1, BarrierPosition.VERTICAL);
        player.makeMove(5, 3, BarrierPosition.VERTICAL);

        player.makeMove(5, 5, BarrierPosition.VERTICAL);
    }
}
