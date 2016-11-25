package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


public class Marker extends OneCellItem {

    public Marker(int vertical, int horizontal) {
        super(vertical, horizontal);
        this.type = ItemType.MARKER;
    }

    public void moveTo(int vertical, int horizontal) {
        coordinates.setVertical(vertical);
        coordinates.setHorizontal(horizontal);
    }
}
