package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public class PlaneEntity extends AbstractEntity
{
	
	public PlaneEntity(Array<Decal> decals)
	{
		super(decals);
	}
	
	@Override
	public void updateRenderOrder()
	{
		decals.get(decals.size - 1).rotateZ(-22f);
		
		for(int i = 0; i < decals.size; i++)
		{
			decals.get(i).translateZ(0.25f);
		}
	}
}
