package com.worldmapgenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.worldmapgenerator.Controller.SimplestDelaunayController;

public class main extends ApplicationAdapter {

	private SimplestDelaunayController controller;
	
	@Override
	public void create () {
		controller = new SimplestDelaunayController();
	}

	@Override
	public void render () {
		controller.update();
	}
	
	@Override
	public void dispose () {
		controller.dispose();
	}

}
