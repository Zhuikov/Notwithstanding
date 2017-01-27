package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

public enum PlayerPosition {

    TOP(0, 8, 16, Owner.TOP),
    BOT(16, 8, 0, Owner.BOTTOM);

    int initialVertical;
    int initialHorizontal;
    int destinationRow;
    Owner owner;

    PlayerPosition(int initialVertical, int initialHorizontal, int destinationRow, Owner owner) {
        this.initialVertical = initialVertical;
        this.initialHorizontal = initialHorizontal;
        this.destinationRow = destinationRow;
        this.owner = owner;
    }

    public Owner getOwner() { return owner; }
}
