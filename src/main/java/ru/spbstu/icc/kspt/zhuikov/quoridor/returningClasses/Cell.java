package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

public class Cell {

    private final int vertical;
    private final int horizontal;
    private final ItemType itemType;
    private final CellColor color;
    private final Owner owner;

    Cell(int vertical, int horizontal, ItemType itemType, CellColor color, Owner owner) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.itemType = itemType;
        this.color = color;
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

    public CellColor getColor() {
        return color;
    }

    public Owner getOwner() {
        return owner;
    }
}

