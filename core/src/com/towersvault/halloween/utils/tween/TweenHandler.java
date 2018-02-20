package com.towersvault.halloween.utils.tween;

import com.aurelienribon.tweenengine.Tween;
import com.aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TweenHandler
{
	public static final TweenHandler inst = new TweenHandler();
	
	public TweenManager manager;
	
	public void init()
	{
		manager = new TweenManager();
		
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		Tween.registerAccessor(Decal.class, new DecalAccessor());
	}
	
	public void update(float deltaTime)
	{
		manager.update(deltaTime);
	}
}
