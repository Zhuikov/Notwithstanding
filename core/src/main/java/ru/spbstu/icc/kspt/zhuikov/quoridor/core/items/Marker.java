package ru.spbstu.icc.kspt.zhuikov.quoridor.core.items;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;

/**
 * Класс, представляющий фишку игрока на игровом поле.
 */
public class Marker extends Item {

    /**
     * Конструктор класса. Создает объект фишки игрока с заданным владельцем.
     * @param owner - владелец фишки.
     */
    public Marker(Owner owner) {
        this.type = ItemType.MARKER;
        this.owner = owner;
    }
}
