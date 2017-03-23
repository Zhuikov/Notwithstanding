package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;

/**
 * Исключение, генерирующееся при попытке сделать ход на занятую клетку поля.
 */
public class CellIsNotEmptyException extends FieldItemException {

    public CellIsNotEmptyException(String s) {
        super(s);
    }

}
