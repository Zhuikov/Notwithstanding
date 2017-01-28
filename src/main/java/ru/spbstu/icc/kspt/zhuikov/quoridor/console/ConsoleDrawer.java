package ru.spbstu.icc.kspt.zhuikov.quoridor.console;


import ru.spbstu.icc.kspt.zhuikov.quoridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoWinnerException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.RetPlayer;

import java.util.HashMap;
import java.util.Map;

public class ConsoleDrawer {

    private Quoridor game = null;
    private static final Map<Owner, String> positions = new HashMap<Owner, String>() {{
        put(Owner.BOTTOM, "BOTTOM");
        put(Owner.TOP, "TOP");
        put(Owner.FOX, "FOX");
    }};

    public ConsoleDrawer(Quoridor game) {
        this.game = game;
    }

    public void drawField() {

        Field field = game.getField();
        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                switch (field.getCell(i, j).getType()) {
                    case EMPTY:
                        if (field.getCell(i, j).getColor() == CellColor.BLACK) {
                            System.out.print("O");
                        } else {
                            System.out.print(" ");
                        }
                        break;
                    case BARRIER:
                        System.out.print("#");
                        break;
                    case MARKER:
                        if (field.getCell(i, j).getOwner() == Owner.BOTTOM) {
                            System.out.print("B");
                        } else if (field.getCell(i, j).getOwner() == Owner.TOP) {
                            System.out.print("T");
                        }
                }
            }
            System.out.println();
        }

    }

    public void drawPlayerInformation() {

        for (RetPlayer player : game.getPlayers()) {
            System.out.println(positions.get(player.getPosition().getOwner()) + " player has " + player.getBarriersNumber() + " barriers");
        }
    }

    public void drawTurn() {

        System.out.print(positions.get(game.getCurrentPlayer().getPosition().getOwner()) + " player turn");
    }

    public void drawWinner() {

        try {
            System.out.println(positions.get(game.getWinner()) + " won!");
        } catch (NoWinnerException e ) {
            System.out.println("why did you call me?");
        }
    }

    public void drawHelp() {

        String help = "Quoridor game. Commands help.\n" +
                "   Command examples (without \"[ ]\":\n" +
                "     [marker 2 2] - moves current player's marker to [2 2] position if it possible\n" +
                "     [barrier 5 5 vertical] - places current player's barrier to [5 5] position " +
                "in vertical if it possible\n" +
                "     [barrier 5 5 horizontal] - places current player's barrier to [5 5] position " +
                "in horizontal if it possible\n";

        System.out.println(help);
    }

}
