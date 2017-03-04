package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


// обычный игрок, который хочет дойти до конца (не лиса)
// м.б. ботом или человеком
abstract public class UsualPlayer extends QuoridorPlayer {

    protected PlayerPosition position;

    public PlayerPosition getPosition() {
        return position;
    }

    public int getBarriersNumber() {
        return game.getBarriersNumber(position);
    }

}
