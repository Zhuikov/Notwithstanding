//package ru.sbpstu.icc.kspt.Zhuikov.courseWork;
//
//import org.junit.Test;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.player.HumanPlayer;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.player.PlayerPosition;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.game.QuoridorField;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.exceptions.*;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.items.BarrierPosition;
//import ru.spbstu.icc.kspt.zhuikov.quoridor.GUI.items.ItemType;
//
//import static org.junit.Assert.assertEquals;
//
//public class BarrierTest {
//
//    @Test
//    public void testBarrierSet() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player = new HumanPlayer(field, PlayerPosition.TOP);
//
//        player.placeBarrier(5, 3, BarrierPosition.VERTICAL);
//
//        assertEquals(ItemType.BARRIER, field.getItem(5, 3).getType());
//        assertEquals(ItemType.BARRIER, field.getItem(4, 3).getType());
//        assertEquals(ItemType.BARRIER, field.getItem(6, 3).getType());
//
//        player.placeBarrier(13, 11, BarrierPosition.HORIZONTAL);
//
//        assertEquals(ItemType.BARRIER, field.getItem(13, 10).getType());
//        assertEquals(ItemType.BARRIER, field.getItem(13, 11).getType());
//        assertEquals(ItemType.BARRIER, field.getItem(13, 12).getType());
//    }
//
//
//    @Test(expected = ImpossibleToSetItemException.class)
//    public void testBlackCellSet() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player = new HumanPlayer(field, PlayerPosition.TOP);
//        player.placeBarrier(2, 8, BarrierPosition.HORIZONTAL);
//    }
//
//    @Test(expected = ImpossibleToSetItemException.class)
//    public void testSetBetweenBlackCells() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
//        player.placeBarrier(7, 12, BarrierPosition.VERTICAL);
//    }
//
//    @Test(expected = ImpossibleToSetItemException.class)
//    public void testWrongCoordinates() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
//        player.placeBarrier(0, 1, BarrierPosition.VERTICAL);
//    }
//
//    @Test(expected = CellIsNotEmptyException.class)
//    public void testImpossibleSet() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player = new HumanPlayer(field, PlayerPosition.BOT);
//
//        player.placeBarrier(5, 3, BarrierPosition.VERTICAL);
//        player.placeBarrier(3, 3, BarrierPosition.VERTICAL);
//    }
//
//    @Test (expected = NoBarriersException.class)
//    public void testNoBarriers() throws FieldItemException, NoBarriersException {
//
//        QuoridorField field = new QuoridorField(9);
//        HumanPlayer player =new HumanPlayer(field, PlayerPosition.TOP);
//
//        for (int i = 1; i < 16; i+=2) {
//            player.placeBarrier(1, i, BarrierPosition.VERTICAL); // 8 barriers
//        }
//        player.placeBarrier(5, 1, BarrierPosition.VERTICAL);
//        player.placeBarrier(5, 3, BarrierPosition.VERTICAL);
//
//        player.placeBarrier(5, 5, BarrierPosition.VERTICAL);
//    }
//
//    @Test (expected = ImpossibleToSetItemException.class)
//    public void testPlayerBlock() throws FieldItemException, NoBarriersException {
//
////        QuoridorField field = new QuoridorField(9);
////        HumanPlayer bottom = new HumanPlayer(field, PlayerPosition.BOT);
////        new HumanPlayer(field, PlayerPosition.TOP);
////
////        field.setBarrier(new Barrier(new Coordinates(1, 7), BarrierPosition.VERTICAL));
////        field.setBarrier(new Barrier(new Coordinates(1, 9), BarrierPosition.VERTICAL));
////
////        bottom.placeBarrier(3, 8, BarrierPosition.HORIZONTAL);
//    }
//}
