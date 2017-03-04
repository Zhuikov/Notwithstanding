package ru.spbstu.icc.kspt.zhuikov.quoridor.player;

import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;

@FunctionalInterface
public interface VictoryListener {

    void setWinner(Owner owner);

}
