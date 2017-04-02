package ru.spbstu.icc.kspt.zhuikov.quoridor.core.exceptions;

/**
 * Исключение, генерирующееся при попытке обратится к клетке с неверными координатами.
 */
public class FieldBoundsException extends ArrayIndexOutOfBoundsException {
    public FieldBoundsException(String s) {
        super(s);
    }
}
