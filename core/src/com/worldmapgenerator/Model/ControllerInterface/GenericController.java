package com.worldmapgenerator.Model.ControllerInterface;

import com.worldmapgenerator.Input.CommandType;

public interface GenericController {

    /**
     * Обновляет состояние программы в соответствии с временем, прошедшим с последнего обновления
     *
     * @param tickDuration - время с последнего обновления
     */
    void update(double tickDuration);

    /**
     * Удаляет все ресурсы программы
     */
    void dispose();

    /**
     * Получает команду с ввода и выполняет действие в соответствии с её типом
     *
     * @param command - тип команды
     */
    void receiveCommand(CommandType command);

}
