package com.towersvault.halloween.world.entities;

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
	
	public PlaneEntity(Array<Decal> decals)
	{
		super(decals);
		
		rotate(1);
	}
	
	@Override
	public void updateRenderOrder()
	{
		/*counter++;
		
		if(counter == 60)
		{
			counter = 0;
			rotation++;
			if(rotation <= 3)
			{
				rotate(rotation);
			}
			else
			{
				rotation = 0;
				rotate(rotation);
			}
		}*/
		
		/*decals.get(decals.size - 1).rotateZ(-22f);*/
		
		/*if(rotation == 0)
		{
			for(int i = 0; i < decals.size; i++)
			{
				decals.get(i).translateZ(0.25f);
			}
			if(decals.get(0).getZ() >= 5f * BoxesHandler.TILE_SIZE)
				rotate(1);
		}
		else if(rotation == 1)
		{
			for(int i = 0; i < decals.size; i++)
			{
				decals.get(i).translateX(-0.25f);
			}
			if(decals.get(0).getX() <= -5f * BoxesHandler.TILE_SIZE)
				rotate(2);
		}
		else if(rotation == 2)
		{
			for(int i = 0; i < decals.size; i++)
			{
				decals.get(i).translateZ(-0.25f);
			}
			if(decals.get(0).getZ() <= 0f)
				rotate(3);
		}
		else if(rotation == 3)
		{
			for(int i = 0; i < decals.size; i++)
			{
				decals.get(i).translateX(0.25f);
			}
			if(decals.get(0).getX() >= 0f)
				rotate(0);
		}*/
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
		}
	}
}
