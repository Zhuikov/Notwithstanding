package ru.spbstu.icc.kspt.zhuikov.qouridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.qouridor.items.ItemType;

public class Cell {

    private final int vertical;
    private final int horizontal;
    private final ItemType itemType;
    private final Owner owner;

    public Cell(int vertical, int horizontal, ItemType itemType, Owner owner) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.itemType = itemType;
        this.owner = owner;
    }

    public int getVertical() {
        return vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Owner getOwner() {
        return owner;
    }
}

