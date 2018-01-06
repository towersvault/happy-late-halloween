package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.WorldHandler;

public class PlantDecal extends AbstractDecal
{
	public Array<Decal> decals = new Array<Decal>();
	private int order = 0;
	private Vector2 plantVec;
	private int pointingDirection;
	
	public PlantDecal(Decal d1, Decal d2)
	{
		super(d1, d2);
		plantVec = new Vector2(d1.getPosition().x, d1.getPosition().z);
	}
	
	/*
	 * Used for alphaDecals.
	 *
	 * 	0		1
	 *
	 * 		S
	 *
	 * 	1		0
	 */
	@Override
	public void updateRenderOrder()
	{
		if((super.decals.get(0).getPosition().x < WorldHandler.inst.getBodyX()
				&& super.decals.get(0).getPosition().z < WorldHandler.inst.getBodyY())
				|| (super.decals.get(0).getPosition().x > WorldHandler.inst.getBodyX()
				&& super.decals.get(0).getPosition().z > WorldHandler.inst.getBodyY()))
		{
			if(order == 1)
			{
				super.decals.reverse();
				order = 0;
			}
		}
		else if((super.decals.get(0).getPosition().x > WorldHandler.inst.getBodyX()
				&& super.decals.get(0).getPosition().z < WorldHandler.inst.getBodyY())
				|| (super.decals.get(0).getPosition().x < WorldHandler.inst.getBodyX()
				&& super.decals.get(0).getPosition().z > WorldHandler.inst.getBodyY()))
		{
			if(order == 0)
			{
				super.decals.reverse();
				order = 1;
			}
		}
		
		for(int i = 0; i < super.decals.size; i++)
		{
			if((super.decals.get(i).getX() < WorldHandler.inst.getBodyX() - WorldHandler.inst.TILE_WIDTH * 2f ||
					super.decals.get(i).getX() > WorldHandler.inst.getBodyX() + WorldHandler.inst.TILE_WIDTH * 2f)
					|| (super.decals.get(i).getZ() < WorldHandler.inst.getBodyY() - WorldHandler.inst.TILE_WIDTH * 2f ||
					super.decals.get(i).getZ() > WorldHandler.inst.getBodyY() + WorldHandler.inst.TILE_WIDTH * 2f))
			{
				if((super.decals.get(i).getX() < WorldHandler.inst.getBodyX() - WorldHandler.inst.TILE_WIDTH * 5f ||
						super.decals.get(i).getX() > WorldHandler.inst.getBodyX() + WorldHandler.inst.TILE_WIDTH * 5f)
						|| (super.decals.get(i).getZ() < WorldHandler.inst.getBodyY() - WorldHandler.inst.TILE_WIDTH * 5f ||
						super.decals.get(i).getZ() > WorldHandler.inst.getBodyY() + WorldHandler.inst.TILE_WIDTH * 5f))
					super.decals.get(i).setColor(75f / 255f, 75f / 255f, 75f / 255f, super.decals.get(i).getColor().a);
				else
					super.decals.get(i).setColor(150f / 255f, 150f / 255f, 150f / 255f, super.decals.get(i).getColor().a);
			}
			else
				super.decals.get(i).setColor(1f, 1f, 1f, super.decals.get(i).getColor().a);
		}
	}
}
