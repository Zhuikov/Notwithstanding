package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;
import ru.spbstu.icc.kspt.zhuikov.qouridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Field;
import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.ItemFieldException;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.BarrierPosition;

import static org.junit.Assert.assertEquals;

public class FieldTest {

    @Test
    public void testInitialization() {

        Field field = new Field(9);

        assertEquals(CellColor.BLACK, field.getColor(0, 0));
        assertEquals(CellColor.WHITE, field.getColor(0, 1));
        assertEquals(CellColor.WHITE, field.getColor(1, 1));
        assertEquals(CellColor.BLACK, field.getColor(2, 2));
    }

    @Test
    public void testPathBetweenCells1() {  // пустое поле

        Field field = new Field(9);
        assertEquals(true, field.foo(new Coordinates(16, 8), 0));
        assertEquals(true, field.foo(new Coordinates(0, 8), 10));
    }

    @Test
    public void testPathBetweenCells2() throws ItemFieldException { // "перегородка" по вертикали

        Field field = new Field(9);

        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(i, 7, BarrierPosition.VERTICAL));
        }
        field.setItem(new Barrier(15, 7, BarrierPosition.HORIZONTAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));

        assertEquals(true, field.foo(new Coordinates(16, 8), 0));
        assertEquals(true, field.foo(new Coordinates(0, 16), 0));
        assertEquals(true, field.foo(new Coordinates(0, 16), 16));
    }

    @Test
    public void testPathBetweenCells3() throws ItemFieldException { // "перегородка" по горизонтали

        Field field = new Field(9);
        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(7, i, BarrierPosition.HORIZONTAL));
        }
        field.setItem(new Barrier(7, 15, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(5, 15, BarrierPosition.HORIZONTAL));

        assertEquals(true,  field.foo(new Coordinates(0, 4), 2));
        assertEquals(false, field.foo(new Coordinates(0, 4), 10));
        assertEquals(false, field.foo(new Coordinates(16, 6), 0));
    }

    @Test
    public void testPathBetweenCells4() throws ItemFieldException { // закрытая фишка

        Field field = new Field(9);
        field.setItem(new Barrier(15, 7, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(13, 8, BarrierPosition.HORIZONTAL));

        assertEquals(false, field.foo(new Coordinates(16, 8), 6));
        assertEquals(true,  field.foo(new Coordinates(16, 8), 16));
        assertEquals(true,  field.foo(new Coordinates(0, 8), 16));
    }

}
