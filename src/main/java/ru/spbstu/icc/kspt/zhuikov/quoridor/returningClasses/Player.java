package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;


public enum Player {

    TOP,
    BOTTOM,
    RIGHT,
    LEFT;

    private int barrierNumber;

    public void createPlayer(int barrierNumber) {
        this.barrierNumber = barrierNumber;
    }

    public int getBarrierNumber() {
        return barrierNumber;
    }
}
