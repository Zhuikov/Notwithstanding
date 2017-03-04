package ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

public class Player {

    private final int barriersNumber;
    private final Owner owner;

    public Player(int barriersNumber, Owner owner) {
        this.barriersNumber = barriersNumber;
        this.owner = owner;
    }

    public int getBarriersNumber() {
        return barriersNumber;
    }

    public Owner getOwner() {
        return owner;
    }

}