package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;


public class ImpossibleToSetException extends FieldItemException {  //TODO Невозможно Установить Исключение, установить что?, ты хотя бы написал бы когда кидать, и ещё пожалуйста сделай так, чтобы исключение содержало больше информации о себе, нежели одну строчку

    public ImpossibleToSetException(String s) {
        super(s);
    }

}
