package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class FenceDecal extends AbstractDecal
{
	public FenceDecal(Decal d1)
	{
		super(d1, null);
		super.onlyOneDecal = true;
	}
	
	public FenceDecal(Decal d1, Decal d2)
	{
		super(d1, d2);
	}
	
	@Override
	public void updateRenderOrder()
	{
	
	}
}
