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
		END
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
		room.gotClick();
		//Scene2DHelper.inst.setDialogueText("");
	}
	
	public void update()
	{
		room.update();
		
		if(room.waitForClick)
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
				click();
	}
}
