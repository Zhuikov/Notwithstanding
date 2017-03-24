package ru.spbstu.icc.kspt.zhuikov.quoridor.game;

import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Owner;

/**
 * Интерфейс, необходимый "слушателям" окончания игры.
 */
@FunctionalInterface
public interface WinnerListener {

    /**
     * Метод, вызываемый у "слушателя" при окончании игры.
     * @param owner - содержит игрока, победившего в игре.
     */
    void setWinner(Owner owner);

}
