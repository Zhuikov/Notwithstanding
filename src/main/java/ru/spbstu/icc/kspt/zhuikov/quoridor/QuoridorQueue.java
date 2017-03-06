package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
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
    private Set<WinnerListener> listeners = new HashSet<>();

    private int step = 1;
    private static int foxTime = 2;
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
        player.addWinnerListener(listeners);
        core.addBarriers(player.getPosition());
    }

    public void addWinnerListener(WinnerListener listener) {

        listeners.add(listener);
        for (QuoridorPlayer player : players) {
            player.addWinnerListener(listener);
        }
    }

    public void moveNextPlayer() {

        if (players.get(currentPlayer).getOwner() == Owner.FOX) {

            if (step % foxFrequency == 0) {
                players.get(currentPlayer).makeMove();
            } else {
                players.get(getNextPlayer()).makeMove();
            }

        } else {
            players.get(currentPlayer).makeMove();
        }

        if (++step == foxTime) {
            Fox fox = new Fox(core);
            fox.addWinnerListener(listeners);
            players.add(fox);
        }

        currentPlayer = getNextPlayer();
    }

    private int getNextPlayer() {

        if (++currentPlayer >= players.size()) {
            return 0;
        }
        return currentPlayer;
    }

}
