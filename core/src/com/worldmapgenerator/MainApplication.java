package com.worldmapgenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.worldmapgenerator.Controller.SimplestVoronoiController;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;

public class MainApplication extends ApplicationAdapter {

    private GenericController controller;

    @Override
    public void create() {
        controller = new SimplestVoronoiController();
    }

    @Override
    public void render() {
        controller.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        controller.dispose();
    }
}
