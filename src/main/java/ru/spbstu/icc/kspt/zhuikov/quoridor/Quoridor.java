package ru.spbstu.icc.kspt.zhuikov.quoridor;

import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.*;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Player;

import java.util.ArrayList;
import java.util.List;


//TODO мне не хватает документации к коду
//TODO также хотелось бы, чтобы ядро с логикой было выделенно, если не в отдельный модуль, то хотябы в отдельный пакет(в отдельный относительно UI)

public class Quoridor {

    private final QuoridorField field = new QuoridorField(9);
    private List<UsualPlayer> players = new ArrayList<>();
    private Fox fox;

    //TODO возможно есть смысл поменять на enum, с методом nextPlayer();
    private int currentPlayer = 0;
    private int step = 0;
    private static int foxTime = 20;
    private static int foxFrequency = 10;

    public Quoridor(boolean bot) {

        HumanPlayer humanPlayer = new HumanPlayer(field, PlayerPosition.BOT);
        players.add(humanPlayer);
        if (bot) {
            BotPlayer botPlayer = new BotPlayer(field, PlayerPosition.TOP, humanPlayer);
            players.add(botPlayer);
        } else {
            HumanPlayer humanPlayer1 = new HumanPlayer(field, PlayerPosition.TOP);
            players.add(humanPlayer1);
        }

    }

    public Player getCurrentPlayer() {

        UsualPlayer normalPlayer = players.get(currentPlayer);
        return new Player(normalPlayer.getBarriersNumber(), normalPlayer.getPosition());
    }

    public static void setFoxTime(int foxTime) {

        if (foxTime < 0) {
            throw new IllegalArgumentException("Fox time must be >= 0");
        }
        Quoridor.foxTime = foxTime;
    }

    public static int getFoxTime() {
        return foxTime;
    }

    public static int getFoxFrequency() {
        return foxFrequency;
    }

    public static void setFoxFrequency(int foxTurn) {

        if (foxTurn < 1 ) {
            throw new IllegalArgumentException("Fox Frequency must be > 0");
        }
        Quoridor.foxFrequency = foxTurn;
    }

    public int getStep() {
        return step;
    }

    public Field getField() {
        return new Field(field);
    }

    public List<Player> getPlayers() {

        List<Player> players = new ArrayList<>();

        for (UsualPlayer player : this.players) {
            players.add(new Player(player.getBarriersNumber(), player.getPosition()));
        }

        return players;
    }


    public boolean isEnd() {              //TODO возможно следует подумать об использование шаблона Наблюдатель

        for (UsualPlayer player : players) {
            if (player.getPosition().getDestinationRow() == player.getCoordinates().getVertical()) {
                return true;
            }
        }
        return fox != null && fox.getCoordinates().equals(fox.getTarget());

    }

    public Owner getWinner(){   //TODO по-моему, при использование Наблюдателя метод атрофируется

        if (isEnd()) {

            for (UsualPlayer player : players) {
                if (player.getPosition().getDestinationRow() == player.getCoordinates().getVertical()) {
                    return player.getPosition().getOwner();
                }
            }

            if (fox.getCoordinates().equals(fox.getTarget())) {
                return Owner.FOX;
            }
        }

        return Owner.NOBODY;
    }

    public void moveMarker(int vertical, int horizontal)
            throws FieldItemException, NoBarriersException {

        players.get(currentPlayer).moveMarker(vertical, horizontal);
        changePlayerTurn();
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {

        players.get(currentPlayer).placeBarrier(vertical, horizontal, position);
        changePlayerTurn();
    }

    public List<Coordinates> getPossibleMoves() {

        return players.get(currentPlayer).getPossibleMoves();
    }

    private void changePlayerTurn() throws FieldItemException, NoBarriersException {

        if (++currentPlayer == players.size()) {
            currentPlayer = 0;
        }

        if (isEnd()) return;

        if (step % foxFrequency == 0 && fox != null) {
            fox.makeMove();
            if (isEnd()) return;
        }

        if (step == foxTime) {
            fox = new Fox(field, players);
        }

        step++;

        if (players.get(currentPlayer).isBot()) {
            players.get(currentPlayer).makeMove();
//            if (isEnd()) return;
            changePlayerTurn();
        }
    }

}
