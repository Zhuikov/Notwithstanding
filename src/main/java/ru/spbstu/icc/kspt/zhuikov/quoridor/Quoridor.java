package ru.spbstu.icc.kspt.zhuikov.quoridor;

import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoWinnerException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Player;

import javax.print.attribute.standard.PrinterLocation;
import java.util.ArrayList;
import java.util.List;

public class Quoridor {

    private static QuoridorField field = new QuoridorField(9);
    private List<QuoridorPlayer> players = new ArrayList<QuoridorPlayer>(); //todo мб убрать этот ненужный список
    private short currentPlayer;

    public Quoridor(int playersNumber) {

        if (playersNumber == 2) {
            QuoridorPlayer player = QuoridorPlayer.TOP;
            player.createPlayer(field);
            currentPlayer = 0;
            players.add(player);

            player = QuoridorPlayer.BOTTOM;
            player.createPlayer(field);
            players.add(player);

        } else {
            throw new AssertionError("пока рано еще думать о чем-то большем...");
        }
    }

    public Player getCurrentPlayer() {

        switch (players.get(currentPlayer)) {
            case TOP:
                return Player.TOP;
            case BOTTOM:
                return Player.BOTTOM;
            case RIGHT:
                return Player.RIGHT;
            default:
                return Player.LEFT;
        }
    }

    public Field getField() {
        return new Field(field);
    }

    //todo по рукам бы надавать наверно надо за такое...
    public Player getPlayerInformation(Player player) {

        switch (player) {
            case TOP:
                player.createPlayer(QuoridorPlayer.TOP.getBarriersNumber());
                return player;
            case BOTTOM:
                player.createPlayer(QuoridorPlayer.BOTTOM.getBarriersNumber());
                return player;
            case RIGHT:
                player.createPlayer(QuoridorPlayer.RIGHT.getBarriersNumber());
                return player;
            default:
                player.createPlayer(QuoridorPlayer.LEFT.getBarriersNumber());
                return player;
        }
    }

    public boolean isEnd() {

        if (QuoridorPlayer.TOP.getMarker().getCoordinates().getVertical() == field.getRealSize() - 1) {
            return true;
        }

        if (QuoridorPlayer.BOTTOM.getMarker().getCoordinates().getVertical() == 0) {
            return true;
        }

        // todo тут еще потом других сделать
        return false;
    }

    public Player getWinner() throws NoWinnerException {

        if (isEnd()) {
            if (QuoridorPlayer.TOP.getMarker().getCoordinates().getVertical() == field.getRealSize() - 1) {
                return Player.TOP;
            }
            if (QuoridorPlayer.BOTTOM.getMarker().getCoordinates().getVertical() == 0) {
                return Player.BOTTOM;
            }
        }

        // todo вообще ужас! за это точно надо надавать...
        throw new NoWinnerException("There is no winner");
    }

    public void moveMarker(int vertical, int horizontal) throws FieldItemException {

        players.get(currentPlayer).makeMove(vertical, horizontal);
        if (++currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {

        players.get(currentPlayer).makeMove(vertical, horizontal, position);
        if (++currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

}
