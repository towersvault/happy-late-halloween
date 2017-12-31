package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.BoxesHandler;

public class PlaneEntity extends AbstractEntity
{
	/**
	 * 0 -> translate z+
	 * 1 -> translate x+
	 * 2 -> translate z-
	 * 3 -> translate x-
	 */
	private int rotation = 0;
	private int counter = 0;
	private boolean moveUp = true;
	private float baseY = 0f;
	
	public PlaneEntity(Array<Decal> decals)
	{
		super(decals);
		baseY = decals.get(0).getY();
		
		rotate(0);
	}
	
	@Override
	public void updateRenderOrder()
	{
		if(moveUp)
		{
			if(decals.get(0).getY() < baseY + 1f)
			{
				for(int i = 0; i < decals.size; i++)
				{
					decals.get(i).translateY(0.05f);
				}
			}
			else
				moveUp = false;
		}
		else
		{
			if(decals.get(0).getY() > baseY)
			{
				for(int i = 0; i < decals.size; i++)
				{
					decals.get(i).translateY(-0.05f);
				}
			}
			else
				moveUp = true;
		}
		
		decals.get(8).rotateZ(-16f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			move();
	}
	
	public void rotateLeft()
	{
		if(rotation >= 3)
			rotation = 0;
		else
			rotation += 1;
		rotate(rotation);
	}
	
	public void rotateRight()
	{
		if(rotation <= 0)
			rotation = 3;
		else
			rotation -= 1;
		rotate(rotation);
	}
	
	private void move()
	{
		for(int i = 0; i < decals.size; i++)
		{
			if(rotation == 0)
				decals.get(i).translateZ(1f);
			else if(rotation == 1)
				decals.get(i).translateX(1f);
			else if(rotation == 2)
				decals.get(i).translateZ(-1f);
			else
				decals.get(i).translateX(-1f);
		}
	}
	
	private void rotate(int rotation)
	{
		this.rotation = rotation;
		if(rotation == 0)
		{
			decals.get(0).setRotation(0f, 90f, 0f);
			
			decals.get(1).setPosition(decals.get(0).getX() + decals.get(1).getWidth() / 2f, decals.get(0).getY() + decals.get(1).getWidth() / 2f, decals.get(0).getZ());
			decals.get(1).setRotation(90f, 0f, 90f);
			
			decals.get(2).setPosition(decals.get(0).getX() - decals.get(2).getWidth() / 2f, decals.get(0).getY() + decals.get(2).getWidth() / 2f, decals.get(0).getZ());
			decals.get(2).setRotation(90f, 0f, 90f);
			
			decals.get(3).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(3).getHeight() / 2f, decals.get(0).getZ() + decals.get(0).getHeight() / 2f);
			decals.get(3).setRotation(0f, 0f, 0f);
			
			decals.get(4).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(4).getHeight() / 2f, decals.get(0).getZ() - decals.get(0).getHeight() / 2f);
			decals.get(4).setRotation(0f, 0f, 0f);
			
			decals.get(5).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() + 0.5f);
			decals.get(5).setRotation(0f, 90f, 0f);
			
			decals.get(6).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(6).getHeight() / 2f, decals.get(0).getZ() - decals.get(0).getHeight() / 2f);
			decals.get(6).setRotation(90f, 0f, 0f);
			
			decals.get(7).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() - decals.get(0).getHeight() / 2f);
			decals.get(7).setRotation(0f, 90f, 0f);
			
			decals.get(8).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() + decals.get(0).getHeight() / 2f + 1f);
			decals.get(8).setRotation(0f, 0f, 0f);
		}
		else if(rotation == 1)
		{
			decals.get(0).setRotation(90f, 90f, 0f);
			
			decals.get(1).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(1).getWidth() / 2f, decals.get(0).getZ() + decals.get(1).getWidth() / 2f);
			decals.get(1).setRotation(180f, 0f, 90f);
			
			decals.get(2).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(2).getWidth() / 2f, decals.get(0).getZ() - decals.get(2).getWidth() / 2f);
			decals.get(2).setRotation(180f, 0f, 90f);
			
			decals.get(3).setPosition(decals.get(0).getX() + decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(3).getHeight() / 2f, decals.get(0).getZ());
			decals.get(3).setRotation(90f, 0f, 0f);
			
			decals.get(4).setPosition(decals.get(0).getX() - decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(4).getHeight() / 2f, decals.get(0).getZ());
			decals.get(4).setRotation(90f, 0f, 0f);
			
			decals.get(5).setPosition(decals.get(0).getX() + 0.5f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(5).setRotation(90f, 90f, 0f);
			
			decals.get(6).setPosition(decals.get(0).getX() - decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(6).getHeight() / 2f, decals.get(0).getZ());
			decals.get(6).setRotation(180f, 0f, 0f);
			
			decals.get(7).setPosition(decals.get(0).getX() - decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(7).setRotation(0f, 90f, 270f);
			
			decals.get(8).setPosition(decals.get(0).getX() + decals.get(0).getHeight() / 2f + 1f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(8).setRotation(90f, 0f, 0f);
		}
		else if(rotation == 2)
		{
			decals.get(0).setRotation(180f, 90f, 0f);
			
			decals.get(1).setPosition(decals.get(0).getX() + decals.get(1).getWidth() / 2f, decals.get(0).getY() + decals.get(1).getWidth() / 2f, decals.get(0).getZ());
			decals.get(1).setRotation(270f, 0f, 90f);
			
			decals.get(2).setPosition(decals.get(0).getX() - decals.get(2).getWidth() / 2f, decals.get(0).getY() + decals.get(2).getWidth() / 2f, decals.get(0).getZ());
			decals.get(2).setRotation(270f, 0f, 90f);
			
			decals.get(3).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(3).getHeight() / 2f, decals.get(0).getZ() - decals.get(0).getHeight() / 2f);
			decals.get(3).setRotation(180f, 0f, 0f);
			
			decals.get(4).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(4).getHeight() / 2f, decals.get(0).getZ() + decals.get(0).getHeight() / 2f);
			decals.get(4).setRotation(180f, 0f, 0f);
			
			decals.get(5).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() - 0.5f);
			decals.get(5).setRotation(180f, 90f, 0f);
			
			decals.get(6).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(6).getHeight() / 2f, decals.get(0).getZ() + decals.get(0).getHeight() / 2f);
			decals.get(6).setRotation(270f, 0f, 0f);
			
			decals.get(7).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() + decals.get(0).getHeight() / 2f);
			decals.get(7).setRotation(0f, 270f, 0f);
			
			decals.get(8).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ() - decals.get(0).getHeight() / 2f - 1f);
			decals.get(8).setRotation(0f, 180f, 0f);
		}
		else
		{
			decals.get(0).setRotation(270f, 90f, 0f);
			
			decals.get(1).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(1).getWidth() / 2f, decals.get(0).getZ() + decals.get(1).getWidth() / 2f);
			decals.get(1).setRotation(0f, 0f, 90f);
			
			decals.get(2).setPosition(decals.get(0).getX(), decals.get(0).getY() + decals.get(2).getWidth() / 2f, decals.get(0).getZ() - decals.get(2).getWidth() / 2f);
			decals.get(2).setRotation(0f, 0f, 90f);
			
			decals.get(3).setPosition(decals.get(0).getX() - decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(3).getHeight() / 2f, decals.get(0).getZ());
			decals.get(3).setRotation(270f, 0f, 0f);
			
			decals.get(4).setPosition(decals.get(0).getX() + decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(4).getHeight() / 2f, decals.get(0).getZ());
			decals.get(4).setRotation(270f, 0f, 0f);
			
			decals.get(5).setPosition(decals.get(0).getX() - 0.5f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(5).setRotation(270f, 90f, 0f);
			
			decals.get(6).setPosition(decals.get(0).getX() + decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(6).getHeight() / 2f, decals.get(0).getZ());
			decals.get(6).setRotation(0f, 0f, 0f);
			
			decals.get(7).setPosition(decals.get(0).getX() + decals.get(0).getHeight() / 2f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(7).setRotation(0f, 270f, 90f);
			
			decals.get(8).setPosition(decals.get(0).getX() - decals.get(0).getHeight() / 2f - 1f, decals.get(0).getY() + decals.get(0).getWidth() / 2f, decals.get(0).getZ());
			decals.get(8).setRotation(270f, 0f, 0f);
		}
	}
}
