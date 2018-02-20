package com.towersvault.halloween.utils.tween;

import com.aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class DecalAccessor implements TweenAccessor<Decal>
{
	public static final int X_TRANSLATE = 0;
	public static final int Y_TRANSLATE = 1;
	public static final int Z_TRANSLATE = 2;
	
	@Override
	public int getValues(Decal target, int tweenType, float[] returnValues)
	{
		switch(tweenType)
		{
			case X_TRANSLATE:
				returnValues[0] = target.getX();
				return 1;
			case Y_TRANSLATE:
				returnValues[0] = target.getY();
				return 1;
			case Z_TRANSLATE:
				returnValues[0] = target.getZ();
				return 1;
			default:
				assert false;
				return 1;
		}
	}
	
	@Override
	public void setValues(Decal target, int tweenType, float[] newValues)
	{
		switch(tweenType)
		{
			case X_TRANSLATE:
				target.setX(newValues[0]);
				break;
			case Y_TRANSLATE:
				target.setY(newValues[0]);
				break;
			case Z_TRANSLATE:
				target.setZ(newValues[0]);
				break;
			default:
				assert false;
				break;
		}
	}
}
