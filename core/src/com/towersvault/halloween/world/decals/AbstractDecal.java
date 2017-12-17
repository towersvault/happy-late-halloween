package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractDecal
{
	public Array<Decal> decals = new Array<Decal>();
	public boolean renderOnlyOneSide = false;
	public boolean onlyOneDecal = false;
	public boolean multiDecal = false;
	
	public AbstractDecal(Decal d1, Decal d2)
	{
		if(d1 != null)
			decals.add(d1);
		if(d2 != null)
			decals.add(d2);
	}
	
	public abstract void updateRenderOrder();
}
