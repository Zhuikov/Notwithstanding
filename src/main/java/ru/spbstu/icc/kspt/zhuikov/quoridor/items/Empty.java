package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


public class Empty extends Item {

    public Empty() {
        this.type = ItemType.EMPTY;
        this.owner = Owner.NOBODY;
    }

}
