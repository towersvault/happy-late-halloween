package com.towersvault.halloween.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.towersvault.halloween.game.rooms.Room1Joke;
import com.towersvault.halloween.game.rooms.RoomNull;
import com.towersvault.halloween.screens.Scene2DHelper;

public class RoomHandler
{
	public static final RoomHandler inst = new RoomHandler();
	
	public AbstractRoom room = new RoomNull();
	
	public enum RoomActionState
	{
		TALK,
		PAUSE,
		END,
		MOVE_ACTOR,     // Normal move. Action loader waits until action is complete.
		MOVE_ACTOR_FF   // Fire-and-forget move. Action loader will continue while actor moves.
	}
	
	public enum Rooms
	{
		ROOM_1_JOKE
	}
	
	public void loadRoom(Rooms eRoom)
	{
		room.clear();
		
		switch(eRoom)
		{
			case ROOM_1_JOKE:
				room = new Room1Joke(0f, 0f, 0f);
				break;
			default:
				room = new RoomNull();
				break;
		}
	}
	
	public void click()
	{
		if(Scene2DHelper.inst.dialogueOutputFinished() && room.waitForClick)
			room.gotClick();
		//Scene2DHelper.inst.setDialogueText("");
	}
	
	public boolean currentRoomCompleted()
	{
		return room.roomCompleted;
	}
	
	public void update()
	{
		room.update();
		
		/*if(room.waitForClick)
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
				click();*/
	}
}
