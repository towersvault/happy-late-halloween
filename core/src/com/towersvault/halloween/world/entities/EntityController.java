package com.towersvault.halloween.world.entities;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.utils.Assets;
import com.towersvault.halloween.world.BoxesHandler;

import javax.swing.*;

public class EntityController
{
	public static final EntityController inst = new EntityController();
	
	public enum Entity
	{
		PLANE,
		BOMB
	}
	
	private Array<AbstractEntity> entities = new Array<AbstractEntity>();
	private Array<Decal> entitySwap = new Array<Decal>();
	
	public void init()
	{
		entities.clear();
		
		createEntity(Entity.PLANE, -2, 1, -2);
		createEntity(Entity.BOMB, -2, 0, -2);
	}
	
	public void createEntity(Entity entity, float x, float y, float z)
	{
		entitySwap.clear();
		
		switch(entity)
		{
			case PLANE:
				Decal dBottom = Decal.newDecal(5f, 16f, Assets.inst.entitySprite.planeSide);
				dBottom.setPosition(x * BoxesHandler.TILE_SIZE, y * BoxesHandler.TILE_SIZE, z * BoxesHandler.TILE_SIZE);
				dBottom.rotateX(90f);
				System.out.println(dBottom.getRotation().x);
				
				Decal dSide1 = Decal.newDecal(5f, 16f, Assets.inst.entitySprite.planeSide);
				dSide1.setPosition(x * BoxesHandler.TILE_SIZE + dSide1.getWidth() / 2f, y * BoxesHandler.TILE_SIZE + dSide1.getWidth() / 2f, z * BoxesHandler.TILE_SIZE);
				dSide1.rotateY(90f);
				dSide1.rotateZ(90f);
				
				Decal dSide2 = Decal.newDecal(5f, 16f, Assets.inst.entitySprite.planeSide);
				dSide2.setPosition(x * BoxesHandler.TILE_SIZE - dSide2.getWidth() / 2f, y * BoxesHandler.TILE_SIZE + dSide2.getWidth() / 2f, z * BoxesHandler.TILE_SIZE);
				dSide2.rotateY(90f);
				dSide2.rotateZ(90f);
				
				Decal dFront = Decal.newDecal(5f, 5f, Assets.inst.entitySprite.planeFront);
				dFront.setPosition(dBottom.getX(), dBottom.getY() + dFront.getHeight() / 2f, dBottom.getZ() + dBottom.getHeight() / 2f);
				
				Decal dBack = Decal.newDecal(5f, 5f, Assets.inst.entitySprite.planeBack);
				dBack.setPosition(dBottom.getX(), dBottom.getY() + dBack.getHeight() / 2f, dBottom.getZ() - dBottom.getHeight() / 2f);
				
				Decal dWing = Decal.newDecal(21f, 5f, Assets.inst.entitySprite.planeWingFront);
				dWing.setPosition(dBottom.getX(), dBottom.getY() + dBottom.getWidth() / 2f, dBottom.getZ() + 0.5f);
				dWing.rotateX(90f);
				
				Decal dWingVert = Decal.newDecal(7f, 7f, Assets.inst.entitySprite.planeWingVert);
				dWingVert.setPosition(dBottom.getX(), dBottom.getY() + dWingVert.getHeight() / 2f, dBottom.getZ() - dBottom.getHeight() / 2f);
				dWingVert.rotateY(90f);
				
				Decal dWingBack = Decal.newDecal(13f, 4f, Assets.inst.entitySprite.planeWingBack);
				dWingBack.setPosition(dBottom.getX(), dBottom.getY() + dBottom.getWidth() / 2f, dBottom.getZ() - dBottom.getHeight() / 2f);
				dWingBack.rotateX(90f);
				
				Decal dProp = Decal.newDecal(7f, 3f, Assets.inst.entitySprite.planeProp);
				dProp.setPosition(dBottom.getX(), dBottom.getY() + dBottom.getWidth() / 2f, dBottom.getZ() + dBottom.getHeight() / 2f + 1f);
				//dProp.rotateX(90f);
				
				entitySwap.add(dBottom);
				entitySwap.add(dSide1);
				entitySwap.add(dSide2);
				entitySwap.add(dFront);
				entitySwap.add(dBack);
				entitySwap.add(dWing);
				/*entitySwap.add(dWingVert);
				entitySwap.add(dWingBack);
				entitySwap.add(dProp);*/
				
				PlaneEntity plane = new PlaneEntity(entitySwap);
				BoxesHandler.inst.createEntityDecal(plane);
				entities.add(plane);
				break;
			case BOMB:
				Decal dBomb = Decal.newDecal(7f, 10f, Assets.inst.entitySprite.bomb);
				dBomb.setPosition(x * BoxesHandler.TILE_SIZE, y * BoxesHandler.TILE_SIZE + dBomb.getHeight() / 2f, z * BoxesHandler.TILE_SIZE);
				dBomb.setBlending(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				
				entitySwap.add(dBomb);
				
				BombEntity bomb = new BombEntity(entitySwap);
				BoxesHandler.inst.createEntityDecal(bomb);
				entities.add(bomb);
				break;
			default:
				break;
		}
	}
}
