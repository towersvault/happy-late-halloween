package com.towersvault.halloween.utils;

import com.badlogic.gdx.Gdx;

public class AspectRatioHelper
{
	public final static AspectRatioHelper inst = new AspectRatioHelper();
	
	private int resize;
	
	//return (float)((int)(Gdx.graphics.getHeight() / 180f));
	
	public float baseRatio;
	
	private int ratioWidth;
	private int ratioHeight;
	
	public static final float GAME_SCREEN_HEIGHT = 140f;
	
	public void findAspectRatio()
	{
		findRatios:
			for(int i = 1; i > -1; i++)
			{
				ratioHeight = i;
				ratioWidth = (int)(i * baseRatio);
				
				if(Gdx.graphics.getHeight() / ratioHeight == Gdx.graphics.getWidth() / ratioWidth)
				{
					break findRatios;
				}
			}
		
		if(!Constants.RELEASE_MODE)
			System.out.println("Aspect Ratio=" + ratioWidth + ":" + ratioHeight);
	
		resize = ((int)(Gdx.graphics.getHeight() / GAME_SCREEN_HEIGHT));
		
		if(resize % 2 == 1)
			resize += 1;
	}
	
	public int getResize()
	{
		return resize;
	}
	
	public int getRatioWidth()
	{
		return ratioWidth;
	}
	
	public int getRatioHeight()
	{
		return ratioHeight;
	}
}
