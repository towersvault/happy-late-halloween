package com.towersvault.halloween.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.towersvault.halloween.game.RoomHandler;
import com.towersvault.halloween.render.Renderer;
import com.towersvault.halloween.utils.*;
import com.towersvault.halloween.utils.audio.AudioController;
import com.towersvault.halloween.world.entities.EntityController;
import com.badlogic.gdx.controllers.Controller;
import io.anuke.gif.GifRecorder;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;

public class MainScreen extends AbstractGameScreen
{
	private Stage stage;
	private Stack stack;
	
	private SpriteBatch gifBatch;
	private GifRecorder gif;
	
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
		
		addActors();
	}
	
	private void addActors()
	{
		Scene2DHelper.inst.init();

		//stack.addActor(Scene2DHelper.inst.buildFilter());

		stack.addActor(Scene2DHelper.inst.buildInventoryIcons());
		stack.addActor(Scene2DHelper.inst.buildDialogue());
		
		stack.addActor(Scene2DHelper.inst.buildDebug());
	}
	
	@Override
	public void render(float deltaTime)
	{
		InputHandler.inst.checkInput();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f / 255f, 25f / 255f, 38f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		Renderer.inst.render();
		
		EntityController.inst.update();
		
		stage.act(deltaTime);
		stage.draw();
		
		Scene2DCrt.inst.render(deltaTime);
		
		gif.update();
		
		RoomHandler.inst.update();
		
		Scene2DHelper.inst.update();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show()
	{
		AspectRatioHelper.inst.findAspectRatio();
		
		Assets.inst.init();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stack = new Stack();
		
		init();
		
		Scene2DCrt.inst.init();
		
		Renderer.inst.init();
		
		InputHandler.inst.init();
		
		gifBatch = new SpriteBatch();
		gif = new GifRecorder(gifBatch);
		
		RoomHandler.inst.loadRoom(RoomHandler.Rooms.ROOM_1_JOKE);
		
		if(InputHandler.inst.isControllerConnected())
			Gdx.input.setCursorCatched(true);
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
