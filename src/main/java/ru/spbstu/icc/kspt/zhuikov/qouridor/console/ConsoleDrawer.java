package ru.spbstu.icc.kspt.zhuikov.qouridor.console;


import ru.spbstu.icc.kspt.zhuikov.qouridor.CellColor;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Player;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Quoridor;

import static ru.spbstu.icc.kspt.zhuikov.qouridor.console.CommandType.BARRIER;
import static ru.spbstu.icc.kspt.zhuikov.qouridor.console.CommandType.MARKER;
import static ru.spbstu.icc.kspt.zhuikov.qouridor.items.ItemType.EMPTY;

public class ConsoleDrawer {

    private Quoridor game = null;

    public ConsoleDrawer(Quoridor game) {
        this.game = game;
    }

//    public void drawField() {
//
//        for (int i = 0; i < game.getField().getRealSize(); i++) {
//            for (int j = 0; j < game.getField().getRealSize(); j++) {
//                switch (game.getField().getItem(i, j).getType()) {
//                    case EMPTY:
//                        if (game.getField().getColor(i, j) == CellColor.BLACK) {
//                            System.out.print("O");
//                        } else {
//                            System.out.print(" ");
//                        }
//                        break;
//                    case BARRIER:
//                        System.out.print("#");
//                        break;
//                    case MARKER:
//                        System.out.print("@");
//                }
//            }
//            System.out.println();
//        }
//
//    }
//
//    public void drawPlayerInformation(Player playerPosition) {
//
//        System.out.println("Player " + playerPosition +
//                ". Barriers: " + game.getPlayerInformation(playerPosition).getBarriers());
//
//    }

    public void drawTurn() {

        switch (game.getCurrentPlayer()) {
            case TOP:
                System.out.println("TOP player's turn: ");
                break;
            case BOTTOM:
                System.out.println("BOTTOM player's turn: ");
                break;
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
