package com.towersvault.halloween.utils;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;

/*
 *     /\__/\
 *    /`    '\
 *  === 0  0 ===
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/#######o
 *   
 * Looking through code is fun, is it not?
 * 
 * Please don't leak any information from the code.
 * 
 * Thanks! :)
 */

public class ScreenshotHandler
{
	private static int counter = 1;
	
	// Thanks Niklas.
	
	public static void saveScreenshot()
	{
		try
		{
			FileHandle file;
			
			do
			{
				file = new FileHandle(Gdx.files.getLocalStoragePath() + "screenshot_" + Constants.getDate() + "(" + counter++ + ").png");
			} while(file.exists());
			
			Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
			PixmapIO.writePNG(file, pixmap);
			pixmap.dispose();
			
			//System.out.println("Took screenshot, saved file as screenshot_" + Constants.GAME_NAME + "_" + counter + ".png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown)
	{
		final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
		
		if(yDown)
		{
			// Flip the pixmap upside down.
			
			ByteBuffer pixels = pixmap.getPixels();
			
			int numBytes = w * h * 4;
			
			byte[] lines = new byte[numBytes];
			
			int numBytesPerLine = w * 4;
			
			for(int i = 0; i < h; i++)
			{
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			
			pixels.clear();
			
			pixels.put(lines);
		}
		
		return pixmap;
	}
}
