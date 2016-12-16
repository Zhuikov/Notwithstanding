package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;


public class ImpossibleToSetItemException extends FieldItemException {  //TODO пожалуйста сделай так, чтобы исключение содержало больше информации о себе, нежели одну строчку

    public ImpossibleToSetItemException(String s) {
        super(s);
    }

}
