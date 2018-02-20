package com.towersvault.halloween.game;

import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.screens.Scene2DHelper;

public abstract class AbstractRoom
{
	public Array<RoomAction> roomActions = new Array<RoomAction>();
	public Array<RoomActor> roomActors = new Array<RoomActor>();
	
	public float xTrigger, yTrigger, triggerRadius;
	public boolean waitingForTrigger = true;
	public boolean removeCurrentAction = false;
	public boolean waitForClick = false;
	public boolean waitForActor = false;
	public boolean roomCompleted = false;
	
	public AbstractRoom(float xTrigger, float yTrigger, float triggerRadius)
	{
		this.xTrigger = xTrigger;
		this.yTrigger = yTrigger;
		this.triggerRadius = triggerRadius;
	}
	
	public abstract AbstractRoom addAction(RoomHandler.RoomActionState actionState, String interactionData);
	
	public abstract AbstractRoom registerActor(String actorName, int decalHashcode, float spawnX, float spawnY, float spawnZ);
	
	public void gotClick()
	{
		Scene2DHelper.inst.setDialogueText("");
		waitForClick = false;
	}
	
	public abstract void update();
	
	public abstract void clear();
}
