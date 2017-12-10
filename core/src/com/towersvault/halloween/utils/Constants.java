package com.towersvault.halloween.utils;

import java.util.Calendar;

public class Constants
{
	public static final float R = 23f / 255f;
	public static final float G = 17f / 255f;
	public static final float B = 27f / 255f;
	
	public static final String SPRITES_JSON = "sprites/sprites.json";
	public static final String SPRITES_ATLAS = "sprites/sprites.atlas";
	
	public static final String DEV_NAME = "towersvaultgames";
	public static final String VERSION = "0.1.0";
	public static final String GAME_NAME = "Happy Late Halloween";
	
	public static final boolean RELEASE_MODE = false;
	
	private static Calendar cal = Calendar.getInstance();
	
	public static float resize()
	{
		return AspectRatioHelper.inst.getResize();
	}
	
	public static String getDate()
	{
		cal.setTimeInMillis(System.currentTimeMillis());
		
		return cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH);
	}
	
	// GAME SETTING VARIABLES
	public static boolean DYNAMIC_PHYSICS = true; // Handles physics boxes tracking based on player location.
}
