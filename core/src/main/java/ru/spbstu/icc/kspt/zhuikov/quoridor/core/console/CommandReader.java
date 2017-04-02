package ru.spbstu.icc.kspt.zhuikov.quoridor.core.console;


import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.Coordinates;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.BarrierPosition;

public class CommandReader {

    public static Command read(String string) throws IllegalArgumentException {

        Command command;

        if (string.toLowerCase().matches("\\s*marker\\s+-?\\d+\\s+-?\\d+\\s*")) {
            command = new Command(CommandType.MARKER);
            String[] stringArray = string.split("\\s+");
            command.setCoordinates(new Coordinates(Integer.parseInt(stringArray[1]),
                                                   Integer.parseInt(stringArray[2])));
            return command;
        }

        if (string.toLowerCase().matches("\\s*barrier\\s+-?\\d+\\s+-?\\d+\\s+(horizontal|vertical)\\s*")) {
            command = new Command(CommandType.BARRIER);
            String[] stringArray = string.split("\\s+");
            command.setCoordinates(new Coordinates(Integer.parseInt(stringArray[1]),
                                                   Integer.parseInt(stringArray[2])));
            if (stringArray[3].equals("vertical")) {
                command.setBarrierPosition(BarrierPosition.VERTICAL);
            } else {
                command.setBarrierPosition(BarrierPosition.HORIZONTAL);
            }

            return command;
        }

        if (string.toLowerCase().matches("\\s*help\\s*")) {
            return new Command(CommandType.HELP);
        }

//        if (string.toLowerCase().matches("\\s*hide\\s+help\\s*")) {
//            return new Command(CommandType.HIDE_HELP);
//        }

        throw new IllegalArgumentException("unknown command");
    }

}
