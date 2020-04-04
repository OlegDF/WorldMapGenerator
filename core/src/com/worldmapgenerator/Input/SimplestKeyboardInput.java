package com.worldmapgenerator.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;

public class SimplestKeyboardInput implements GenericInput {

    public void listenForInput(final GenericController controller) {
        //генерация карты
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            controller.receiveCommand(CommandType.RELAX_MAP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            controller.receiveCommand(CommandType.GENERATE_NEW_MAP);
        }
        //показать/спрятать элементы
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            controller.receiveCommand(CommandType.HIDE_POINTS);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            controller.receiveCommand(CommandType.HIDE_CONNECTIONS);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            controller.receiveCommand(CommandType.HIDE_POLYGONS);
        }
        //движение карты
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            controller.receiveCommand(CommandType.MOVE_LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            controller.receiveCommand(CommandType.MOVE_RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            controller.receiveCommand(CommandType.MOVE_DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            controller.receiveCommand(CommandType.MOVE_UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            controller.receiveCommand(CommandType.SCALE_DOWN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            controller.receiveCommand(CommandType.SCALE_UP);
        }
    }

}
