package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

/**
 * Обычный игрок, цель которого - дойти до нужного ряда на поле для победы.
 * Может быть ботом или игроком-пользователем.
 */
abstract public class UsualPlayer extends QuoridorPlayer {

    /**
     * Позиция игрока на поле.
     */
    protected PlayerPosition position;

    /**
     * Возвращает позицию игрока на поле.
     */
    public PlayerPosition getPosition() {
        return position;
    }

    /**
     * Возвращает число перегородок у игрока.
     */
    public int getBarriersNumber() {
        return core.getBarriersNumber(owner);
    }

}
