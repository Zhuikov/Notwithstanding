package ru.spbstu.icc.kspt.zhuikov.quoridor.core.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.QuoridorCore;

/**
 * Класс, представляющий игрока-пользователя игры.
 */
public class HumanPlayer extends UsualPlayer {

    /**
     * Конструктор игрока.
     * @param core - ядро, которое используется игроком.
     * @param position - начальное расположение фишки игрока на игровом поле.
     */
    public HumanPlayer(QuoridorCore core, PlayerPosition position) {

        this.core = core;
        this.position = position;
        owner = position.getOwner();
    }

    @Override
    public void makeMove() {
        core.setCurrentPlayer(this);
    }
}
