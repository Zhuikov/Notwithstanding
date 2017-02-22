package ru.spbstu.icc.kspt.zhuikov.quoridor;


import ru.spbstu.icc.kspt.zhuikov.quoridor.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Coordinates;

public class Command {

    private CommandType commandType;
    private Coordinates destination;
    private BarrierPosition barrierPosition = null;

    public CommandType getCommandType() {
        return commandType;
    }

    public Coordinates getDestination() {
        return destination;
    }

    public BarrierPosition getBarrierPosition() {
        return barrierPosition;
    }

    public Command(CommandType commandType, Coordinates destination) {
        this.commandType = commandType;
        this.destination = destination;
    }

    public Command(CommandType commandType, Coordinates destination, BarrierPosition barrierPosition) {
        this.commandType = commandType;
        this.destination = destination;
        this.barrierPosition = barrierPosition;
    }
}