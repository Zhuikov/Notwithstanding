/*
  Содержит классы, возвращаемые игрой по трбованию пользователя.
 */
package ru.spbstu.icc.kspt.zhuikov.quoridor.core.returningClasses;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.ItemType;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;

/**
 * Класс, представляющий клетку на поле.
 */
public class Cell {

    /**
     * Тип элемента, находящегося на клетке.
     */
    private final ItemType itemType;

    /**
     * Владелец элемента, находящегося на клетке.
     */
    private final Owner owner;

    /**
     * Конструктор класса.
     * @param itemType - тип элемента, находящегося на клетке.
     * @param owner - владелец элемента, находящегося на клетке.
     */
    Cell(ItemType itemType, Owner owner) {
        this.itemType = itemType;
        this.owner = owner;
    }

    /**
     * Возвращает тип элемента, находящегося на клетке.
     */
    public ItemType getType() {
        return itemType;
    }

    /**
     * Возвращает владельца элемента, находящегося на клетке.
     */
    public Owner getOwner() {
        return owner;
    }
}

