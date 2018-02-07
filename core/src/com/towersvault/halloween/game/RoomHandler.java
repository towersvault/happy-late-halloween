package com.towersvault.halloween.game;

import com.towersvault.halloween.game.rooms.Room1Joke;

public class RoomHandler
{
	public static final RoomHandler inst = new RoomHandler();
	
	public AbstractRoom room;
	
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
		switch(eRoom)
		{
			case ROOM_1_JOKE:
				room = new Room1Joke(0f, 0f, 0f);
				break;
		}
	}
	
	public void click()
	{
		room.gotClick();
	}
}
