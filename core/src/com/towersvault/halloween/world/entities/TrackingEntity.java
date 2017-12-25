package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.WorldHandler;

public class TrackingEntity extends AbstractEntity
{
	private boolean bounce = false;
	private int pointingDirection = 0;
	private float decalHeight = 0f;
	
	public TrackingEntity(Array<Decal> decals)
	{
		super(decals);
		decalHeight = decals.get(0).getHeight();
	}
	
	@Override
	public void updateRenderOrder()
	{
		Vector2 entityVec = new Vector2(super.decals.get(0).getX(), super.decals.get(0).getZ());
		pointingDirection = (int)entityVec.sub(WorldHandler.inst.getBodyPosition()).angle();
		
		super.decals.get(0).setRotationY(-(float)pointingDirection - 90f);
		
		if(bounce)
		{
			//super.decals.get(0).setWidth(decals.get(0).getWidth() + 0.15f);
			super.decals.get(0).setHeight(decals.get(0).getHeight() - 0.15f);
			super.decals.get(0).setY(decals.get(0).getHeight() / 2f);
			
			if(super.decals.get(0).getHeight() <= decalHeight - 1f)
				bounce = false;
		}
		else
		{
			//super.decals.get(0).setWidth(decals.get(0).getWidth() - 0.15f);
			super.decals.get(0).setHeight(decals.get(0).getHeight() + 0.15f);
			super.decals.get(0).setY(decals.get(0).getHeight() / 2f);
			
			if(super.decals.get(0).getHeight() >= decalHeight + 1f)
				bounce = true;
		}
	}
}