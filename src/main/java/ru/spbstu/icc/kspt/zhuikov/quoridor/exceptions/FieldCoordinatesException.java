package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;


public class FieldCoordinatesException extends ArrayIndexOutOfBoundsException {   //TODO мне не нравится название, Поле Координаты Исключение
    public FieldCoordinatesException(String s) {
        super(s);
    }
}
