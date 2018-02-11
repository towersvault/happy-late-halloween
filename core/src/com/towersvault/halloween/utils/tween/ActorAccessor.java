package com.towersvault.halloween.utils.tween;

import com.aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor>
{
	public static final int Y_TRANSLATE = 0;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues)
	{
		switch(tweenType)
		{
			case Y_TRANSLATE:
				returnValues[0] = target.getY();
				return 1;
			default:
				assert false;
				return 1;
		}
	}
	
	@Override
	public void setValues(Actor target, int tweenType, float[] newValues)
	{
		switch(tweenType)
		{
			case Y_TRANSLATE:
				target.setY(newValues[0]);
				break;
			default:
				assert false;
		}
	}
	
}
