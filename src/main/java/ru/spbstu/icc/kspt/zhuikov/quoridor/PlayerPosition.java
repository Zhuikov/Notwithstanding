package ru.spbstu.icc.kspt.zhuikov.quoridor;


public enum PlayerPosition {

    TOP(0, 8, 16),
    BOT(16, 8, 0);

    int initialVertical;
    int initialHorizontal;
    int destinationRow;

    PlayerPosition(int initialVertical, int initialHorizontal, int destinationRow) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
        this.destinationRow = destinationRow;
    }
}
