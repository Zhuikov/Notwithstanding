package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;

/**
 * Класс исключений, связанных с логикой на игровом поле.
 */
public class FieldItemException extends Exception {   //TODO Исключение Пункт Области, возможно здесь есть смысл, но я не могу сказать, когда конкретно кидать это исключение

    public FieldItemException(String s) {
        super(s);
    }

}
