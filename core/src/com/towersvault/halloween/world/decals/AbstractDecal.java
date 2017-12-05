package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractDecal
{
	public Array<Decal> decals = new Array<Decal>();
	public boolean renderOnlyOneSide = false;
	public boolean onlyOneDecal = false;
	
	public AbstractDecal(Decal d1, Decal d2)
	{
		decals.add(d1);
		decals.add(d2);
	}
	
	public abstract void updateRenderOrder();
}
