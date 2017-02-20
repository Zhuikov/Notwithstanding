package ru.sbpstu.icc.kspt.Zhuikov.courseWork;


import org.junit.Test;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorField;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Barrier;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;

import static org.junit.Assert.assertEquals;

public class FieldTest {

    @Test
    public void testPathBetweenCells1() {  // пустое поле

        QuoridorField field = new QuoridorField(9);
        assertEquals(false, field.getPath(new Coordinates(0, 0), new Coordinates(16, 8)).empty());
        assertEquals(false, field.getPath(new Coordinates(0, 8), new Coordinates(10, 0)).empty());
    }

//    @Test
//    public void testest() {
//
//        class Vertex {
//            private Coordinates coordinates;
//            private Vertex from;
//
//            private Vertex(Coordinates coordinates, Vertex from) {
//                this.coordinates = coordinates;
//                this.from = from;
//            }
//
//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (!(o instanceof Vertex)) return false;
//
//                Vertex vertex = (Vertex) o;
//
//                if (coordinates != null ? !coordinates.equals(vertex.coordinates) : vertex.coordinates != null)
//                    return false;
//                return from != null ? from.equals(vertex.from) : vertex.from == null;
//
//            }
//
//            @Override
//            public int hashCode() {
//                int result = coordinates != null ? coordinates.hashCode() : 0;
//                result = 31 * result + (from != null ? from.hashCode() : 0);
//                return result;
//            }
//        }
//
//        Queue<Vertex> vertexes = new LinkedList<>();
//        Vertex vertex1 = new Vertex(new Coordinates(0, 0), null);
//        Vertex vertex2 = new Vertex(new Coordinates(0, 2), vertex1);
//        Vertex vertex3 = new Vertex(new Coordinates(2, 2), vertex2);
//        Vertex vertex4 = new Vertex(new Coordinates(2, 4), vertex3);
//        vertexes.add(vertex1);
//        vertexes.add(vertex2);
//        vertexes.add(vertex3);
//        vertexes.add(vertex4);
//        assertEquals(true, vertexes.contains(new Vertex(new Coordinates(2, 4), vertex3)));
//
//    }

    @Test
    public void testPathBetweenCells2() throws FieldItemException { // "перегородка" по вертикали

        QuoridorField field = new QuoridorField(9);

        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(i, 7, BarrierPosition.VERTICAL));
        }
        field.setItem(new Barrier(15, 7, BarrierPosition.HORIZONTAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));

        assertEquals(false, field.getPath(new Coordinates(16, 8), new Coordinates(0, 0)).empty());
        assertEquals(true, field.getPath(new Coordinates(0, 16), new Coordinates(0, 0)).empty());
        assertEquals(false, field.getPath(new Coordinates(0, 16), new Coordinates(16, 16)).empty());
    }

    @Test
    public void testPathBetweenCells3() throws FieldItemException { // "перегородка" по горизонтали

        QuoridorField field = new QuoridorField(9);
        for (int i = 1; i <= 13; i+=4) {
            field.setItem(new Barrier(7, i, BarrierPosition.HORIZONTAL));
        }
        field.setItem(new Barrier(7, 15, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(5, 15, BarrierPosition.HORIZONTAL));

        assertEquals(false, field.getPath(new Coordinates(0, 4), new Coordinates(2, 16)).empty());
        assertEquals(true, field.getPath(new Coordinates(0, 4), new Coordinates(10, 10)).empty());
        assertEquals(true, field.getPath(new Coordinates(16, 6), new Coordinates(0, 12)).empty());
    }

    @Test
    public void testPathBetweenCells4() throws FieldItemException { // закрытая фишка

        QuoridorField field = new QuoridorField(9);
        field.setItem(new Barrier(15, 7, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(15, 9, BarrierPosition.VERTICAL));
        field.setItem(new Barrier(13, 8, BarrierPosition.HORIZONTAL));

        assertEquals(true, field.getPath(new Coordinates(16, 8), new Coordinates(6, 8)).empty());
        assertEquals(true, field.getPath(new Coordinates(16, 8), new Coordinates(16, 8)).empty());
        assertEquals(false, field.getPath(new Coordinates(16, 8), new Coordinates(14, 8)).empty());
    }

}
