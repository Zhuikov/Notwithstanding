package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;


import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;

public class Player {

    private final int barriersNumber;
    private final PlayerPosition position;

    public Player(int barriersNumber, PlayerPosition position) {
        this.barriersNumber = barriersNumber;
        this.position = position;
    }

    public int getBarriersNumber() {
        return barriersNumber;
    }

    public PlayerPosition getPosition() {
        return position;
    }
}