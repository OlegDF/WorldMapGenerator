package com.worldmapgenerator.Model.ControllerInterface;

public interface GenericController {

    /**
     * Обновляет состояние программы в соответствии с временем, прошедшим с последнего обновления
     * @param tickDuration - время с последнего обновления
     */
    void update(double tickDuration);

    /**
     * Удаляет все ресурсы программы
     */
    void dispose();

}
