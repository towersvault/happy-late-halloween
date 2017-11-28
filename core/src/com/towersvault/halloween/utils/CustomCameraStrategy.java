package com.towersvault.halloween.utils;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;

public class CustomCameraStrategy extends CameraGroupStrategy
{
	private static final int GROUP_OPAQUE = 0;
	private static final int GROUP_BLEND = 1;

	Pool<Array<Decal>> arrayPool = new Pool<Array<Decal>>(16) {
		@Override
		protected Array<Decal> newObject () {
			return new Array();
		}
	};
	Array<Array<Decal>> usedArrays = new Array<Array<Decal>>();
	ObjectMap<DecalMaterial, Array<Decal>> materialGroups = new ObjectMap<DecalMaterial, Array<Decal>>();

	Camera camera;
	ShaderProgram shader;
	private final Comparator<Decal> cameraSorter;

	public CustomCameraStrategy(final Camera camera) {
		this(camera, new Comparator<Decal>() {
			@Override
			public int compare (Decal o1, Decal o2) {
				float dist1 = camera.position.dst(o1.getPosition());
				float dist2 = camera.position.dst(o2.getPosition());
				return (int)Math.signum(dist2 - dist1);
			}
		});
	}
	
	public CustomCameraStrategy(Camera camera, Comparator<Decal> sorter)
	{
		super(camera, sorter);
		this.cameraSorter = sorter;
	}
	
	@Override
	public void beforeGroup(int group, Array<Decal> contents)
	{
		if(group == GROUP_BLEND)
		{
			//Gdx.gl.glDepthMask(false);
			//Gdx.gl.glEnable(GL20.GL_BLEND);
			//Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
			contents.sort(cameraSorter);
			//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
			//Gdx.gl.glDepthMask(true);
			//Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		}
		else {
			//Gdx.gl.glDepthMask(true);
			
			for (int i = 0, n = contents.size; i < n; i++) {
				Decal decal = contents.get(i);
				Array<Decal> materialGroup = materialGroups.get(decal.getMaterial());
				if (materialGroup == null) {
					materialGroup = arrayPool.obtain();
					materialGroup.clear();
					usedArrays.add(materialGroup);
					materialGroups.put(decal.getMaterial(), materialGroup);
				}
				materialGroup.add(decal);
			}

			contents.clear();
			for (Array<Decal> materialGroup : materialGroups.values()) {
				contents.addAll(materialGroup);
			}

			materialGroups.clear();
			arrayPool.freeAll(usedArrays);
			usedArrays.clear();
		}
	}
}
