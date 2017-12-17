package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.BoxesHandler;
import com.towersvault.halloween.world.WorldHandler;

public class BombEntity extends AbstractEntity
{
	private boolean bounce = false;
	private int pointingDirection = 0;
	
	public BombEntity(Array<Decal> decals)
	{
		super(decals);
	}
	
	@Override
	public void updateRenderOrder()
	{
		Vector2 bombVec = new Vector2(super.decals.get(0).getX(), super.decals.get(0).getZ());
		pointingDirection = (int)bombVec.sub(WorldHandler.inst.getBodyPosition()).angle();
		
		super.decals.get(0).setRotationY(-(float)pointingDirection - 90f);
		
		if(bounce)
		{
			super.decals.get(0).setWidth(decals.get(0).getWidth() + 0.15f);
			super.decals.get(0).setHeight(decals.get(0).getHeight() - 0.15f);
			
			if(super.decals.get(0).getHeight() <= 9f)
				bounce = false;
		}
		else
		{
			super.decals.get(0).setWidth(decals.get(0).getWidth() - 0.15f);
			super.decals.get(0).setHeight(decals.get(0).getHeight() + 0.15f);
			
			if(super.decals.get(0).getHeight() >= 11f)
				bounce = true;
		}
	}
}
