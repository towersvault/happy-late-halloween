package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.decals.AbstractDecal;

public abstract class AbstractEntity extends AbstractDecal
{
	public AbstractEntity(Array<Decal> decals)
	{
		super(null, null);
		super.multiDecal = true;
		super.decals.addAll(decals.toArray());
	}
	
	public Array<Decal> getDecals()
	{
		return super.decals;
	}
}
