package com.towersvault.halloween.game;

public class RoomActor
{
	public String actorName;
	public int decalHashcode;
	public float spawnX, spawnY, spawnZ;
	private float newX, newY, newZ;
	
	public RoomActor(String actorName, int decalHashcode, float spawnX, float spawnY, float spawnZ)
	{
		this.actorName = actorName;
		this.decalHashcode = decalHashcode;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.spawnZ = spawnZ;
		this.newX = spawnX;
		this.newY = spawnY;
		this.newZ = spawnZ;
	}
	
	public void moveTo(float newX, float newZ, float seconds)
	{
		moveTo(newX, getY(), newZ, seconds);
	}
	
	public void moveTo(float newX, float newY, float newZ, float seconds)
	{
		this.newX = newX;
		this.newY = newY;
		this.newZ = newZ;
	}
	
	public float getX()
	{
		return 0f;
	}
	
	public float getY()
	{
		return 0f;
	}
	
	public float getZ()
	{
		return 0f;
	}
	
	public boolean reachedMoveDestination()
	{
		return getX() == newX
				&& getY() == newY
				&& getZ() == newZ;
	}
	
	public void update()
	{
		// Add code here for tweening and shit.
	}
}
