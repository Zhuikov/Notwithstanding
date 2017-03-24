package ru.spbstu.icc.kspt.zhuikov.quoridor.player.AI;


import ru.spbstu.icc.kspt.zhuikov.quoridor.game.QuoridorCore;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.PlayerPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.player.UsualPlayer;

/**
 * Класс, представляющий игрока с искусственным интеллектом (далее "бота").
 */
public class BotPlayer extends UsualPlayer {

    /**
     * "Мозг" бота.
     * @see Brain ;
     */
    private BotBrain brain;

    /**
     * Конструктор бота.
     * @param core - ссылка на ядро игры, которое используется ботом.
     * @param position - начальное расположение фишки бота на игровом поле.
     */
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
