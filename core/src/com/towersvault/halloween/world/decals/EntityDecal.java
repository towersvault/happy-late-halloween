package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.towersvault.halloween.world.WorldHandler;

public class EntityDecal extends AbstractDecal
{
	private Vector2 entityVec;
	private int pointingDirection;
	
	public EntityDecal(Decal d1)
	{
		super(d1, null);
		super.onlyOneDecal = true;
		entityVec = new Vector2(d1.getPosition().x, d1.getPosition().y);
	}
	
	@Override
	public void updateRenderOrder()
	{
		entityVec.set(super.decals.get(0).getX(), super.decals.get(0).getZ());
		pointingDirection = (int)entityVec.sub(WorldHandler.inst.getBodyPosition()).angle();
		
		// TODO: Fix the below.
		if(pointingDirection >= 0 && pointingDirection < 45)
			super.decals.get(0).setRotationY(0f);
		else if(pointingDirection >= 45 && pointingDirection < 90)
			super.decals.get(0).setRotationY(45f);
		else if(pointingDirection >= 90 && pointingDirection < 135)
			super.decals.get(0).setRotationY(90f);
		else if(pointingDirection >= 180 && pointingDirection < 225)
			super.decals.get(0).setRotationY(135f);
		else if(pointingDirection >= 225 && pointingDirection < 270)
			super.decals.get(0).setRotationY(225f);
		else if(pointingDirection >= 270 && pointingDirection < 315)
			super.decals.get(0).setRotationY(270f);
		else if(pointingDirection >= 315 && pointingDirection < 360)
			super.decals.get(0).setRotationY(315f);
	}
}
