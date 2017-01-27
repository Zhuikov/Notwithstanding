package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


import ru.spbstu.icc.kspt.zhuikov.quoridor.Coordinates;

public class Barrier extends ManyCellsItem {

    public final static int length = 2;

    public Barrier(int vertical, int horizontal, BarrierPosition position) {
        this.type = ItemType.BARRIER;

        if (position == BarrierPosition.VERTICAL) {
            for (int i = vertical - length + 1; i <= vertical + length - 1; i++) {
                this.coordinates.add(new Coordinates(i, horizontal));
            }
        } else if (position == BarrierPosition.HORIZONTAL) {
            for (int i = horizontal - length + 1; i <= horizontal + length - 1; i++) {
                this.coordinates.add(new Coordinates(vertical, i));
            }
        }

//        if (coordinates.size() != realLength) {
//            throw new IllegalArgumentException("too long barrier. Its size must be equals to " + length);
//        }


    }

}
