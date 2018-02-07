package com.towersvault.halloween.game.rooms;

import com.badlogic.gdx.Gdx;
import com.towersvault.halloween.game.AbstractRoom;
import com.towersvault.halloween.game.RoomAction;
import com.towersvault.halloween.game.RoomHandler;

public class Room1Joke extends AbstractRoom
{
	private float delta = 0f;
	
	public Room1Joke(float xTrigger, float yTrigger, float triggerRadius)
	{
		super(xTrigger, yTrigger, triggerRadius);
		
		this.addAction(RoomHandler.RoomActionState.TALK, "Hello world.")
				.addAction(RoomHandler.RoomActionState.PAUSE, "3")
				.addAction(RoomHandler.RoomActionState.TALK, "This is all a test.");
	}
	
	@Override
	public AbstractRoom addAction(RoomHandler.RoomActionState actionState, String interactionData)
	{
		switch(actionState)
		{
			case TALK:
				super.roomActions.add(new RoomAction(actionState, interactionData));
				break;
			case PAUSE:
				super.roomActions.add(new RoomAction(actionState, Integer.parseInt(interactionData)));
				break;
			case END:
				super.roomActions.add(new RoomAction(actionState, "END"));
				break;
			default:
				break;
		}
		
		return this;
	}
	
	@Override
	public void update()
	{
		if(waitForClick)
		{
			delta += Gdx.graphics.getDeltaTime();
			
			if(delta >= 1f)
			{
				delta = 0f;
				
				switch(super.roomActions.get(0).actionState)
				{
					case PAUSE:
						super.roomActions.get(0).pause -= 1;
						if (super.roomActions.get(0).pause <= 0)
							removeCurrentAction = true;
						break;
					case END:
						roomCompleted = true;
						break;
				}
			}
			
			if(removeCurrentAction)
			{
				super.roomActions.removeIndex(0);
				removeCurrentAction = false;
			}
		}
	}
}
