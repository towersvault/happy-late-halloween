package com.towersvault.halloween.utils.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.towersvault.halloween.render.Renderer;
import com.towersvault.halloween.world.WorldHandler;

public class DirectionalSound
{
	private Sound sound;
	private Vector2 position;
	private Vector2 tmp = new Vector2();
	private long id;
	
	public DirectionalSound(Sound sound, Vector3 position)
	{
		this.sound = sound;
		this.position = new Vector2(position.x, position.z);
	}
	
	public DirectionalSound(Sound sound, float x, float y, float z)
	{
		this.sound = sound;
		this.position = new Vector2(x, z);
	}
	
	public void play()
	{
		id = sound.play();
	}
	
	public void update()
	{
		/*Vector2 soundVec = new Vector2(position.x, position.y);
		float direction = soundVec.sub(WorldHandler.inst.getBodyPosition()).angle();
		//float direction = Renderer.inst.getCameraAngle().angle() - Renderer.inst.getCameraAngle().sub(soundVec).angle();
		//float direction = soundVec.sub(Renderer.inst.getCameraVector()).nor().angle();
		
		System.out.println(direction - Renderer.inst.getCameraRotation());*/
		
		Vector2 listenerPos = WorldHandler.inst.getBodyPosition();
		Vector2 listenerDir = Renderer.inst.getListener();
		
		tmp.set(position);
		tmp.sub(listenerPos);
		tmp.nor();
		
		float angle = Math.abs(listenerDir.angleRad() - tmp.angleRad());
		boolean isRight = tmp.crs(listenerDir) > 0.0f;
		float pan = 0.0f;
		
		if (angle > MathUtils.PI * 0.5f) {
			angle -= MathUtils.PI * 0.5f;
			pan = Interpolation.linear.apply(isRight ? 1.0f : -1.0f, 0.0f, angle / (MathUtils.PI * 0.5f));
		}
		else {
			pan = Interpolation.linear.apply(0.0f, isRight ? 1.0f : -1.0f, angle / (MathUtils.PI * 0.5f));
		}
		
		// Calculate volume
		float distance = position.dst(listenerPos);
		float falloffStart = 1; //data.getFalloffStart();
		float volume = MathUtils.clamp(1.0f - (distance - falloffStart) / (1000 /*data.getMaxDistance()*/ - falloffStart),
				0.0f,
				1.0f);
		
		// Apply results
		sound.setPan(id, pan * 100f, volume);
		
		System.out.println(pan);
	}
}
