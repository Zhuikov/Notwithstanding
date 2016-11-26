package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


abstract public class Item {

    protected ItemType type = ItemType.EMPTY;

    public Item() {}

    public ItemType getType() { return type; }
}
