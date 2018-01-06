package com.towersvault.halloween.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.towersvault.halloween.render.Renderer;
import com.towersvault.halloween.utils.Assets;
import com.towersvault.halloween.utils.CinemaController;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.InputHandler;
import com.towersvault.halloween.utils.audio.AudioController;
import com.towersvault.halloween.world.entities.EntityController;
import com.badlogic.gdx.controllers.Controller;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;

public class MainScreen extends AbstractGameScreen
{
	private Stage stage;
	private Stack stack;
	
	public MainScreen(Game game)
	{
		super(game);
	}
	
	private void init()
	{
		stage.clear();
		stack.clear();
		
		stage.addActor(stack);
		stack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void render(float deltaTime)
	{
		InputHandler.inst.checkInput();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f / 255f, 16f / 255f, 24f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);  //| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		
		Renderer.inst.render();
		
		//CinemaController.inst.checkInput();
		
		//AudioController.inst.update();
		
		EntityController.inst.update();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show()
	{
		//AspectRatioHelper.inst.findAspectRatio();
		
		Assets.inst.init();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stack = new Stack();
		
		init();
		
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCursorCatched(true);
		
		Renderer.inst.init();
		
		InputHandler.inst.init();
		
		//AudioController.inst.init();
	}
	
	@Override
	public void hide()
	{
		stage.dispose();
		Renderer.inst.dispose();
	}
	
	@Override
	public void pause()
	{
	
	}
}
