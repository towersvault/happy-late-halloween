package com.towersvault.halloween.world.decals;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.towersvault.halloween.world.WorldHandler;

public class StopDecal extends AbstractDecal
{
	private String pointingDirection;
	private int frontIndex = 0;
	
	/**
	 *
	 * @param d1 Front decal
	 * @param d2 Back decal
	 * @param pointingDirection Direction sign is pointing in. N = North, S = South, E = East, W = West.
	 */
	public StopDecal(Decal d1, Decal d2, String pointingDirection)
	{
		super(d1, d2);
		super.renderOnlyOneSide = true;
		this.pointingDirection = pointingDirection;
	}
	
	/*
	
	Below used as an example.
	
	pointingDirection = "N"
	
		0
		
		N
		
		1
	
	 */
	@Override
	public void updateRenderOrder()
	{
		if(pointingDirection.equals("N"))
		{
			if(decals.get(frontIndex).getZ() < WorldHandler.inst.getBodyY())
			{
				if(frontIndex == 0)
				{
					frontIndex = 1;
					decals.reverse();
				}
			}
			else
			{
				if(frontIndex == 1)
				{
					frontIndex = 0;
					decals.reverse();
				}
			}
		}
		else if(pointingDirection.equals("S"))
		{
			if(decals.get(frontIndex).getZ() > WorldHandler.inst.getBodyY())
			{
				if(frontIndex == 0)
				{
					frontIndex = 1;
					decals.reverse();
				}
			}
			else
			{
				if(frontIndex == 1)
				{
					frontIndex = 0;
					decals.reverse();
				}
			}
		}
		else if(pointingDirection.equals("E"))
		{
			if(super.decals.get(0).getX() > WorldHandler.inst.getBodyX())
			{
				if(frontIndex == 0)
				{
					frontIndex = 1;
					super.decals.reverse();
				}
			}
			else
			{
				if(frontIndex == 1)
				{
					frontIndex = 0;
					super.decals.reverse();
				}
			}
		}
		else
		{
			if(decals.get(frontIndex).getX() < WorldHandler.inst.getBodyX())
			{
				if(frontIndex == 0)
				{
					frontIndex = 1;
					decals.reverse();
				}
			}
			else
			{
				if(frontIndex == 1)
				{
					frontIndex = 0;
					decals.reverse();
				}
			}
		}
	}
}
