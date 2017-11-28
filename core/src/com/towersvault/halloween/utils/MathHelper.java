package com.towersvault.halloween.utils;

import com.badlogic.gdx.Gdx;

public class MathHelper
{
	public static final MathHelper inst = new MathHelper();
	
	public float pxToScreen(float px)
	{
		return px * Constants.resize();
	}
	
	public float pxToScreen(float px, boolean fromTop)
	{
		return Gdx.graphics.getHeight() - pxToScreen(px);
	}
	
	public float translate(float coord)
	{
		return coord * (8f * Constants.resize());
	}
	
	/*public int posToTileX(float x)
	{
		return (int)(x + (NewChunkController.CHUNK_WIDTH / 2f));
	}*/
	
	public int posToTileY(float y)
	{
		return (int)((y - 1f) * -1 % 3f);
	}
	
	public int posToChunkId(float y)
	{
		return (int)((y - 1f) * -1 / 3f);
	}
}
