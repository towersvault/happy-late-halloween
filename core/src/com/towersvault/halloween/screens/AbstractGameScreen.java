package com.towersvault.halloween.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/*
 *     /\__/\
 *    /`    '\
 *  === 0  0 ===
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/#######o
 *   
 * Looking through code is fun, is it not?
 * 
 * Please don't leak any information from the code.
 * 
 * Thanks! :)
 */

abstract class AbstractGameScreen implements Screen
{
	protected Game game;
	
	public AbstractGameScreen(Game game)
	{
		this.game = game;
	}
	
	@Override
	public abstract void render(float deltaTime);
	@Override
	public abstract void resize(int width, int height);
	@Override
	public abstract void show();
	@Override
	public abstract void hide();
	@Override
	public abstract void pause();
	
	@Override
	public void resume()
	{
		//Assets.inst.init(new AssetManager());
	}
	
	@Override
	public void dispose()
	{
		//Assets.inst.dispose();
		//COther.GAME_SKIN.dispose();
	}
}
