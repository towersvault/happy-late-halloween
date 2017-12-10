package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class ItemDecal extends AbstractDecal
{
	private boolean yMovingUp = false;
	
	public ItemDecal(Decal d1)
	{
		super(d1, null);
		super.onlyOneDecal = true;
	}
	
	@Override
	public void updateRenderOrder()
	{
		super.decals.get(0).rotateY(2f);
		if(!yMovingUp)
		{
			super.decals.get(0).setY(super.decals.get(0).getY() - 0.025f);
			if(super.decals.get(0).getY() < super.decals.get(0).getHeight() / 2f + 1f)
				yMovingUp = true;
		}
		else if(yMovingUp)
		{
			super.decals.get(0).setY(super.decals.get(0).getY() + 0.025f);
			if(super.decals.get(0).getY() > super.decals.get(0).getHeight() / 2f + 3f)
				yMovingUp = false;
		}
	}
}
