package com.towersvault.halloween.game;

public class RoomAction
{
	public RoomHandler.RoomActionState actionState;
	public String talkOutput;
	public int pause;
	
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
}
