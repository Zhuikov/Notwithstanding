package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

@FunctionalInterface
public interface WinnerListener {

    void setWinner(Owner owner);

}
