package com.towersvault.halloween.game.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.towersvault.halloween.game.AbstractRoom;
import com.towersvault.halloween.game.RoomAction;
import com.towersvault.halloween.game.RoomHandler;
import com.towersvault.halloween.screens.Scene2DHelper;
import com.towersvault.halloween.world.MapLoader;
import com.towersvault.halloween.world.WorldHandler;

public class Room1Joke extends AbstractRoom
{
	private float delta = 0f;
	
	public Room1Joke(float xTrigger, float yTrigger, float triggerRadius)
	{
		super(xTrigger, yTrigger, triggerRadius);
		
		this.addAction(RoomHandler.RoomActionState.PAUSE, "1")
				.addAction(RoomHandler.RoomActionState.TALK, "Hello world.")
				.addAction(RoomHandler.RoomActionState.TALK, "This is all a test.")
				.addAction(RoomHandler.RoomActionState.PAUSE, "1");
		
		System.out.println(MapLoader.inst.getMapHeight() - 82);
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
		if(WorldHandler.inst.getBodyTileX() == 34
				&& WorldHandler.inst.getBodyTileZ() == MapLoader.inst.getMapHeight() - 99
				&& waitingForTrigger)
		{
			waitingForTrigger = false;
			
			Scene2DHelper.inst.toggleDialogue(true);
		}
		
		if(!waitingForTrigger && !roomCompleted)
		{
			if(!waitForClick)
			{
				delta += Gdx.graphics.getDeltaTime();
				
				if(delta >= 1f)
				{
					delta = 0f;
					
					switch(super.roomActions.get(0).actionState)
					{
						case PAUSE:
							System.out.println("Pause");
							super.roomActions.get(0).pause -= 1;
							if (super.roomActions.get(0).pause <= 0)
								removeCurrentAction = true;
							break;
						case TALK:
							System.out.println(super.roomActions.get(0).talkOutput);
							waitForClick = true;
							removeCurrentAction = true;
							Scene2DHelper.inst.setDialogueText(super.roomActions.get(0).talkOutput);
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
					
					if(super.roomActions.size == 0)
					{
						System.out.println("Room completed.");
						roomCompleted = true;
						
						Scene2DHelper.inst.toggleDialogue(false);
					}
				}
			}
		}
	}
	
	@Override
	public void clear()
	{
		super.roomActions.clear();
	}
}
