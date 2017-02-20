package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

public class Cell {

    private final int vertical;
    private final int horizontal;
    private final ItemType itemType;
    private final Owner owner;

    Cell(int vertical, int horizontal, ItemType itemType, Owner owner) {
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

    public ItemType getType() {
        return itemType;
    }
    public Owner getOwner() {
        return owner;
    }
}

