package com.worldmapgenerator.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;

public class SimplestKeyboardInput implements GenericInput {

    public void listenForInput(GenericController controller) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            controller.receiveCommand(CommandType.RELAX_MAP);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            controller.receiveCommand(CommandType.GENERATE_NEW_MAP);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            controller.receiveCommand(CommandType.HIDE_POINTS);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            controller.receiveCommand(CommandType.HIDE_CONNECTIONS);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            controller.receiveCommand(CommandType.HIDE_POLYGONS);
        }
    }

}
