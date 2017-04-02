package ru.spbstu.icc.kspt.zhuikov.quoridor.core.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.QuoridorCore;

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