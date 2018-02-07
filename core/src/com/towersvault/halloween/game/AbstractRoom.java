package com.towersvault.halloween.game;

import com.badlogic.gdx.utils.Array;

public abstract class AbstractRoom
{
	public Array<RoomAction> roomActions = new Array<RoomAction>();
	public float xTrigger, yTrigger, triggerRadius;
	public boolean removeCurrentAction = false;
	public boolean waitForClick = false;
	public boolean roomCompleted = false;
	
	public AbstractRoom(float xTrigger, float yTrigger, float triggerRadius)
	{
		this.xTrigger = xTrigger;
		this.yTrigger = yTrigger;
		this.triggerRadius = triggerRadius;
	}
	
	public abstract AbstractRoom addAction(RoomHandler.RoomActionState actionState, String interactionData);
	
	public void gotClick()
	{
		waitForClick = false;
	}
	
	public abstract void update();
}
