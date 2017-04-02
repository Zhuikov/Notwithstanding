package ru.spbstu.icc.kspt.zhuikov.quoridor.core.console;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.BarrierPosition;

public class Command {

    private CommandType commandType;
    private Coordinates coordinates;
    private BarrierPosition barrierPosition;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public BarrierPosition getBarrierPosition() {
        return barrierPosition;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setBarrierPosition(BarrierPosition barrierPosition) {
        this.barrierPosition = barrierPosition;
    }
}
