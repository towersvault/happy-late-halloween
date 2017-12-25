package com.towersvault.halloween.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.towersvault.halloween.HalloweenMain;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		/*int fullscreenWidth = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		int fullscreenHeight = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		
		config.width = fullscreenWidth;
		config.height = fullscreenHeight;
		
		config.fullscreen = true;*/
		
		config.width = 1280;
		config.height = 720;
		
		//config.samples = 32;
		
		config.resizable = false;
		
		config.title = "Super Secret Project";
		
		new LwjglApplication(new HalloweenMain(), config);
	}
}
