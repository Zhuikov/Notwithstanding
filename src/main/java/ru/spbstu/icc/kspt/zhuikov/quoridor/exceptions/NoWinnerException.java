package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;


public class NoWinnerException extends Exception {            //TODO я бы не стал делать исключение для рядовой ситуации
    public NoWinnerException(String s) {
        super(s);
    }
}
