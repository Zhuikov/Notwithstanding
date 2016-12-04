package ru.spbstu.icc.kspt.zhuikov.quoridor.items;


abstract public class Item {

    protected ItemType type = ItemType.EMPTY;

    protected Owner owner = Owner.NOBODY;

    public Item() {}

    public ItemType getType() { return type; }

    public Owner getOwner() { return  owner; }
}
