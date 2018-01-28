package com.towersvault.halloween.world;

import com.badlogic.gdx.utils.IntArray;

public class TileData
{
	private IntArray hashes;
	public int lightLevel;
	public int x;
	public int y;
	public int z;
	
	public TileData(int lightLevel, int x, int y, int z)
	{
		hashes = new IntArray();
		this.lightLevel = lightLevel;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void addHash(int hash)
	{
		hashes.add(hash);
	}
	
	public void removeHash(int hash)
	{
		hashes.removeValue(hash);
	}
	
	public IntArray getHashes()
	{
		return hashes;
	}
}
