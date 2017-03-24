package ru.spbstu.icc.kspt.zhuikov.quoridor.player.AI;

import ru.spbstu.icc.kspt.zhuikov.quoridor.game.GameLogic;
import ru.spbstu.icc.kspt.zhuikov.quoridor.game.QuoridorField;

/**
 * Абстрактный класс, представляющий логику принятия решений игроков, обладающих искуственным интеллектом.
 */
abstract public class Brain {

    /**
     * Логика игры, используемая при принятии решения.
     */
    protected GameLogic GL;

    /**
     * Игровое поле.
     */
    protected QuoridorField quoridorField;

    /**
     * Возвращает команду, содержащую информацию для выполнения следующего хода.
     * @see Command ;
     */
    abstract public Command whatToDo();
}
