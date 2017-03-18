package ru.spbstu.icc.kspt.zhuikov.quoridor.player;


import ru.spbstu.icc.kspt.zhuikov.quoridor.BotBrain;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Command;
import ru.spbstu.icc.kspt.zhuikov.quoridor.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;

public class BotPlayer extends UsualPlayer {

    private BotBrain brain;

    public BotPlayer(QuoridorCore core, PlayerPosition position) {

        this.core = core;
        this.position = position;
        brain = new BotBrain(core.getField(), this);
        owner = position.getOwner();
    }

    @Override
    public void makeMove() {

        core.setCurrentPlayer(this);
        Command command = brain.whatToDo();

        switch (command.getCommandType()) {
            case MARKER:
                try {
                    core.moveMarker(command.getDestination());
                } catch (FieldItemException e) {
                    throw new AssertionError("bot.makeMove()");
                }
                break;
            case BARRIER:
                try {
                    core.placeBarrier(command.getDestination(), command.getBarrierPosition());
                } catch (FieldItemException | NoBarriersException e) {
                    throw new AssertionError("bot.makeMove()");
                }
                break;
        }
    }

}
