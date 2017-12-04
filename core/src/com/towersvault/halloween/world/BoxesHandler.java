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
import com.towersvault.halloween.utils.Assets;
import com.towersvault.halloween.world.decals.AbstractDecal;
import com.towersvault.halloween.world.decals.PlantDecal;
import com.towersvault.halloween.world.decals.StopDecal;

public class BoxesHandler implements Disposable
{
	public static final BoxesHandler inst = new BoxesHandler();

	private DecalBatch batch;
	private Array<Decal> decals = new Array<Decal>();
	//private Array<PlantDecal> plantDecals = new Array<PlantDecal>();
	private Array<AbstractDecal> alphaDecals = new Array<AbstractDecal>();
	
	public static final float TILE_SIZE = 20f;
	
	private Decal dMoon;
	
	/*private class PlantDecal
	{
		public Array<Decal> decals = new Array<Decal>();
		private int order = 0;
		
		public PlantDecal(Decal decal1, Decal decal2)
		{
			decals.add(decal1);
			decals.add(decal2);
		}
		
		*//*
		 * Used for alphaDecals.
		 * 
		 * 	0		1
		 * 
		 * 		S	
		 * 
		 * 	1		0
		 *//*
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
	}*/
	
	public enum BoxType
	{
		WALL,
		CEILING,
		BUSH,
		FLOOR,
		SIDEWALK,
		ROAD,
		GRASS,
		GRASS_SIDE,
		ROOF,
		FIRE_HYDRANT,
		STOP_N,
		STOP_S,
		STOP_E,
		STOP_W,
		FENCE_TL,
		FENCE_T,
		FENCE_TR,
		FENCE_CL,
		FENCE_CR,
		FENCE_BL,
		FENCE_B,
		FENCE_BR
	}
	
	public void init(DecalBatch batch)
	{
		this.batch = batch;
		decals.clear();
		//plantDecals.clear();
		alphaDecals.clear();
		
		dMoon = Decal.newDecal(144f, 80f, Assets.inst.staticSprite.moon);
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
			
			if(addY == 0)
			{
				WorldHandler.inst.createWallBody((float)x, (float)z);
			}
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
			Decal dTop = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dRight = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dBottom = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dLeft = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			
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
			Decal dBush = Decal.newDecal(TILE_SIZE, 12f, Assets.inst.staticSprite.bush, true);
			dBush.setPosition(x * TILE_SIZE, 12f / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush.rotateY(45f);
			dBush.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dBush2 = Decal.newDecal(TILE_SIZE, 12f, Assets.inst.staticSprite.bush, true);
			dBush2.setPosition(x * TILE_SIZE, 12f / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush2.rotateY(45f + 90f);
			dBush2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			//plantDecals.add(new PlantDecal(dBush, dBush2));
			alphaDecals.add(new PlantDecal(dBush, dBush2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.FIRE_HYDRANT))
		{
			Decal dFireHydrant1 = Decal.newDecal(8f, 11f, Assets.inst.staticSprite.fireHydrant, true);
			dFireHydrant1.setPosition(x * TILE_SIZE, 11f / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFireHydrant1.rotateY(45f);
			dFireHydrant1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dFireHydrant2 = Decal.newDecal(8f, 11f, Assets.inst.staticSprite.fireHydrant, true);
			dFireHydrant2.setPosition(dFireHydrant1.getX(), dFireHydrant1.getY(), dFireHydrant1.getZ());
			dFireHydrant2.rotateY(45f + 90f);
			dFireHydrant2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			//plantDecals.add(new PlantDecal(dFireHydrant1, dFireHydrant2));
			alphaDecals.add(new PlantDecal(dFireHydrant1, dFireHydrant2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.GRASS))
		{
			Decal dGrassBlades1 = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.grassBlades, true);
			dGrassBlades1.setPosition(x * TILE_SIZE, dGrassBlades1.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dGrassBlades1.rotateY(45f);
			dGrassBlades1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dGrassBlades2 = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.grassBlades, true);
			dGrassBlades2.setPosition(dGrassBlades1.getX(), dGrassBlades1.getY(), dGrassBlades1.getZ());
			dGrassBlades2.rotateY(45f + 90f);
			dGrassBlades2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			//plantDecals.add(new PlantDecal(dGrassBlades1, dGrassBlades2));
			alphaDecals.add(new PlantDecal(dGrassBlades1, dGrassBlades2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
		}
		else if((boxType.equals(BoxType.STOP_N))
				|| (boxType.equals(BoxType.STOP_S))
				|| (boxType.equals(BoxType.STOP_E))
				|| (boxType.equals(BoxType.STOP_W)))
		{
			Decal dStopFront = Decal.newDecal(15f, 33f, Assets.inst.staticSprite.stopFront, true);
			dStopFront.setPosition(x * TILE_SIZE, dStopFront.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dStopFront.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dStopBack = Decal.newDecal(15f, 33f, Assets.inst.staticSprite.stopBack, true);
			dStopBack.setPosition(x * TILE_SIZE, dStopFront.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dStopBack.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			Decal dBoxBack = Decal.newDecal(3f, 30f, Assets.inst.staticSprite.stopBoxBack);
			Decal dBoxSide1 = Decal.newDecal(1f, 30f, Assets.inst.staticSprite.stopBoxSide);
			Decal dBoxSide2 = Decal.newDecal(1f, 30f, Assets.inst.staticSprite.stopBoxSide);
			
			switch(boxType)
			{
				case STOP_N:
					dStopFront.rotateY(180f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "N"));
					
					dBoxSide1.rotateY(90f);
					dBoxSide2.rotateY(90f);
					
					dBoxBack.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() + 1.05f);
					dBoxSide1.setPosition(dStopFront.getX() - 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() + 0.55f);
					dBoxSide2.setPosition(dStopFront.getX() + 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 0.55f);
					break;
				case STOP_S:
					dStopBack.rotateY(180f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "S"));
					
					dBoxSide1.rotateY(90f);
					dBoxSide2.rotateY(90f);
					
					dBoxBack.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() - 1.05f);
					dBoxSide1.setPosition(dStopFront.getX() - 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 0.55f);
					dBoxSide2.setPosition(dStopFront.getX() + 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() - 0.55f);
					break;
				case STOP_E:
					dStopFront.rotateY(90f);
					dStopBack.rotateY(270f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "E"));
					
					dBoxBack.rotateY(90f);
					
					dBoxBack.setPosition(dStopFront.getX() - 1.05f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxSide1.setPosition(dStopFront.getX() - 0.55f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() - 0.55f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					break;
				case STOP_W:
					dStopFront.rotateY(270f);
					dStopBack.rotateY(90f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "W"));
					
					dBoxBack.rotateY(90f);
					
					dBoxBack.setPosition(dStopFront.getX() + 1.05f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxSide1.setPosition(dStopFront.getX() + 0.55f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() + 0.55f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					break;
				default:
					break;
			}
			
			decals.add(dBoxBack);
			decals.add(dBoxSide1);
			decals.add(dBoxSide2);
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
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
			
			Decal dTop = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dRight = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dBottom = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			Decal dLeft = Decal.newDecal(TILE_SIZE, 1f, Assets.inst.staticSprite.pavementSide);
			
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
		else if(boxType.equals(BoxType.ROOF))
		{
			Decal dRoof = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dRoof.setPosition(x * TILE_SIZE, TILE_SIZE * 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dRoof.rotateX(90f);
			//dRoof.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			decals.add(dRoof);
		}
		else if((boxType.equals(BoxType.FENCE_TL))
				|| (boxType.equals(BoxType.FENCE_T))
				|| (boxType.equals(BoxType.FENCE_TR))
				|| (boxType.equals(BoxType.FENCE_CL))
				|| (boxType.equals(BoxType.FENCE_CR))
				|| (boxType.equals(BoxType.FENCE_BL))
				|| (boxType.equals(BoxType.FENCE_B))
				|| (boxType.equals(BoxType.FENCE_BR)))
		{
			// TODO: Add fenceTile in Assets, and add Decal code below.
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
		
		dMoon.setPosition(WorldHandler.inst.getBodyX() + 120f, 80f, WorldHandler.inst.getBodyY() - 120f);
		dMoon.lookAt(camPosition, cam.up);
		
		/*for(int i = 0; i < plantDecals.size; i++)
		{
			plantDecals.get(i).updateRenderOrder();
			
			*//*PLANT_ROTATION = (float)(Math.atan2(
					plantDecals.get(i).decals.get(0).getX() - camPosition.x,
					plantDecals.get(i).decals.get(0).getZ() - camPosition.z
				) * 180.0d / Math.PI);
			plantDecals.get(i).decals.get(0).setRotationY(PLANT_ROTATION);*//*
			
			//plantDecals.get(i).decals.get(0).lookAt(camPosition, cam.up);
			
			batch.add(plantDecals.get(i).decals.get(0));
			batch.add(plantDecals.get(i).decals.get(1));
		}
		batch.flush();*/
		
		for(int i = 0; i < alphaDecals.size; i++)
		{
			alphaDecals.get(i).updateRenderOrder();
			
			if(!alphaDecals.get(i).renderOnlyOneSide)
			{
				batch.add(alphaDecals.get(i).decals.get(0));
				batch.add(alphaDecals.get(i).decals.get(1));
			}
			else
			{
				batch.add(alphaDecals.get(i).decals.get(0));
			}
		}
		batch.flush();
	}
	
	@Override
	public void dispose()
	{
		
	}
}
