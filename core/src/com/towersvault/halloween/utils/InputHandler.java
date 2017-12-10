package com.towersvault.halloween.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler
{
	// For camera movement, see Renderer class.
	
	public static final InputHandler inst = new InputHandler();
	
	public void checkInput()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.V))
			Constants.DYNAMIC_PHYSICS = !Constants.DYNAMIC_PHYSICS;
	}
}
