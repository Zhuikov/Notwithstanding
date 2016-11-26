package ru.spbstu.icc.kspt.zhuikov.qouridor;

import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.qouridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.qouridor.items.BarrierPosition;

import java.util.ArrayList;
import java.util.List;

public class Quoridor {

    private static Field field = new Field(9);
    private List<Player> players = new ArrayList<Player>();
    private short currentPlayer;

    public Quoridor(int playersNumber) {

        if (playersNumber == 2) {
            Player player = Player.TOP;
            player.createPlayer(field);
            currentPlayer = 0;
            players.add(player);

            player = Player.BOTTOM;
            player.createPlayer(field);
            players.add(player);

        } else {
            throw new AssertionError("пока рано еще думать о чем-то большем...");
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public boolean isEnd() { return false; } // todo это <---

    public void moveMarker(int vertical, int horizontal) throws FieldItemException {

        players.get(currentPlayer).makeMove(vertical, horizontal);
        if (++currentPlayer == players.size()) {
            currentPlayer = 0;
        }

    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException{

        players.get(currentPlayer).makeMove(vertical, horizontal, position);
        if (++currentPlayer == players.size()) {
            currentPlayer = 0;
        }

    }

}
