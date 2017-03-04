package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

public class Cell {

    private final ItemType itemType;
    private final Owner owner;

    Cell(ItemType itemType, Owner owner) {
        this.itemType = itemType;
        this.owner = owner;
    }

    public ItemType getType() {
        return itemType;
    }

    public Owner getOwner() {
        return owner;
    }
}

