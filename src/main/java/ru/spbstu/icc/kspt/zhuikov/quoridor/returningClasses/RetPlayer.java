package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;


import ru.spbstu.icc.kspt.zhuikov.quoridor.PlayerPosition;

public class RetPlayer {

    private int barriersNumber;
    private PlayerPosition position;

    public RetPlayer(int barriersNumber, PlayerPosition position) {
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