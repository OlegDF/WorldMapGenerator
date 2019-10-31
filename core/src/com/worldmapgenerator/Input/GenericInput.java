package com.worldmapgenerator.Input;

import com.worldmapgenerator.Model.ControllerInterface.GenericController;

public interface GenericInput {

    /**
     * Узнает, был ли с последнего обновления ввод, нужный приложению (с клавиатуры, мышки и т. д.); если был, передает
     * его контроллеру
     *
     * @param controller - получатель сигналов ввода
     */
    void listenForInput(GenericController controller);

}
