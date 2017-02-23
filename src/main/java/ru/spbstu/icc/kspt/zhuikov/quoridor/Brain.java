package ru.spbstu.icc.kspt.zhuikov.quoridor;


abstract public class Brain {

    protected QuoridorField field;

    abstract public Command whatToDo();

}
