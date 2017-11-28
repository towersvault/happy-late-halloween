package com.towersvault.halloween;

import com.badlogic.gdx.Game;
import com.towersvault.halloween.screens.MainScreen;

public class HalloweenMain extends Game
{
	@Override
	public void create()
	{
		setScreen(new MainScreen(this));
	}
}
