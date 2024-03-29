package com.towersvault.halloween.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.towersvault.halloween.utils.Constants;

public class WorldHandler implements Disposable
{
	public static final WorldHandler inst = new WorldHandler();
	
	private World world;
	
	public final float TILE_WIDTH = 16f;
	
	private Body playerBody;
	
	private static final int SEARCH_RANGE = 2; // The amount of tiles it should check for collision.
	private String[][] collisionMap;
	private Array<Body> wallBodies = new Array<Body>();
	private int bodiesIterator = 0;
	
	private Array<Body> interactionBodies = new Array<Body>();
	private Array<ItemData> interactionData = new Array<ItemData>();
	
	private Vector2 newPlayerCoordinates = new Vector2();
	
	private class ItemData
	{
		public ItemHandler.Item item;
		public float x;
		public float y;
		
		public ItemData(ItemHandler.Item item, float x, float y)
		{
			this.item = item;
			this.x = x;
			this.y = y;
		}
	}
	
	public void init()
	{
		world = new World(new Vector2(0, 0), false);
		
		wallBodies.clear();
		bodiesIterator = 0;
		
		interactionBodies.clear();
		interactionData.clear();
		
		// Player body
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.DynamicBody;
		bd.position.x = 40f * TILE_WIDTH;
		bd.position.y = 20f * TILE_WIDTH;
		bd.gravityScale = 0f;
		
		playerBody = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(4f, 4f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		playerBody.createFixture(fd);
		playerBody.setSleepingAllowed(false);
		
		shape.dispose();
		
		world.setContactListener(new ContactListener()
		{
			@Override
			public void beginContact(Contact contact)
			{
				for(int i = interactionBodies.size; --i >= 0;)
				{
					if((contact.getFixtureA().getBody() == playerBody
						&& contact.getFixtureB().getBody() == interactionBodies.get(i))
						||
						(contact.getFixtureA().getBody() == interactionBodies.get(i)
						&& contact.getFixtureB().getBody() == playerBody))
					{
						if(ItemHandler.inst.inventoryAdd(interactionData.get(i).item))
						{
							ItemHandler.inst.destroyItem((int) interactionBodies.get(i).getPosition().x, (int) interactionBodies.get(i).getPosition().y);
							interactionBodies.removeIndex(i);
							interactionData.removeIndex(i);
						}
					}
				}
			}
			
			@Override
			public void endContact(Contact contact)
			{
			
			}
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
	}
	
	public void setCollsionMap(String[][] collisionMap)
	{
		this.collisionMap = collisionMap;
		
		createWallBodies();
		
		/*for(int i = 0; i < wallBodies.size; i++)
			System.out.println((int)(wallBodies.get(i).getPosition().x / TILE_WIDTH) + " " + (int)(wallBodies.get(i).getPosition().y / TILE_WIDTH));*/
	}
	
	private void createWallBodies()
	{
		for(int i = 0; i < 9; i++)
			createWallBody();
	}
	
	private void createWallBody()
	{
		float x = -200f;
		float y = -200f;
		
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(x * TILE_WIDTH, y * TILE_WIDTH - TILE_WIDTH / 2f);
		
		Body body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(TILE_WIDTH / 2f, TILE_WIDTH / 2f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		body.createFixture(fd);
		body.setSleepingAllowed(false);
		
		shape.dispose();
		
		wallBodies.add(body);
	}
	
	private boolean collisionAt(float x, float z)
	{
		//System.out.println("Body: " + x + " " + z);
		for(int i = 0; i < wallBodies.size; i++)
		{
			if(wallBodies.get(i).getPosition().x == x
					&& wallBodies.get(i).getPosition().y == z)
				return true;
		}
		
		return false;
	}
	
	private void moveWallBody(float x, float z)
	{
		if(!collisionAt(x * TILE_WIDTH, z * TILE_WIDTH))
		{
			//System.out.println("Moved body to (" + (x * TILE_WIDTH) + ", " + (z * TILE_WIDTH) + ").");
			
			wallBodies.get(bodiesIterator).setTransform((x * TILE_WIDTH),
					(z * TILE_WIDTH),
					wallBodies.get(bodiesIterator).getAngle());
			bodiesIterator += 1;
			if(bodiesIterator >= wallBodies.size)
				bodiesIterator = 0;
		}
	}
	
	public void createWallBody(float x, float y)
	{
		/*BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(x * TILE_WIDTH, y * TILE_WIDTH - TILE_WIDTH / 2f);
		
		Body body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(TILE_WIDTH / 2f, TILE_WIDTH / 2f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		body.createFixture(fd);
		body.setSleepingAllowed(false);
		
		shape.dispose();
		
		System.out.println("Bodies Loaded: " + world.getBodyCount());*/
	}
	
	public void createInteractionBody(int x, int z, ItemHandler.Item item)
	{
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(x * TILE_WIDTH, z * TILE_WIDTH - TILE_WIDTH / 2f);
		
		Body body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(3f, 3f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.isSensor = true;
		
		body.createFixture(fd);
		body.setFixedRotation(true);
		body.setSleepingAllowed(false);
		
		shape.dispose();
		
		interactionBodies.add(body);
		
		interactionData.add(new ItemData(item, x * TILE_WIDTH, z * TILE_WIDTH - TILE_WIDTH / 2f));
	}
	
	public void createBodyBox(float x, float y, float sideLength)
	{
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(x * TILE_WIDTH, y * TILE_WIDTH - TILE_WIDTH / 2f);
		
		Body body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(sideLength / 2f, sideLength / 2f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		body.createFixture(fd);
		body.setSleepingAllowed(false);
		
		shape.dispose();
	}
	
	public void createBodyLine(float x1, float y1, float x2, float y2)
	{
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		//bd.position.set(x1 * TILE_WIDTH, y1 * TILE_WIDTH);
		
		Body body = world.createBody(bd);
		
		EdgeShape shape = new EdgeShape();
		shape.set(x1 * TILE_WIDTH, y1 * TILE_WIDTH,
				x2 * TILE_WIDTH, y2 * TILE_WIDTH);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		body.createFixture(fd);
		shape.dispose();
	}
	
	public void resetBodyVelocity()
	{
		playerBody.setLinearVelocity(playerBody.getLinearVelocity().x / 2f, playerBody.getLinearVelocity().y / 2f);
		if(playerBody.getLinearVelocity().x < 0.001f && playerBody.getLinearVelocity().x > -0.001f)
			playerBody.setLinearVelocity(0f, playerBody.getLinearVelocity().y);
		if(playerBody.getLinearVelocity().y < 0.001f && playerBody.getLinearVelocity().y > -0.001f)
			playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0f);
		/*System.out.println(playerBody.getLinearVelocity().x + " " + playerBody.getLinearVelocity().y);*/
	}
	
	public void moveBody(float x, float y)
	{
		playerBody.setLinearVelocity(x * TILE_WIDTH + playerBody.getLinearVelocity().x, y * TILE_WIDTH + playerBody.getLinearVelocity().y);
	}
	
	public void rotateBody(float angle)
	{
		/*System.out.println(MathUtils.degreesToRadians * angle);*/
		//playerBody.setTransform(playerBody.getPosition(), MathUtils.degreesToRadians * angle);
	}
	
	public float getBodyX()
	{
		return playerBody.getPosition().x + 0f;
	}
	
	public float getBodyY()
	{
		return playerBody.getPosition().y + 0f;
	}
	
	public Vector2 getBodyPosition()
	{
		return playerBody.getPosition();
	}
	
	public int getBodyTileX()
	{
		return (int)((getBodyX() + TILE_WIDTH / 2f) / TILE_WIDTH);
	}
	
	public int getBodyTileZ()
	{
		return (int)((getBodyY() - 2f) / TILE_WIDTH);
	}
	
	public void setPlayerPosition(float x, float y)
	{
		newPlayerCoordinates.x = x;
		newPlayerCoordinates.y = y;
	}
	
	public void update()
	{
		if((newPlayerCoordinates.x != 0f) || (newPlayerCoordinates.y != 0f))
		{
			playerBody.setTransform(newPlayerCoordinates, playerBody.getAngle());
			newPlayerCoordinates.x = 0f;
			newPlayerCoordinates.y = 0f;
		}
		
		world.step(1f / 60f, 6, 2);
		
		if(Constants.DYNAMIC_PHYSICS)
		{
			for(int x = -SEARCH_RANGE; x <= SEARCH_RANGE; x++)
			{
				for(int z = -SEARCH_RANGE; z <= SEARCH_RANGE; z++)
				{
					try
					{
						if (collisionMap[z + getBodyTileZ()][x + getBodyTileX()].equals("W"))
						{
							moveWallBody(x + getBodyTileX(), z + getBodyTileZ() - 0.5f);
						}
					}
					catch(Exception e) {}
				}
			}
		}
	}
	
	@Override
	public void dispose()
	{
		world.dispose();
	}
}
