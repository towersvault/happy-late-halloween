package com.towersvault.halloween.utils.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class AudioController
{
	public static final AudioController inst = new AudioController();
	
	private Array<DirectionalSound> sounds = new Array<DirectionalSound>();
	
	public void init()
	{
		sounds.clear();
		
		newSound(Gdx.audio.newSound(Gdx.files.internal("audio_test.mp3")), 0f, 0f, 0f);
	}
	
	public void newSound(Sound sound, float x, float y, float z)
	{
		sounds.add(new DirectionalSound(sound, x, y, z));
		sounds.get(sounds.size - 1).play();
	}
	
	public void update()
	{
		// TODO: Add code to remove sounds when they're done playing.
		for(int i = 0; i < sounds.size; i++)
		{
			sounds.get(i).update();
		}
	}
}
