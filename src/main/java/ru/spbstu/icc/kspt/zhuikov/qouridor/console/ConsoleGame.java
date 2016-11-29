package ru.spbstu.icc.kspt.zhuikov.qouridor.console;


import ru.spbstu.icc.kspt.zhuikov.qouridor.Player;
import ru.spbstu.icc.kspt.zhuikov.qouridor.Quoridor;

import java.util.Scanner;

public class ConsoleGame {

//    private static Quoridor game = new Quoridor(2);
//    private static ConsoleDrawer drawer = new ConsoleDrawer(game);
//
//    public static void main(String[] args) {
//
//        ConsoleGame consoleGame = new ConsoleGame();
//        consoleGame.launch();
//
//    }
//
//    public void launch() {
//
//        Scanner in = new Scanner(System.in);
//        drawer.drawPlayerInformation(Player.TOP);
//        drawer.drawField();
//        drawer.drawPlayerInformation(Player.BOTTOM);
//        drawer.drawTurn();
//        while (!game.isEnd()) {
//
//            try {
//                Command command = CommandReader.read(in.nextLine());
//                switch (command.getCommandType()) {
//                    case BARRIER:
//                        game.placeBarrier(command.getCoordinates().getVertical(),
//                                          command.getCoordinates().getHorizontal(),
//                                          command.getBarrierPosition());
//                        break;
//                    case MARKER:
//                        game.moveMarker(command.getCoordinates().getVertical(),
//                                        command.getCoordinates().getHorizontal());
//                        break;
//                    case HELP:
//                        drawer.drawHelp();
//                        break;
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                System.out.println();
//                drawer.drawTurn();
//                continue;
//            }
//
//            System.out.println();
//            drawer.drawPlayerInformation(Player.TOP);
//            drawer.drawField();
//            drawer.drawPlayerInformation(Player.BOTTOM);
//            drawer.drawTurn();
//        }
//
//    }

}
