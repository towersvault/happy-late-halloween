package com.towersvault.halloween.game;

public class RoomAction
{
	public RoomHandler.RoomActionState actionState;
	public String talkOutput;
	public int pause;
	
	// Actor only
	public String actorName;
	public float x;
	public float y;
	public float z;
	
	public RoomAction(RoomHandler.RoomActionState actionState, String talkOutput)
	{
		this.actionState = actionState;
		this.talkOutput = talkOutput;
	}
	
	public RoomAction(RoomHandler.RoomActionState actionState, int pause)
	{
		this.actionState = actionState;
		this.pause = pause;
	}
	
	public RoomAction(RoomHandler.RoomActionState actionState, String[] actorData)
	{
		this.actionState = actionState;
		this.actorName = actorData[0];
		this.x = Float.parseFloat(actorData[1]);
		this.y = Float.parseFloat(actorData[2]);
		this.z = Float.parseFloat(actorData[3]);
	}
}
