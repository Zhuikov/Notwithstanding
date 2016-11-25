package ru.spbstu.icc.kspt.zhuikov.qouridor.items;


abstract public class Item {

    protected ItemType type = null;

    public Item() {}

    public ItemType getType() { return type; }
}
