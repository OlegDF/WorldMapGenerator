package com.worldmapgenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.worldmapgenerator.Controller.SimplestVoronoiController;

public class main extends ApplicationAdapter {

	private SimplestVoronoiController controller;
	
	@Override
	public void create () {
		controller = new SimplestVoronoiController();
	}

	@Override
	public void render () {
		controller.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		controller.dispose();
	}

}
