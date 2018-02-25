package com.towersvault.halloween.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;
import com.towersvault.halloween.utils.Assets;
import com.towersvault.halloween.utils.CinemaController;
import com.towersvault.halloween.utils.MathHelper;
import com.towersvault.halloween.world.decals.*;
import com.towersvault.halloween.world.entities.AbstractEntity;
import com.towersvault.halloween.world.entities.EntityController;
import com.towersvault.halloween.world.entities.PlaneEntity;

public class BoxesHandler implements Disposable
{
	public static final BoxesHandler inst = new BoxesHandler();

	private DecalBatch batch;
	private Array<Decal> decals = new Array<Decal>();
	private Array<AbstractDecal> alphaDecals = new Array<AbstractDecal>();
	//private Array<TileData> tileData = new Array<TileData>();
	
	public static final float TILE_SIZE = 16f;
	
	private Decal dMoon;
	private Decal dDebugX, dDebugZ;
	
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
		ROOF_N,
		ROOF_S,
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
		FENCE_BR,
		VENDING_MACHINE,
		ITEM_BURGER,
		CORN,
		SCARECROW,
		SLIDING_DOOR_N,
		SLIDING_DOOR_E,
		SLIDING_DOOR_S,
		SLIDING_DOOR_W,
		SLIDING_DOOR,
		WATER,
		CRATE
	}
	
	public void init(DecalBatch batch)
	{
		this.batch = batch;
		decals.clear();
		alphaDecals.clear();
		
		dMoon = Decal.newDecal(144f, 80f, Assets.inst.staticSprite.moon);
		dMoon.setPosition(40f, 60f, 40f);

		dDebugX = Decal.newDecal(10f, 1f, Assets.inst.staticSprite.debugX);
		dDebugX.rotateX(90f);

		dDebugZ = Decal.newDecal(1f, 10f, Assets.inst.staticSprite.debugZ);
		dDebugZ.rotateX(90f);
		
		//decals.add(dMoon);
	}
	
	public void loadBox(TextureRegion tex, BoxType boxType, boolean physics, int x, int z, float addY)
	{
		if(boxType.equals(BoxType.WALL))
		{
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
		else if(boxType.equals(BoxType.CRATE))
		{
			// TODO: Fix crate.

			Decal dExterior1 = Decal.newDecal(14f, 14f, Assets.inst.staticSprite.crateExterior);
			Decal dExterior2 = Decal.newDecal(14f, 14f, Assets.inst.staticSprite.crateExterior);
			//Decal dLeftE = Decal.newDecal(14f, 14f, Assets.inst.staticSprite.crateExterior);
			//Decal dRightE = Decal.newDecal(14f, 14f, Assets.inst.staticSprite.crateExterior);

			Decal dInteriorFront = Decal.newDecal(10f, 10f, Assets.inst.staticSprite.crateInterior);
			Decal dInteriorBack = Decal.newDecal(10f, 10f, Assets.inst.staticSprite.crateInterior);
			Decal dInteriorLeft = Decal.newDecal(10f, 10f, Assets.inst.staticSprite.crateInterior);
			Decal dInteriorRight = Decal.newDecal(10f, 10f, Assets.inst.staticSprite.crateInterior);

			dExterior1.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - 1f);
			//dRightE.setPosition(TILE_SIZE * x + TILE_SIZE / 2f - 1f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
			//dRightE.rotateY(90f);
			dExterior2.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE + 1f);
			//dLeftE.setPosition(TILE_SIZE * x - TILE_SIZE / 2f + 1f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
			//dLeftE.rotateY(90f);

			dInteriorFront.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - 2f);
			dInteriorRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f - 2f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
			dInteriorRight.rotateY(90f);
			dInteriorBack.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE + 2f);
			dInteriorLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f + 2f, TILE_SIZE / 2f + addY - 1f, TILE_SIZE * z - TILE_SIZE / 2f);
			dInteriorLeft.rotateY(90f);

			//decals.add(dFrontE);
			//decals.add(dRightE);
			//decals.add(dBackE);
			//decals.add(dLeftE);

			decals.add(dInteriorFront);
			decals.add(dInteriorBack);
			decals.add(dInteriorLeft);
			decals.add(dInteriorRight);

			CrateDecal crate = new CrateDecal(dExterior1, dExterior2, x, z);
			alphaDecals.add(crate);
		}
		else if(boxType.equals(BoxType.WATER))
		{
			Decal dWater = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dWater.setPosition(x * TILE_SIZE, -TILE_SIZE, z * TILE_SIZE - TILE_SIZE / 2f);
			dWater.rotateX(90f);
			
			decals.add(dWater);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dWater.hashCode());
		}
		else if(boxType.equals(BoxType.FLOOR))
		{
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
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
			
			if(LightRenderer.inst.hasLight(x, z + 1))
				LightRenderer.inst.addHash(x, z + 1, dTop.hashCode());
			if(LightRenderer.inst.hasLight(x, z - 1))
				LightRenderer.inst.addHash(x, z - 1, dBottom.hashCode());
			if(LightRenderer.inst.hasLight(x + 1, z))
				LightRenderer.inst.addHash(x + 1, z, dRight.hashCode());
			if(LightRenderer.inst.hasLight(x - 1, z))
				LightRenderer.inst.addHash(x - 1, z, dLeft.hashCode());
			
			decals.add(dTop);
			decals.add(dRight);
			decals.add(dBottom);
			decals.add(dLeft);
		}
		else if(boxType.equals(BoxType.CEILING))
		{
			Decal dCeiling = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dCeiling.setPosition(x * TILE_SIZE, TILE_SIZE + TILE_SIZE / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dCeiling.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dCeiling.hashCode());
			
			decals.add(dCeiling);
		}
		else if(boxType.equals(BoxType.SLIDING_DOOR))
		{
			Decal dDoor1 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.slidingDoorLeft);
			dDoor1.setPosition(x * TILE_SIZE - TILE_SIZE / 2f + 2f, TILE_SIZE / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dDoor1.rotateY(90f);
			
			Decal dDoor2 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.slidingDoorRight);
			dDoor2.setPosition(x * TILE_SIZE - TILE_SIZE / 2f + 2f, TILE_SIZE / 2f, z * TILE_SIZE - TILE_SIZE / 2f - TILE_SIZE);
			dDoor2.rotateY(90f);
			
			alphaDecals.add(new SlidingDoorDecal(dDoor1, dDoor2));
			
			Decal dFloor1 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.carpet);
			dFloor1.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor1.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dFloor1.hashCode());
			
			decals.add(dFloor1);
			
			Decal dFloor2 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.carpet);
			dFloor2.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f - TILE_SIZE);
			dFloor2.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z - 1))
				LightRenderer.inst.addHash(x, z - 1, dFloor2.hashCode());
			
			decals.add(dFloor2);
			
			Decal dTop1 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.brickWall);
			dTop1.setPosition(x * TILE_SIZE, TILE_SIZE, z * TILE_SIZE - TILE_SIZE / 2f);
			dTop1.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dTop1.hashCode());
			
			decals.add(dTop1);
			
			Decal dTop2 = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.brickWall);
			dTop2.setPosition(x * TILE_SIZE, TILE_SIZE, z * TILE_SIZE - TILE_SIZE / 2f - TILE_SIZE);
			dTop2.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z - 1))
				LightRenderer.inst.addHash(x, z - 1, dTop2.hashCode());
			
			decals.add(dTop2);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dDoor1.hashCode());
				LightRenderer.inst.addHash(x, z, dDoor2.hashCode());
				LightRenderer.inst.addHash(x, z, dFloor1.hashCode());
				LightRenderer.inst.addHash(x, z, dFloor2.hashCode());
				LightRenderer.inst.addHash(x, z, dTop1.hashCode());
				LightRenderer.inst.addHash(x, z, dTop2.hashCode());
			}
		}
		else if(boxType.equals(BoxType.BUSH))
		{
			Decal dBush = Decal.newDecal(TILE_SIZE, 10f, Assets.inst.staticSprite.bush, true);
			dBush.setPosition(x * TILE_SIZE, dBush.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush.rotateY(45f);
			dBush.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dBush2 = Decal.newDecal(TILE_SIZE, dBush.getHeight(), Assets.inst.staticSprite.bush, true);
			dBush2.setPosition(x * TILE_SIZE, dBush2.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush2.rotateY(45f + 90f);
			dBush2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			alphaDecals.add(new PlantDecal(dBush, dBush2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
				LightRenderer.inst.addHash(x, z, dBush.hashCode());
				LightRenderer.inst.addHash(x, z, dBush2.hashCode());
			}
		}
		else if(boxType.equals(BoxType.CORN))
		{
			// TODO: Positional rendering is a bit wonky, instead of partitioning specifically on x and z axis, instead use -x + z, x + z, -x - z, x - z
			
			Decal dBush = Decal.newDecal(13f, 24f, Assets.inst.staticSprite.corn, true);
			dBush.setPosition(x * TILE_SIZE, dBush.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush.rotateY(45f);
			dBush.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Decal dBush2 = Decal.newDecal(13f, dBush.getHeight(), Assets.inst.staticSprite.corn, true);
			dBush2.setPosition(x * TILE_SIZE, dBush2.getHeight() / 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dBush2.rotateY(45f + 90f);
			dBush2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			alphaDecals.add(new PlantDecal(dBush, dBush2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.farmland);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			WorldHandler.inst.createBodyBox(x, z, 2f);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
				LightRenderer.inst.addHash(x, z, dBush.hashCode());
				LightRenderer.inst.addHash(x, z, dBush2.hashCode());
			}
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
			
			alphaDecals.add(new PlantDecal(dFireHydrant1, dFireHydrant2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			WorldHandler.inst.createBodyBox(x, z, 2f);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
				LightRenderer.inst.addHash(x, z, dFireHydrant1.hashCode());
				LightRenderer.inst.addHash(x, z, dFireHydrant2.hashCode());
			}
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
			
			alphaDecals.add(new PlantDecal(dGrassBlades1, dGrassBlades2));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dGrassBlades1.hashCode());
				LightRenderer.inst.addHash(x, z, dGrassBlades2.hashCode());
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
			}
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
			
			Decal dBoxFront = Decal.newDecal(3f, 30f, Assets.inst.staticSprite.stopBoxBack);
			Decal dBoxBack = Decal.newDecal(3f, 30f, Assets.inst.staticSprite.stopBoxBack);
			Decal dBoxSide1 = Decal.newDecal(1f, 30f, Assets.inst.staticSprite.stopBoxSide);
			Decal dBoxSide2 = Decal.newDecal(1f, 30f, Assets.inst.staticSprite.stopBoxSide);
			
			WorldHandler.inst.createBodyBox(x, z, 1f);
			
			switch(boxType)
			{
				case STOP_N:
					dStopFront.rotateY(180f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "N"));
					
					dBoxSide1.rotateY(90f);
					dBoxSide2.rotateY(90f);
					
					dBoxFront.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() + 1f);
					dBoxBack.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() + 2f);
					dBoxSide1.setPosition(dStopFront.getX() - 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() + 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					break;
				case STOP_S:
					dStopBack.rotateY(180f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "S"));
					
					dBoxSide1.rotateY(90f);
					dBoxSide2.rotateY(90f);
					
					dBoxFront.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() - 1f);
					dBoxBack.setPosition(dStopFront.getX(), dBoxBack.getHeight() / 2f, dStopFront.getZ() - 2f);
					dBoxSide1.setPosition(dStopFront.getX() - 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() + 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					break;
				case STOP_E:
					dStopFront.rotateY(90f);
					dStopBack.rotateY(270f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "E"));
					
					dBoxFront.rotateY(90f);
					dBoxBack.rotateY(90f);
					
					dBoxFront.setPosition(dStopFront.getX() - 1f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxBack.setPosition(dStopFront.getX() - 2f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxSide1.setPosition(dStopFront.getX() - 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() - 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					break;
				case STOP_W:
					dStopFront.rotateY(270f);
					dStopBack.rotateY(90f);
					alphaDecals.add(new StopDecal(dStopFront, dStopBack, "W"));
					
					dBoxFront.rotateY(90f);
					dBoxBack.rotateY(90f);
					
					dBoxFront.setPosition(dStopFront.getX() + 1f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxBack.setPosition(dStopFront.getX() + 2f, dBoxBack.getHeight() / 2f, dStopFront.getZ());
					dBoxSide1.setPosition(dStopFront.getX() + 1.5f, dBoxSide1.getHeight() / 2f, dStopFront.getZ() - 1.5f);
					dBoxSide2.setPosition(dStopFront.getX() + 1.5f, dBoxSide2.getHeight() / 2f, dStopFront.getZ() + 1.5f);
					break;
				default:
					break;
			}
			
			decals.add(dBoxFront);
			decals.add(dBoxBack);
			decals.add(dBoxSide1);
			decals.add(dBoxSide2);
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			if(LightRenderer.inst.hasLight(x, z))
			{
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
				LightRenderer.inst.addHash(x, z, dStopFront.hashCode());
				LightRenderer.inst.addHash(x, z, dStopBack.hashCode());
				LightRenderer.inst.addHash(x, z, dBoxSide1.hashCode());
				LightRenderer.inst.addHash(x, z, dBoxSide2.hashCode());
				LightRenderer.inst.addHash(x, z, dBoxFront.hashCode());
				LightRenderer.inst.addHash(x, z, dBoxBack.hashCode());
			}
		}
		else if(boxType.equals(BoxType.SIDEWALK))
		{
			Decal dSideWalk = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dSideWalk.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dSideWalk.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dSideWalk.hashCode());
			
			decals.add(dSideWalk);
		}
		else if(boxType.equals(BoxType.ROAD))
		{
			Decal dRoad = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dRoad.setPosition(x * TILE_SIZE, -1f, z * TILE_SIZE - TILE_SIZE / 2f);
			dRoad.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dRoad.hashCode());
			
			decals.add(dRoad);
		}
		else if(boxType.equals(BoxType.ROOF))
		{
			Decal dRoof = Decal.newDecal(TILE_SIZE, TILE_SIZE, tex);
			dRoof.setPosition(x * TILE_SIZE, TILE_SIZE * 2f, z * TILE_SIZE - TILE_SIZE / 2f);
			dRoof.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dRoof.hashCode());
			
			decals.add(dRoof);
		}
		else if(boxType.equals(BoxType.ROOF_N))
		{
			Decal dRoof = Decal.newDecal(TILE_SIZE, MathHelper.inst.getTriangleSlopeLength(TILE_SIZE * 3f, TILE_SIZE * 3f), Assets.inst.staticSprite.roof);
			dRoof.setPosition(x * TILE_SIZE, TILE_SIZE * 2f + TILE_SIZE * 1.5f, (z * TILE_SIZE - TILE_SIZE / 2f) + TILE_SIZE * 1f);
			dRoof.rotateX(45f);

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
			Decal dFence1 = null;
			Decal dFence2 = null;
			
			switch(boxType)
			{
				case FENCE_TL:
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x + 0.5f, z);
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x - 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence2.setPosition(x * TILE_SIZE - TILE_SIZE / 2f,
							dFence2.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2.rotateY(90f);
					break;
				case FENCE_T:
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x + 0.5f, z);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case FENCE_TR:
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x + 0.5f, z);
					WorldHandler.inst.createBodyLine(x + 0.5f, z, x + 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence2.setPosition(x * TILE_SIZE + TILE_SIZE / 2f,
							dFence2.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2.rotateY(90f);
					break;
				case FENCE_CL:
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x - 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE - TILE_SIZE / 2f,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence1.rotateY(90f);
					break;
				case FENCE_CR:
					WorldHandler.inst.createBodyLine(x + 0.5f, z, x + 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE + TILE_SIZE / 2f,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence1.rotateY(90f);
					break;
				case FENCE_BL:
					WorldHandler.inst.createBodyLine(x - 0.5f, z - 1f, x + 0.5f, z - 1f);
					WorldHandler.inst.createBodyLine(x - 0.5f, z, x - 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence2.setPosition(x * TILE_SIZE - TILE_SIZE / 2f,
							dFence2.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2.rotateY(90f);
					break;
				case FENCE_B:
					WorldHandler.inst.createBodyLine(x - 0.5f, z - 1f, x + 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case FENCE_BR:
					WorldHandler.inst.createBodyLine(x - 0.5f, z - 1f, x + 0.5f, z - 1f);
					WorldHandler.inst.createBodyLine(x + 0.5f, z, x + 0.5f, z - 1f);
					
					dFence1 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence1.setPosition(x * TILE_SIZE,
							dFence1.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE);
					dFence1.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2 = Decal.newDecal(16f, 10f, Assets.inst.staticSprite.fence);
					dFence2.setPosition(x * TILE_SIZE + TILE_SIZE / 2f,
							dFence2.getHeight() / 2f,
							z * TILE_SIZE - TILE_SIZE / 2f);
					dFence2.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					dFence2.rotateY(90f);
					break;
				default:
					return;
			}
			
			if(dFence2 == null)
			{
				alphaDecals.add(new FenceDecal(dFence1));
				
				if(LightRenderer.inst.hasLight(x, z))
					LightRenderer.inst.addHash(x, z, dFence1.hashCode());
			}
			else
			{
				alphaDecals.add(new FenceDecal(dFence1, dFence2));
				
				if(LightRenderer.inst.hasLight(x, z))
				{
					LightRenderer.inst.addHash(x, z, dFence1.hashCode());
					LightRenderer.inst.addHash(x, z, dFence2.hashCode());
				}
			}
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.floorGrass);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dFloor.hashCode());
			
			decals.add(dFloor);
		}
		else if(boxType.equals(BoxType.VENDING_MACHINE))
		{
			//WorldHandler.inst.createWallBody((float)x, (float)z);
			WorldHandler.inst.createBodyBox(x, z, 14f);
			
			Decal dVendingFront = Decal.newDecal(14f, 15f, Assets.inst.staticSprite.vendingMachineFront);
			dVendingFront.setPosition((x - 0.5f) * TILE_SIZE + 1f,
					dVendingFront.getHeight() / 2f,
					(z - 0.5f) * TILE_SIZE);
			dVendingFront.rotateY(90f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dVendingFront.hashCode());
			
			Decal dVendingSide1 = Decal.newDecal(14f, 15f, Assets.inst.staticSprite.vendingMachineSide);
			dVendingSide1.setPosition(x * TILE_SIZE,
					dVendingSide1.getHeight() / 2f,
					z * TILE_SIZE - 1f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dVendingSide1.hashCode());
			
			Decal dVendingSide2 = Decal.newDecal(14f, 15f, Assets.inst.staticSprite.vendingMachineSide);
			dVendingSide2.setPosition(x * TILE_SIZE,
					dVendingSide2.getHeight() / 2f,
					(z - 1f) * TILE_SIZE + 1f);
			
			if(LightRenderer.inst.hasLight(x, z))
				LightRenderer.inst.addHash(x, z, dVendingSide2.hashCode());
			
			decals.add(dVendingFront);
			decals.add(dVendingSide1);
			decals.add(dVendingSide2);
		}
		else if(boxType.equals(BoxType.SCARECROW))
		{
			Decal dScarecrow = Decal.newDecal(17f, 26f, Assets.inst.staticSprite.scarecrow);
			dScarecrow.setPosition(x * TILE_SIZE,
					dScarecrow.getHeight() / 2f,
					(z - 0.5f) * TILE_SIZE);
			dScarecrow.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			alphaDecals.add(new EntityDecal(dScarecrow));
			
			Decal dFloor = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.farmland);
			dFloor.setPosition(x * TILE_SIZE, 0f, z * TILE_SIZE - TILE_SIZE / 2f);
			dFloor.rotateX(90f);
			
			decals.add(dFloor);
			
			WorldHandler.inst.createBodyBox(x, z, 2f);
			
			CinemaController.inst.scarecrowCoordinates.x = dScarecrow.getX();
			CinemaController.inst.scarecrowCoordinates.y = dScarecrow.getZ();
		}
	}
	
	public void createEntityDecal(AbstractEntity decals)
	{
		alphaDecals.add(decals);
	}
	
	public void createItemDecal(int x, int z, ItemHandler.Item item)
	{
		switch(item)
		{
			case BURGER:
				Decal dBurger = Decal.newDecal(9f, 9f, Assets.inst.itemSprite.burger);
				dBurger.setPosition(x * TILE_SIZE, dBurger.getHeight() / 2f + 2f, z * TILE_SIZE - TILE_SIZE / 2f);
				dBurger.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				
				alphaDecals.add(new ItemDecal(dBurger));
				
				System.out.println((dBurger.getPosition().x) + " " + (dBurger.getPosition().z));
				break;
			default:
				break;
		}
	}
	
	public void destroyItemDecal(int x, int z)
	{
		for(int i = alphaDecals.size; --i >= 0;)
		{
			if(alphaDecals.get(i).decals.get(0).getX() == x
					&& alphaDecals.get(i).decals.get(0).getZ() == z)
				alphaDecals.removeIndex(i);
		}
	}
	
	public void setTexture(float x, float z, TextureRegion texture)
	{
		for(int i = alphaDecals.size; --i >= 0;)
		{
			if(alphaDecals.get(i).decals.get(0).getX() == x
					&& alphaDecals.get(i).decals.get(0).getZ() == z)
			{
				alphaDecals.get(i).decals.get(0).setTextureRegion(texture);
			}
		}
	}
	
	public void loadWallBoxes(String[][] walls0, String[][] walls1, int mapWidth, int mapHeight)
	{
		for(int x = 1; x < mapWidth - 1; x++)
		{
			for(int y = 1; y < mapHeight - 1; y++)
			{
				if(walls0[y][x].equals("W"))
				{
					if(!walls0[y + 1][x].equals("W"))
					{
						Decal dTop = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(0, x, y));
						dTop.setPosition(TILE_SIZE * x, TILE_SIZE / 2f, TILE_SIZE * y);
						decals.add(dTop);
						
						if(LightRenderer.inst.hasLight(x, y + 1))
							LightRenderer.inst.addHash(x, y + 1, dTop.hashCode());
					}
					if(!walls0[y - 1][x].equals("W"))
					{
						Decal dBottom = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(0, x, y));
						dBottom.setPosition(TILE_SIZE * x, TILE_SIZE / 2f, TILE_SIZE * y - TILE_SIZE);
						decals.add(dBottom);
						
						if(LightRenderer.inst.hasLight(x, y - 1))
							LightRenderer.inst.addHash(x, y - 1, dBottom.hashCode());
					}
					if(!walls0[y][x + 1].equals("W"))
					{
						Decal dRight = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(0, x, y));
						dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, TILE_SIZE / 2f, TILE_SIZE * y - TILE_SIZE / 2f);
						dRight.rotateY(90f);
						decals.add(dRight);
						
						if(LightRenderer.inst.hasLight(x + 1, y))
							LightRenderer.inst.addHash(x + 1, y, dRight.hashCode());
					}
					if(!walls0[y][x - 1].equals("W"))
					{
						Decal dLeft = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(0, x, y));
						dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, TILE_SIZE / 2f, TILE_SIZE * y - TILE_SIZE / 2f);
						dLeft.rotateY(90f);
						decals.add(dLeft);
						
						if(LightRenderer.inst.hasLight(x - 1, y))
							LightRenderer.inst.addHash(x - 1, y, dLeft.hashCode());
					}
				}
			}
		}
		
		for(int x = 1; x < mapWidth - 1; x++)
		{
			for(int y = 1; y < mapHeight - 1; y++)
			{
				if(walls1[y][x].equals("W"))
				{
					if(!walls1[y + 1][x].equals("W"))
					{
						if(!walls1[y + 1][x].equals("WS"))
						{
							Decal dTop = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(1, x, y));
							dTop.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y);
							decals.add(dTop);
							
							if(LightRenderer.inst.hasLight(x, y + 1))
								LightRenderer.inst.addHash(x, y + 1, dTop.hashCode());
						}
					}
					if(!walls1[y - 1][x].equals("W"))
					{
						if(!walls1[y - 1][x].equals("WS"))
						{
							Decal dBottom = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(1, x, y));
							dBottom.setPosition(TILE_SIZE * x, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y - TILE_SIZE);
							decals.add(dBottom);
							
							if(LightRenderer.inst.hasLight(x, y - 1))
								LightRenderer.inst.addHash(x, y - 1, dBottom.hashCode());
						}
					}
					if(!walls1[y][x + 1].equals("W"))
					{
						Decal dRight = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(1, x, y));
						dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y - TILE_SIZE / 2f);
						dRight.rotateY(90f);
						decals.add(dRight);
						
						if(LightRenderer.inst.hasLight(x + 1, y))
							LightRenderer.inst.addHash(x + 1, y, dRight.hashCode());
					}
					if(!walls1[y][x - 1].equals("W"))
					{
						Decal dLeft = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(1, x, y));
						dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y - TILE_SIZE / 2f);
						dLeft.rotateY(90f);
						decals.add(dLeft);
						
						if(LightRenderer.inst.hasLight(x - 1, y))
							LightRenderer.inst.addHash(x - 1, y, dLeft.hashCode());
					}
				}
				if(walls1[y][x].equals("WS"))
				{
					Decal dLeft = Decal.newDecal(TILE_SIZE, TILE_SIZE, MapLoader.inst.getTexture(1, x, y));
					dLeft.setPosition(TILE_SIZE * x - TILE_SIZE / 2f, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y - TILE_SIZE / 2f);
					dLeft.rotateY(90f);
					decals.add(dLeft);
					
					if(LightRenderer.inst.hasLight(x - 1, y))
						LightRenderer.inst.addHash(x - 1, y, dLeft.hashCode());
					
					Decal dRight = Decal.newDecal(TILE_SIZE, TILE_SIZE, Assets.inst.staticSprite.brickWall);
					dRight.setPosition(TILE_SIZE * x + TILE_SIZE / 2f, TILE_SIZE / 2f + TILE_SIZE, TILE_SIZE * y - TILE_SIZE / 2f);
					dRight.rotateY(90f);
					decals.add(dRight);
					
					if(LightRenderer.inst.hasLight(x + 1, y))
						LightRenderer.inst.addHash(x + 1, y, dRight.hashCode());
				}
			}
		}
		
		System.out.println("RAM: " + (Gdx.app.getJavaHeap() / 1000000) + "Mb");
	}
	
	public void updateShadows()
	{
		for(int i = 0; i < decals.size; i++)
		{
			decals.get(i).setColor(48f / 255f, 58f / 255f, 63f / 255f, decals.get(i).getColor().a);
		}
		
		for(int i = 0; i < alphaDecals.size; i++)
		{
			if(!alphaDecals.get(i).multiDecal)
			{
				if(!alphaDecals.get(i).onlyOneDecal)
				{
					alphaDecals.get(i).decals.get(0).setColor(48f / 255f, 58f / 255f, 63f / 255f, 1f);
					alphaDecals.get(i).decals.get(1).setColor(48f / 255f, 58f / 255f, 63f / 255f, 1f);
				}
				else
				{
					alphaDecals.get(i).decals.get(0).setColor(48f / 255f, 58f / 255f, 63f / 255f, 1f);
				}
			}
			else
			{
				for(int k = 0; k < alphaDecals.get(i).decals.size; k++)
					alphaDecals.get(i).decals.get(k).setColor(48f / 255f, 58f / 255f, 63f / 255f, 1f);
			}
		}
		
		LightRenderer.inst.update();
	}
	
	public void optimizeBoxes(String[][] collisionMap0, String[][] collisionMap1)
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
					indexCounter.add(j);
				}
			}
			
			for(int k = 0; k < indexCounter.size - 1; k++)
			{
				newDecals.removeIndex(indexCounter.get(k));
			}
		}
		
		decals = newDecals;
		
		for(int j = newDecals.size; --j >= 0;)
		{
			try
			{
				if(collisionMap0[(int)(newDecals.get(j).getZ() / TILE_SIZE)][(int)(newDecals.get(j).getX() / TILE_SIZE)].equals("W")
						&& newDecals.get(j).getY() == TILE_SIZE / 2f)
				{
					if(newDecals.get(j).getRotation().getAngle() == 0f)
					{
						if(collisionMap0[(int)(newDecals.get(j).getZ() / TILE_SIZE) + 1][(int)(newDecals.get(j).getX() / TILE_SIZE)].equals("W"))
							newDecals.removeIndex(j);
					}
					/*else if(newDecals.get(j).getRotation().getAngle() == 90f)
					{
						if(collisionMap[(int)(newDecals.get(j).getZ() / TILE_SIZE)][(int)(newDecals.get(j).getX() / TILE_SIZE) - 1].equals("W"))
							newDecals.removeIndex(j);
					}*/
				}
				if(collisionMap1[(int)(newDecals.get(j).getZ() / TILE_SIZE)][(int)(newDecals.get(j).getX() / TILE_SIZE)].equals("W")
						&& newDecals.get(j).getY() == TILE_SIZE / 2f + TILE_SIZE)
				{
					if(newDecals.get(j).getRotation().getAngle() == 0f)
					{
						if(collisionMap1[(int)(newDecals.get(j).getZ() / TILE_SIZE) + 1][(int)(newDecals.get(j).getX() / TILE_SIZE)].equals("W"))
							newDecals.removeIndex(j);
					}
					/*else if(newDecals.get(j).getRotation().getAngle() == 90f)
					{
						if(collisionMap[(int)(newDecals.get(j).getZ() / TILE_SIZE)][(int)(newDecals.get(j).getX() / TILE_SIZE) - 1].equals("W"))
							newDecals.removeIndex(j);
					}*/
				}
			}
			catch(Exception e) {}
		}
		
		decals = newDecals;
	}
	
	public void setTileData(Array<TileData> tileData)
	{
		//this.tileData.clear();
		//this.tileData = tileData;
		
		//LightRenderer.inst.setTileData(tileData);
	}
	
	public void setDecalColor(int x, int z, float r, float g, float b)
	{
		for(int i = 0; i < decals.size; i++)
		{
			if((int)(decals.get(i).getX() / TILE_SIZE - 0.5f) == x
					&& (int)(decals.get(i).getZ() / TILE_SIZE - 0.5f) == z)
			{
				decals.get(i).setColor(r, g, b, 1f);
			}
		}
	}
	
	public void setDecalColor(IntArray hashes, float r, float g, float b)
	{
		for(int i = 0; i < decals.size; i++)
		{
			for(int j = 0; j < hashes.size; j++)
			{
				if(decals.get(i).hashCode() == hashes.get(j))
					decals.get(i).setColor(r, g, b, 1f);
			}
		}
	}
	
	public void setAlphaDecalColor(IntArray hashes, float r, float g, float b)
	{
		for(int i = 0; i < alphaDecals.size; i++)
		{
			for(int j = 0; j < hashes.size; j++)
			{
				if(!alphaDecals.get(i).multiDecal)
				{
					if(!alphaDecals.get(i).onlyOneDecal)
					{
						if(alphaDecals.get(i).decals.get(0).hashCode() == hashes.get(j))
							alphaDecals.get(i).decals.get(0).setColor(r, g, b, 1f);
						
						if(alphaDecals.get(i).decals.get(1).hashCode() == hashes.get(j))
							alphaDecals.get(i).decals.get(1).setColor(r, g, b, 1f);
					}
					else
					{
						if(alphaDecals.get(i).decals.get(0).hashCode() == hashes.get(j))
							alphaDecals.get(i).decals.get(0).setColor(r, g, b, 1f);
					}
				}
				else
				{
					for(int k = 0; k < alphaDecals.get(i).decals.size; k++)
						if(alphaDecals.get(i).decals.get(k).hashCode() == hashes.get(j))
							alphaDecals.get(i).decals.get(k).setColor(r, g, b, 1f);
				}
			}
		}
	}
	
	public void printHash()
	{
		for(int i = 0; i < decals.size; i++)
		{
			System.out.println(decals.get(i).hashCode());
		}
	}

	public void render(Vector3 camPosition, PerspectiveCamera cam)
	{
		batch.add(dMoon);
		batch.add(dDebugX);
		batch.add(dDebugZ);
		
		for(int i = 0; i < decals.size; i++)
		{
			batch.add(decals.get(i));
		}
		batch.flush();
		
		dMoon.setPosition(WorldHandler.inst.getBodyX() + 120f, 80f, WorldHandler.inst.getBodyY() + 120f);
		dMoon.lookAt(camPosition, cam.up);

		dDebugX.setPosition(WorldHandler.inst.getBodyX() + 8f, 4f, WorldHandler.inst.getBodyY() + 8f);
		dDebugZ.setPosition(WorldHandler.inst.getBodyX() + 2.5f, 4f, WorldHandler.inst.getBodyY() + 13.5f);
		
		for(int i = 0; i < alphaDecals.size; i++)
		{
			alphaDecals.get(i).updateRenderOrder();
			
			if(!alphaDecals.get(i).multiDecal)
			{
				if(!alphaDecals.get(i).renderOnlyOneSide
						&& !alphaDecals.get(i).onlyOneDecal)
				{
					batch.add(alphaDecals.get(i).decals.get(0));
					batch.add(alphaDecals.get(i).decals.get(1));
				}
				else
				{
					batch.add(alphaDecals.get(i).decals.get(0));
				}
			}
			else
			{
				for(int j = 0; j < alphaDecals.get(i).decals.size; j++)
					batch.add(alphaDecals.get(i).decals.get(j));
			}
		}
		batch.flush();
	}
	
	@Override
	public void dispose()
	{
		
	}
}
