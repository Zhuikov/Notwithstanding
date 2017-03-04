package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.BotBrain;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.*;

public class BotPlayer extends UsualPlayer {

    private BotBrain brain;

    public BotPlayer(QuoridorCore game, PlayerPosition position) {

        this.game = game;
        this.position = position;
        brain = new BotBrain(game.getField(), this);
        owner = position.getOwner();
        markerCoordinates = new Coordinates(position.getInitialVertical(), position.getInitialHorizontal());

    }

    @Override
    public void makeMove() {

        Command command = brain.whatToDo(game.getField());

        switch (command.getCommandType()) {
            case MARKER:
                try {
                    game.moveMarker(command.getDestination());
                    markerCoordinates = command.getDestination();
                } catch (FieldItemException e) {
                    throw new AssertionError("bot.makeMove()");
                }
                break;
            case BARRIER:
                try {
                    game.placeBarrier(command.getDestination(), command.getBarrierPosition());
                } catch (FieldItemException | NoBarriersException e) {
                    throw new AssertionError("bot.makeMove()");
                }
                break;

        }

        if (markerCoordinates.getVertical() == position.getDestinationRow()) {
            for (VictoryListener listener : victoryListeners) {
                listener.setWinner(owner);
            }
        }
    }

}
