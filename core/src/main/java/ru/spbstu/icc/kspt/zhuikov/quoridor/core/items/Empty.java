package ru.spbstu.icc.kspt.zhuikov.quoridor.core.items;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;

/**
 * Класс, представляющий вид Item, который обозначает пустую клетку на поле.
 */
public class Empty extends Item {

    /**
     * Конструктор класса. Устанавливает параметрам начальные, всегда одинаковые, значения для всех объектов этого класса.
     */
    public Empty() {
        this.type = ItemType.EMPTY;
        this.owner = Owner.NOBODY;
    }

}
