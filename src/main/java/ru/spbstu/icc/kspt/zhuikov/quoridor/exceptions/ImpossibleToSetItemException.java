package ru.spbstu.icc.kspt.zhuikov.quoridor.exceptions;

/**
 * Исключение, генерирующееся при попытке сделать неверный ход:
 * ---- ход фишкой на текущую клетку;
 * ---- попытка "перепрыгнуть" фишкой перегородку;
 * ---- недостаток места для установки перегородки;
 * ---- попытка установить перегородку, блокирующую игрока;
 */
public class ImpossibleToSetItemException extends FieldItemException {  //TODO пожалуйста сделай так, чтобы исключение содержало больше информации о себе, нежели одну строчку

    public ImpossibleToSetItemException(String s) {
        super(s);
    }

}
