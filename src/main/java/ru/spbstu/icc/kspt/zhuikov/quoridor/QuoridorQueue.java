package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Fox;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.QuoridorPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.UsualPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.WinnerListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuoridorQueue {

    private QuoridorCore core;
    private int currentPlayer = 0;
    private List<QuoridorPlayer> players = new ArrayList<>();
    private Fox fox = null;
    private Set<WinnerListener> listeners = new HashSet<>();

    private int step = 0;
    private static int foxTime = 20;
    private static int foxFrequency = 10;

    public int getStep() {
        return step;
    }

    public static int getFoxTime() {
        return foxTime;
    }

    public static int getFoxFrequency() {
        return foxFrequency;
    }

    public static void setFoxTime(int foxTime) {

        if (foxTime < 0) {
            throw new IllegalArgumentException("Fox time must be >= 0");
        }
        QuoridorQueue.foxTime = foxTime;
    }

    public static void setFoxFrequency(int foxTurn) {

        if (foxTurn < 1 ) {
            throw new IllegalArgumentException("Fox frequency must be > 0");
        }
        QuoridorQueue.foxFrequency = foxTurn;
    }

    public QuoridorPlayer getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public QuoridorQueue(QuoridorCore core) {
        this.core = core;
    }

    public void addPlayer(UsualPlayer player) {

        players.add(player); // нет защиты от добавления игрока уже существующей позиции
        core.addPlayer(player, player.getPosition());
    }

    public void addWinnerListener(WinnerListener listener) {
        listeners.add(listener);
    }

    public void onWin() {

        for (WinnerListener listener : listeners) {
            listener.setWinner(players.get(currentPlayer).getOwner());
        }
    }

    public void moveNextPlayer() {

        if (step == foxTime) {
            fox = new Fox(core);
            core.spawnFox();
        }

        step++;

        if (fox != null && step % foxFrequency == 0) {
            fox.makeMove();
        } else {
            players.get(currentPlayer).makeMove();
        }
    }

    public void updateCurrentPlayer() {

        if (++currentPlayer > players.size()) {
            currentPlayer = 0;
        }
    }

}
