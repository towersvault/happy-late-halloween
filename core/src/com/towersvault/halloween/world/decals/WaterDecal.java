package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class WaterDecal extends AbstractDecal
{
	
	public WaterDecal(Decal d1)
	{
		super(d1, null);
		super.onlyOneDecal = true;
	}
	
	@Override
	public void updateRenderOrder()
	{
		//super.decals.get(0).setTextureRegion();
	}
}
