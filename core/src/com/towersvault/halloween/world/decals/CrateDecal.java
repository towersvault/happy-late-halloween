package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.towersvault.halloween.world.BoxesHandler;
import com.towersvault.halloween.world.WorldHandler;

public class CrateDecal extends AbstractDecal
{
	private Vector2 crateVector;

	public CrateDecal(Decal dExt1, Decal dExt2, float x, float z)
	{
		super(dExt1, dExt2);
		crateVector = new Vector2(x * BoxesHandler.TILE_SIZE, z * BoxesHandler.TILE_SIZE);

		/*dFrontE.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - 1f);
		dRightE.setPosition(TILE_SIZE * x + TILE_SIZE / 2f - 1f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
		dRightE.rotateY(90f);
		dBackE.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE + 1f);
		dLeftE.setPosition(TILE_SIZE * x - TILE_SIZE / 2f + 1f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
		dLeftE.rotateY(90f);*/
	}

	@Override
	public void updateRenderOrder()
	{
		if(crateVector.x > WorldHandler.inst.getBodyX()
				&& crateVector.y > WorldHandler.inst.getBodyY())
		{
			super.decals.get(0).setPosition(crateVector.x, BoxesHandler.TILE_SIZE / 2f - 1f, crateVector.y - BoxesHandler.TILE_SIZE + 1f);
			super.decals.get(0).setRotation(0f, 0f, 0f);

			super.decals.get(1).setPosition(crateVector.x - BoxesHandler.TILE_SIZE / 2f + 1f, BoxesHandler.TILE_SIZE / 2f - 1f, crateVector.y - BoxesHandler.TILE_SIZE / 2f);
			super.decals.get(1).setRotation(90f, 0f, 0f);
		}
	}
}
