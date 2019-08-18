package com.worldmapgenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.worldmapgenerator.Model.QuadraticEquation;

public class main extends ApplicationAdapter {
	
	@Override
	public void create () {
		System.out.println(new QuadraticEquation(1, 2, -9).getLesserRoot() + " " + new QuadraticEquation(1, 2, -9).getGreaterRoot());
	}

	@Override
	public void render () {
	}
	
	@Override
	public void dispose () {
	}

}
