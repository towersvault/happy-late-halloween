package com.towersvault.halloween.world;

import com.badlogic.gdx.utils.Array;

public class LightRenderer
{
	public static final LightRenderer inst = new LightRenderer();
	
	private Array<TileData> tileData = new Array<TileData>();
	
	public void setTileData(Array<TileData> tileData)
	{
		this.tileData.clear();
		this.tileData = tileData;
	}
	
	public boolean hasLight(int x, int z)
	{
		for(int i = 0; i < tileData.size; i++)
		{
			if(tileData.get(i).x == x
					&& tileData.get(i).z == z)
				return true;
		}
		return false;
	}
	
	public void addHash(int x, int z, int hash)
	{
		for(int i = 0; i < tileData.size; i++)
		{
			if(tileData.get(i).x == x
					&& tileData.get(i).z == z)
			{
				tileData.get(i).addHash(hash);
			}
		}
	}
	
	public void update()
	{
		/*for(int i = 0; i < tileData.size; i++)
		{
			if(tileData.get(i).lightLevel == 1)
			{
				BoxesHandler.inst.setDecalColor(tileData.get(i).x, tileData.get(i).z - 1, 1f, 1f, 1f);
				BoxesHandler.inst.setDecalColor(tileData.get(i).x - 1, tileData.get(i).z - 1, 1f, 1f, 1f);
			}
			else if(tileData.get(i).lightLevel == 2)
			{
				BoxesHandler.inst.setDecalColor(tileData.get(i).x, tileData.get(i).z - 1, 98f / 255f, 119f / 255f, 130f / 255f);
				BoxesHandler.inst.setDecalColor(tileData.get(i).x - 1, tileData.get(i).z - 1, 98f / 255f, 119f / 255f, 130f / 255f);
			}
		}*/
		
		for(int i = 0; i < tileData.size; i++)
		{
			if(tileData.get(i).lightLevel == 1)
				BoxesHandler.inst.setDecalColor(tileData.get(i).getHashes(), 1f, 1f, 1f);
			else if(tileData.get(i).lightLevel == 2)
				BoxesHandler.inst.setDecalColor(tileData.get(i).getHashes(), 166f / 255f, 187f / 255f, 196f / 255f);
			else if(tileData.get(i).lightLevel == 3)
				BoxesHandler.inst.setDecalColor(tileData.get(i).getHashes(), 98f / 255f, 119f / 255f, 130f / 255f);
			
			if(tileData.get(i).lightLevel == 1)
				BoxesHandler.inst.setAlphaDecalColor(tileData.get(i).getHashes(), 1f, 1f, 1f);
			else if(tileData.get(i).lightLevel == 2)
				BoxesHandler.inst.setAlphaDecalColor(tileData.get(i).getHashes(), 166f / 255f, 187f / 255f, 196f / 255f);
			else if(tileData.get(i).lightLevel == 3)
				BoxesHandler.inst.setAlphaDecalColor(tileData.get(i).getHashes(), 98f / 255f, 119f / 255f, 130f / 255f);
		}
	}
}
