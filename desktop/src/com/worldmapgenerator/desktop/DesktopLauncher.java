package com.worldmapgenerator.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.worldmapgenerator.main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.vSyncEnabled = true;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screen.width;
		config.height = screen.height;
		config.samples = 5;
		new LwjglApplication(new main(), config);
	}
}
