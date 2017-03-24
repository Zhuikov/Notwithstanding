package ru.spbstu.icc.kspt.zhuikov.quoridor.items;

import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Owner;

/**
 * Абсрактный класс, представлюящий элемент на игровом поле.
 */
abstract public class Item {

    /**
     * Тип элемента.
     */
    protected ItemType type = ItemType.EMPTY;

    /**
     * Владелец элемента.
     */
    protected Owner owner = Owner.NOBODY;

    Item() {}

    /**
     * Возвращает тип элемента.
     */
    public ItemType getType() { return type; }

    /**
     * Возвращает владельца элемента.
     */
    public Owner getOwner() { return  owner; }
}
