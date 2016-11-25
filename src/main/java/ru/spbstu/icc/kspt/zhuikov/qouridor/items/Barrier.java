package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


import ru.spbstu.icc.kspt.zhuikov.qouridor.Coordinates;

import java.util.List;

public class Barrier extends ManyCellsItem {

    public final static int length = 2;

    private BarrierPosition position;

    public Barrier(List<Coordinates> coordinates, BarrierPosition position) {
        super(coordinates);
        this.position = position;
        this.type = ItemType.BARRIER;
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position) {
        this.position = position;
    }

}
