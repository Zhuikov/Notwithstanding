package ru.spbstu.icc.kspt.zhuikov.quoridor.console;


import ru.spbstu.icc.kspt.zhuikov.quoridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoWinnerException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.items.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.returningClasses.Field;

public class ConsoleDrawer {

    private Quoridor game = null;

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

    public void drawPlayerInformation(Player player) {

        System.out.println(player.name() + " " + game.getPlayerInformation(player).getBarrierNumber());
    }

    public void drawTurn() {

        System.out.print(game.getCurrentPlayer().name() + " player's turn: ");
    }

    public void drawWinner() {

        try {
            Player winner = game.getWinner();
            System.out.println(winner + " player won!");
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
