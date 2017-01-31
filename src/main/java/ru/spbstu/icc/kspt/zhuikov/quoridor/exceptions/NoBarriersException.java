package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;


import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;

public class NoBarriersException extends Exception {

    private PlayerPosition position;

    public NoBarriersException(String s, PlayerPosition position) {
        super(s);
        this.position = position;
    }

    public PlayerPosition getPosition() {
        return position;
    }

}
