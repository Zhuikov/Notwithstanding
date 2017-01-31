package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


public class Marker extends OneCellItem {

    public Marker(int vertical, int horizontal) {
        super(vertical, horizontal);
        this.type = ItemType.MARKER;
    }

    public Marker(int vertical, int horizontal, Owner owner) {
        this(vertical, horizontal);
        this.owner = owner;
    }

    public void moveTo(int vertical, int horizontal) {
        coordinates = new Coordinates(vertical, horizontal);
    }
}
