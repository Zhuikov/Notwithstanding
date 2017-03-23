package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;

/**
 * Исключение, генерируещееся при попытке сходить фишкой на клетку,
 * расстояние до которой больше допустимого.
 */
public class TooLongDistanceException extends FieldItemException {

    public TooLongDistanceException(String s) {
        super(s);
    }

}
