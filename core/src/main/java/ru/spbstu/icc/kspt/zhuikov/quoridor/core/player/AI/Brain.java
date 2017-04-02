package ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.AI;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.GameLogic;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.QuoridorField;

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
