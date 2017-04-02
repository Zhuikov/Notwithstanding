package ru.spbstu.icc.kspt.zhuikov.quoridor.core.game;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.AI.Fox;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.QuoridorPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.UsualPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс для работы с игроками, их очередностью ходов.
 */
public class QuoridorQueue {

    /**
     * Ядро игры.
     */
    private QuoridorCore core;

    /**
     * Номер текущего игрока в списке обычных игроков.
     */
    private int currentPlayer = 0;

    /**
     * Список с обычными игрокаим.
     */
    private List<QuoridorPlayer> players = new ArrayList<>();

    /**
     * Игрок-лиса.
     */
    private Fox fox = null;

    /**
     * Список "слушателей" события конца игры.
     */
    private Set<WinnerListener> listeners = new HashSet<>();

    /**
     * Шаг игры. Игровой шаг увеличивается на единицу при совершении хода обычного игрока.
     * Ход лисы не изменят значание шага игры.
     */
    private int step = 0;

    /**
     * Шаг, на котором в игру добавляется лиса.
     */
    private static int foxTime = 2;

    /**
     * Частота, с которой лиса совершает ход.
     * Ход совершается один раз в foxFrequency шагов.
     */
    private static int foxFrequency = 5;

    /**
     * Возвращает текущий шаг игры.
     */
    public int getStep() {
        return step;
    }

    /**
     * Возвращает время появления лисы.
     */
    public static int getFoxTime() {
        return foxTime;
    }

    /**
     * Возвращает частоту хода лисы.
     */
    public static int getFoxFrequency() {
        return foxFrequency;
    }

    /**
     * Устанавливает шаг, на котором появляется лиса.
     * @param foxTime - целое, неотрицательное число - шаг пояления лисы на поле.
     */
    public static void setFoxTime(int foxTime) {

        if (foxTime < 0) {
            throw new IllegalArgumentException("Fox time must be >= 0");
        }
        QuoridorQueue.foxTime = foxTime;
    }

    /**
     * Устанавливает частоту хода лисы.
     * @param foxTurn - натуральное число - частота хода лисы.
     */
    public static void setFoxFrequency(int foxTurn) {

        if (foxTurn < 1 ) {
            throw new IllegalArgumentException("Fox frequency must be > 0");
        }
        QuoridorQueue.foxFrequency = foxTurn;
    }

    /**
     * Возвращает текущего игрока.
     */
    public QuoridorPlayer getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * Конструктор класса.
     * @param core - ядро, используемое классом.
     */
    public QuoridorQueue(QuoridorCore core) {
        this.core = core;
    }

    /**
     * Добавляет обычного игрока в список.
     * @param player - игрок, добавляемый в список.
     */
    public void addPlayer(UsualPlayer player) {

        players.add(player); // нет защиты от добавления игрока уже существующей позиции
        core.addPlayer(player);
    }

    /**
     * Добавляет "слушателя" конца игры в список.
     * @param listener - добавляемый "слушатель", реализующий необходимый интерфейс.
     * @see WinnerListener;
     */
    public void addWinnerListener(WinnerListener listener) {
        listeners.add(listener);
    }

    /**
     * Метод, вызываемый ядром при одержании победы одним их игроков.
     */
    public void onWin() {

        for (WinnerListener listener : listeners) {
            listener.setWinner(players.get(currentPlayer).getOwner());
        }
    }

    /**
     * Метод, осуществляющий ход игрока currentPlayer.
     * Увеличивает шаг модели; при необходимости долбавляет лису.
     */
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

    /**
     * Меняет номер текущего игрока. Если достигнут последний игрок в списке, ход переходит к первому.
     */
    public void updateCurrentPlayer() {

        if (++currentPlayer >= players.size()) {
            currentPlayer = 0;
        }
    }

}
