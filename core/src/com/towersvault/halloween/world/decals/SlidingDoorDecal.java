package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.BoxesHandler;
import com.towersvault.halloween.world.WorldHandler;

public class SlidingDoorDecal extends AbstractDecal
{
	public Array<Decal> decals = new Array<Decal>();
	private boolean openDoors = false;
	private float x1, z1;
	
	public SlidingDoorDecal(Decal d1, Decal d2)
	{
		super(d1, d2);
		openDoors = false;
		x1 = d1.getX();
		z1 = d1.getZ();
	}
	
	@Override
	public void updateRenderOrder()
	{
		if((x1 < WorldHandler.inst.getBodyX() - WorldHandler.inst.TILE_WIDTH * 2f ||
				x1 > WorldHandler.inst.getBodyX() + WorldHandler.inst.TILE_WIDTH * 2f)
				|| (z1 - BoxesHandler.TILE_SIZE / 2f < WorldHandler.inst.getBodyY() - WorldHandler.inst.TILE_WIDTH * 2f ||
				z1 - BoxesHandler.TILE_SIZE / 2f > WorldHandler.inst.getBodyY() + WorldHandler.inst.TILE_WIDTH * 2f))
		{
			openDoors = true;
		}
		else
			openDoors = false;
		
		if(!openDoors)
		{
			if(super.decals.get(0).getZ() < z1 + 14f)
			{
				super.decals.get(0).translateZ(0.5f);
				super.decals.get(1).translateZ(-0.5f);
			}
		}
		else
		{
			if(super.decals.get(0).getZ() > z1)
			{
				super.decals.get(0).translateZ(-0.5f);
				super.decals.get(1).translateZ(0.5f);
			}
		}
	}
}
