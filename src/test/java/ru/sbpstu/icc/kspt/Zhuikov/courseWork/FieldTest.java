package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;
import ru.spbstu.icc.kspt.zhuikov.quoridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;

import static org.junit.Assert.assertEquals;

public class FieldTest {

    @Test
    public void testInitialization() {

        QuoridorField field = new QuoridorField(9);

        assertEquals(CellColor.BLACK, field.getColor(0, 0));
        assertEquals(CellColor.WHITE, field.getColor(0, 1));
        assertEquals(CellColor.WHITE, field.getColor(1, 1));
        assertEquals(CellColor.BLACK, field.getColor(2, 2));
    }

    @Test
    public void testPathBetweenCells1() {  // пустое поле

        QuoridorField field = new QuoridorField(9);
        assertEquals(false, field.isRowAvailable(new Coordinates(16, 8), 0).empty());
        assertEquals(false, field.isRowAvailable(new Coordinates(0, 8), 10).empty());
    }

    @Test
    public void testPathBetweenCells2() throws FieldItemException { // "перегородка" по вертикали

        QuoridorField field = new QuoridorField(9);

        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(i, 7, BarrierPosition.VERTICAL));
        }
        field.setItem(new Barrier(15, 7, BarrierPosition.HORIZONTAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));

        assertEquals(false, field.isRowAvailable(new Coordinates(16, 8), 0).empty());
        assertEquals(false, field.isRowAvailable(new Coordinates(0, 16), 0).empty());
        assertEquals(false, field.isRowAvailable(new Coordinates(0, 16), 16).empty());
    }

    @Test
    public void testPathBetweenCells3() throws FieldItemException { // "перегородка" по горизонтали

        QuoridorField field = new QuoridorField(9);
        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(7, i, BarrierPosition.HORIZONTAL));
        }
        field.setItem(new Barrier(7, 15, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(5, 15, BarrierPosition.HORIZONTAL));

        assertEquals(false, field.isRowAvailable(new Coordinates(0, 4), 2).empty());
        assertEquals(true, field.isRowAvailable(new Coordinates(0, 4), 10).empty());
        assertEquals(true, field.isRowAvailable(new Coordinates(16, 6), 0).empty());
    }

    @Test
    public void testPathBetweenCells4() throws FieldItemException { // закрытая фишка

        QuoridorField field = new QuoridorField(9);
        field.setItem(new Barrier(15, 7, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(13, 8, BarrierPosition.HORIZONTAL));

        assertEquals(true, field.isRowAvailable(new Coordinates(16, 8), 6).empty());
        assertEquals(false, field.isRowAvailable(new Coordinates(16, 8), 16).empty());
        assertEquals(false, field.isRowAvailable(new Coordinates(0, 8), 16).empty());
    }

}
