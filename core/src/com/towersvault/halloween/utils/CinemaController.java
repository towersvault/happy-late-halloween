package com.towersvault.halloween.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.towersvault.halloween.render.Renderer;
import com.towersvault.halloween.world.BoxesHandler;
import com.towersvault.halloween.world.WorldHandler;

public class CinemaController
{
	public static final CinemaController inst = new CinemaController();
	
	private boolean cameraTween = false;
	private float speed = 15f;
	private boolean tweenOut = false;
	
	public Vector2 scarecrowCoordinates = new Vector2();
	private boolean scarewcrowOn = false;
	
	private long soundId = 0;
	private float volume = 0f;
	private Sound corrupt = Gdx.audio.newSound(Gdx.files.internal("corrupted.ogg"));
	
	public void init()
	{
	
	}
	
	public void checkInput()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.R))
		{
			System.out.println("Cinema positioning set.");
			WorldHandler.inst.setPlayerPosition((float)WorldHandler.inst.getBodyTileX() * WorldHandler.inst.TILE_WIDTH,
					(float)WorldHandler.inst.getBodyTileZ() * WorldHandler.inst.TILE_WIDTH + WorldHandler.inst.TILE_WIDTH / 2f);
			Renderer.inst.setCameraRotation(90f);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F))
			cameraTween = !cameraTween;
		if(Gdx.input.isKeyJustPressed(Input.Keys.V))
		{
			speed -= 0.25f;
			System.out.println("Speed - : " + speed);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.C))
		{
			speed += 0.25f;
			System.out.println("Speed + : " + speed);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.X))
			tweenOut = true;
		
		if(speed > 0f && tweenOut)
		{
			speed -= 0.025f;
			if(speed < 0f)
				speed = 0f;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z))
		{
			if(!scarewcrowOn)
			{
				BoxesHandler.inst.setTexture(scarecrowCoordinates.x, scarecrowCoordinates.y, Assets.inst.staticSprite.scarecrowOn);
				soundId = corrupt.play(volume);
				corrupt.loop();
			}
			scarewcrowOn = true;
			
			volume = (1f - (((scarecrowCoordinates.y - WorldHandler.inst.getBodyY()) / WorldHandler.inst.TILE_WIDTH) / 36f)) / 8f;
			System.out.println(volume);
			corrupt.setVolume(soundId, volume);
		}
		else
		{
			if(scarewcrowOn)
			{
				BoxesHandler.inst.setTexture(scarecrowCoordinates.x, scarecrowCoordinates.y, Assets.inst.staticSprite.scarecrow);
				corrupt.stop();
			}
			scarewcrowOn = false;
		}
		
		if(cameraTween)
		{
			WorldHandler.inst.setPlayerPosition(WorldHandler.inst.getBodyX(), WorldHandler.inst.getBodyY() + speed * Gdx.graphics.getDeltaTime());
		}
	}
}
