package com.towersvault.halloween.game.rooms;

import com.towersvault.halloween.game.AbstractRoom;
import com.towersvault.halloween.game.RoomHandler;

public class RoomNull extends AbstractRoom
{
	public RoomNull()
	{
		super(0f, 0f, 0f);
	}
	
	@Override
	public AbstractRoom addAction(RoomHandler.RoomActionState actionState, String interactionData)
	{
		return null;
	}
	
	@Override
	public AbstractRoom registerActor(String actorName, int decalHashcode, float spawnX, float spawnY, float spawnZ)
	{
		return null;
	}
	
	@Override
	public void update()
	{
	
	}
	
	@Override
	public void clear()
	{
	
	}
}
