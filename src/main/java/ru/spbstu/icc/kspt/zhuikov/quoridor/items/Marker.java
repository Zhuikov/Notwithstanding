package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


public class Marker extends Item {

    public Marker(Owner owner) {
        this.type = ItemType.MARKER;
        this.owner = owner;
    }
}
