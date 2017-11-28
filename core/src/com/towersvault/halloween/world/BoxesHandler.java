package com.towersvault.halloween.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;

public class BoxesHandler implements Disposable
{
	public static final BoxesHandler inst = new BoxesHandler();

	private DecalBatch batch;
	private Array<Decal> decals = new Array<Decal>();
	private Array<PlantDecal> plantDecals = new Array<PlantDecal>();
	
	public static final float TILE_SIZE = 20f;
	
	private TextureRegion tSidewalk;
	private TextureRegion tGrass;
	private TextureRegion tGrassBlades;
	
	private Decal dMoon;
	
	private float PLANT_ROTATION = 0f;
	
	private class PlantDecal
	{
		public Array<Decal> decals = new Array<Decal>();
		private int order = 0;
		
		public PlantDecal(Decal decal1, Decal decal2)
		{
			decals.add(decal1);
			decals.add(decal2);
		}
		
		/*
		 * Used for alphaDecals.
		 * 
		 * 	0		1
		 * 
		 * 		S	
		 * 
		 * 	1		0
		 */
		public void updateRenderOrder()
		{
			if((decals.get(0).getPosition().x < WorldHandler.inst.getBodyX()
					&& decals.get(0).getPosition().z < WorldHandler.inst.getBodyY())
					|| (decals.get(0).getPosition().x > WorldHandler.inst.getBodyX()
					&& decals.get(0).getPosition().z > WorldHandler.inst.getBodyY()))
			{
				if(order == 1)
				{
					decals.reverse();
					order = 0;
				}
			}
			else if((decals.get(0).getPosition().x > WorldHandler.inst.getBodyX()
					&& decals.get(0).getPosition().z < WorldHandler.inst.getBodyY())
					|| (decals.get(0).getPosition().x < WorldHandler.inst.getBodyX()
					&& decals.get(0).getPosition().z > WorldHandler.inst.getBodyY()))
			{
				if(order == 0)
				{
					decals.reverse();
					order = 1;
				}
			}
		}
	}
	
	public enum BoxType
	{
		WALL,
		CEILING,
		BUSH,
		FLOOR,
		SIDEWALK,
		ROAD,
		GRASS,
		GRASS_SIDE;
	}
	
	public void init(DecalBatch batch)
	{
		this.batch = batch;
		decals.clear();
		plantDecals.clear();
		
		tSidewalk = new TextureRegion(new Texture(Gdx.files.internal("side_walk.png")));
		tGrass = new TextureRegion(new Texture(Gdx.files.internal("grass.png")));
		tGrassBlades = new TextureRegion(new Texture(Gdx.files.internal("grass_blades.png")));
		TextureRegion tMoon = new TextureRegion(new Texture(Gdx.files.internal("moon-2.png")));
		
		dMoon = Decal.newDecal(144f, 80f, tMoon);
		dMoon.setPosition(40f, 60f, 40f);
		
		decals.add(dMoon);
	}
	
	public void loadBox(TextureRegion tex, BoxType boxType, boolean physics, int x, int z, float addY)
	{
		if(boxType.equals(BoxType.WALL))
		{
			/*System.out.println("Loaded box.");*/
			
			Decal dTop = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			Decal dRight = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			Decal dBottom = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			Decal dLeft = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			
			dTop.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY, TILE_SIZE * z);
			dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, TILE_SIZE / 2f + addY, TILE_SIZE * z - TILE_SIZE / 2f);
			dRight.rotateY(90f);
			dBottom.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY, TILE_SIZE * z - TILE_SIZE);
			dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, TILE_SIZE / 2f + addY, TILE_SIZE * z - TILE_SIZE / 2f);
			dLeft.rotateY(90f);
			
			decals.add(dTop);
			decals.add(dRight);
			decals.add(dBottom);
			decals.add(dLeft);
		}
		else if(boxType.equals(BoxType.FLOOR))
		{
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.GRASS_SIDE))
		{
			Decal dTop = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dRight = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dBottom = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dLeft = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			
			dTop.setPosition(TILE_SIZE * x, 
					-1f / 2f, 
					TILE_SIZE * z);
			dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE / 2f);
			dRight.rotateY(90f);
			dBottom.setPosition(TILE_SIZE * x, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE);
			dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE / 2f);
			dLeft.rotateY(90f);
			
			decals.add(dTop);
			decals.add(dRight);
			decals.add(dBottom);
			decals.add(dLeft);
		}
		else if(boxType.equals(BoxType.CEILING))
		{
			Decal dCeiling = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dCeiling.setPosition(x * TILE_SIZE, TILE_SIZE, z * TILE_SIZE - TILE_SIZE / 2f);
			dCeiling.rotateX(90f);
			
			decals.add(dCeiling);
		}
		else if(boxType.equals(BoxType.BUSH))
		{
			Decal dBush = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex, true);
			dBush.setPosition(x * TILE_SIZE, TILE_SIZE / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush.rotateY(45f);
			dBush.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dBush2 = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex, true);
			dBush2.setPosition(x * TILE_SIZE, TILE_SIZE / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush2.rotateY(45f + 90f);
			dBush2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			//alphaDecals.add(dBush);
			//alphaDecals.add(dBush2);
			
			plantDecals.add(new PlantDecal(dBush, dBush2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, tGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.GRASS))
		{
			Decal dBush = Decal.newDecal(TILE_SIZE, 1f, tGrassBlades, true);
			dBush.setPosition(x * TILE_SIZE, 0.5f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush.rotateY(45f);
			dBush.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dBush2 = Decal.newDecal(TILE_SIZE, 1f, tGrassBlades, true);
			dBush2.setPosition(x * TILE_SIZE, 0.5f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush2.rotateY(45f + 90f);
			dBush2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			//alphaDecals.add(dBush);
			//alphaDecals.add(dBush2);
			
			plantDecals.add(new PlantDecal(dBush, dBush2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, tGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.SIDEWALK))
		{
			Decal dSideWalk = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dSideWalk.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dSideWalk.rotateX(90f);
			
			decals.add(dSideWalk);
			
			Decal dTop = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dRight = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dBottom = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			Decal dLeft = Decal.newDecal(TILE_SIZE, 1f, tSidewalk);
			
			dTop.setPosition(TILE_SIZE * x, 
					dSideWalk.getY() - 1f / 2f, 
					TILE_SIZE * z);
			dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE / 2f);
			dRight.rotateY(90f);
			dBottom.setPosition(TILE_SIZE * x, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE);
			dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, 
					dTop.getY(), 
					TILE_SIZE * z - TILE_SIZE / 2f);
			dLeft.rotateY(90f);
			
			decals.add(dTop);
			decals.add(dRight);
			decals.add(dBottom);
			decals.add(dLeft);
		}
		else if(boxType.equals(BoxType.ROAD))
		{
			Decal dRoad = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dRoad.setPosition(x * TILE_SIZE, -1f, z * TILE_SIZE - TILE_SIZE / 2f);
			dRoad.rotateX(90f);
			
			decals.add(dRoad);
		}
	}
	
	public void optimizeBoxes()
	{
		Array<Decal> newDecals = new Array<Decal>();
		newDecals.addAll(decals);
		
		IntArray indexCounter = new IntArray();
		
		System.out.println("Optimizing decals.");
		
		for(int i = 0; i < decals.size; i++)
		{
			indexCounter.clear();
			
			for(int j = newDecals.size; --j >= 0;)
			{
				if(decals.get(i).getX() == newDecals.get(j).getX()
						&& decals.get(i).getY() == newDecals.get(j).getY()
						&& decals.get(i).getZ() == newDecals.get(j).getZ())
				{
					//newDecals.removeIndex(j);
					
					indexCounter.add(j);
				}
			}
			
			for(int k = 0; k < indexCounter.size - 1; k++)
			{
				newDecals.removeIndex(indexCounter.get(k));
			}
		}
		
		decals = newDecals;
	}
	
	public void render(Vector3 camPosition, PerspectiveCamera cam)
	{
		for(int i = 0; i < decals.size; i++)
		{
			batch.add(decals.get(i));
		}
		batch.flush();
		
		/*if(determineQuadrant() == 1)
		{
			for(int i = alphaDecals.size - 1; i >= 0; i--)
			{
				batch.add(alphaDecals.get(i));
			}
			batch.flush();
		}
		else
		{
			for(int i = 0; i < alphaDecals.size; i++)
			{
				batch.add(alphaDecals.get(i));
			}
			batch.flush();
		}*/
		
		/*for(int i = 0; i < plantDecals.size; i++)
		{
			plantDecals.get(i).updateRenderOrder();
			
			PLANT_ROTATION = (float)(Math.atan2(
					plantDecals.get(i).decals.get(0).getX() - camPosition.x,
					plantDecals.get(i).decals.get(0).getZ() - camPosition.z
				) * 180.0d / Math.PI);
			plantDecals.get(i).decals.get(0).setRotationY(PLANT_ROTATION);
			
			//plantDecals.get(i).decals.get(0).lookAt(camPosition, cam.up);
			
			batch.add(plantDecals.get(i).decals.get(0));
			//batch.add(plantDecals.get(i).decals.get(1));
		}
		batch.flush();*/
		
		dMoon.setPosition(WorldHandler.inst.getBodyX() + 120f, 80f, WorldHandler.inst.getBodyY() - 120f);
		dMoon.lookAt(camPosition, cam.up);
		
		/*dSky1.setPosition(WorldHandler.inst.getBodyX() - dSky1.getWidth() / 2f, 8f, WorldHandler.inst.getBodyY() - 120f);
		dSky1.lookAt(camPosition, cam.up);*/
		
		for(int i = 0; i < plantDecals.size; i++)
		{
			plantDecals.get(i).updateRenderOrder();
			
			/*PLANT_ROTATION = (float)(Math.atan2(
					plantDecals.get(i).decals.get(0).getX() - camPosition.x,
					plantDecals.get(i).decals.get(0).getZ() - camPosition.z
				) * 180.0d / Math.PI);
			plantDecals.get(i).decals.get(0).setRotationY(PLANT_ROTATION);*/
			
			//plantDecals.get(i).decals.get(0).lookAt(camPosition, cam.up);
			
			batch.add(plantDecals.get(i).decals.get(0));
			batch.add(plantDecals.get(i).decals.get(1));
		}
		batch.flush();
	}
	
	@Override
	public void dispose()
	{
		
	}
}
