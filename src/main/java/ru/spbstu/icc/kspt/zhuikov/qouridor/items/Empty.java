package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


public class Empty extends OneCellItem {

    public Empty(int vertical, int horizontal) {
        super(vertical, horizontal);
        this.type = ItemType.EMPTY;
    }

}
