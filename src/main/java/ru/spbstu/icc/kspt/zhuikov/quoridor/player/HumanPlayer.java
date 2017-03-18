package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;

public class HumanPlayer extends UsualPlayer {

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
