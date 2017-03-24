package ru.spbstu.icc.kspt.zhuikov.quoridor.player.AI;


import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.game.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.QuoridorPlayer;

/**
 * Класс, представляющий игрока "лиса" с искуственным элементом.
 */
public class Fox extends QuoridorPlayer {

    /**
     * "Мозг" лисы.
     * @see Brain;
     */
    private FoxBrain brain;

    /**
     * Конструктор класса.
     * @param core - ядро, которое будет использоваться лисой.
     */
    public Fox(QuoridorCore core) {

        this.core = core;
        owner = Owner.FOX;

        brain = new FoxBrain(core.getField(), this);

    }

    @Override
    public void makeMove() {

        core.setCurrentPlayer(this);
        Command command = brain.whatToDo();

        if (command.getCommandType() == CommandType.MARKER) {
            try {
                core.moveMarker(command.getDestination());
            } catch (FieldItemException e) {
                throw new AssertionError("fox.makeMove()");
            }
        } else {
            throw new AssertionError("the fox can only move marker");
        }
    }
}
