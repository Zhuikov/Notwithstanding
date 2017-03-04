package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.BotPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.HumanPlayer;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;

public class Quoridor {

    private QuoridorCore core;
    private QuoridorQueue queue;

    public Quoridor(boolean bot) {

        queue = new QuoridorQueue(core);
        core  = new QuoridorCore(queue);

        if (bot) {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new BotPlayer(core, PlayerPosition.TOP));
        } else {
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.BOT));
            queue.addPlayer(new HumanPlayer(core, PlayerPosition.TOP));
        }
    }

    public void moveMarker(int vertical, int horizontal) throws FieldItemException {
        core.moveMarker(new Coordinates(vertical, horizontal));
    }

    public void placeBarrier(int vertical, int horizontal, BarrierPosition position)
            throws FieldItemException, NoBarriersException {
        core.placeBarrier(new Coordinates(vertical, horizontal), position);
    }

}
