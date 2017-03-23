package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

/**
 * Абстрактный класс, представляющий игрока, способного играть в "Коридор".
 */
abstract public class QuoridorPlayer {

    /**
     * Ядро игры.
     */
    protected QuoridorCore core;

    /**
     * Владелец игрока. У каждого игрока это поле уникально.
     */
    protected Owner owner;

    /**
     * Возвращает владельца игрока.
     */
    public Owner getOwner() { return owner; }

    /**
     * Возвращает ядро игры, с которым работает игрок.
     */
    public QuoridorCore getCore() {
        return core;
    }

    /**
     * Метод, при вызове которого игрок делает ход.
     */
    abstract public void makeMove();
}