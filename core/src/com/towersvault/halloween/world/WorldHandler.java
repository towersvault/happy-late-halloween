package com.towersvault.halloween.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

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
	
	public void init()
	{
		world = new World(new Vector2(0, 0), false);
		
		wallBodies.clear();
		bodiesIterator = 0;
		
		// Player body
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.DynamicBody;
		bd.position.x = 0f;
		bd.position.y = 0f;
		bd.gravityScale = 0f;
		
		playerBody = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(4f, 4f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		playerBody.createFixture(fd);
		playerBody.setSleepingAllowed(false);
		
		shape.dispose();
	}
	
	public void setCollsionMap(String[][] collisionMap)
	{
		this.collisionMap = collisionMap;
		
		createWallBodies();
		
		for(int i = 0; i < wallBodies.size; i++)
			System.out.println((int)(wallBodies.get(i).getPosition().x / TILE_WIDTH) + " " + (int)(wallBodies.get(i).getPosition().y / TILE_WIDTH));
	}
	
	private void createWallBodies()
	{
		for(int i = 0; i < 9; i++)
			createWallBody();
	}
	
	private void createWallBody()
	{
		float x = 0f;
		float y = 2f;
		
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
		
		System.out.println("Bodies Loaded: " + world.getBodyCount());
	}
	
	private boolean collisionAt(float x, float z)
	{
		for(int i = 0; i < wallBodies.size; i++)
			if((int)(wallBodies.get(i).getPosition().x / TILE_WIDTH) == x * TILE_WIDTH
					&& (int)(wallBodies.get(i).getPosition().y / TILE_WIDTH) == z * TILE_WIDTH)
				return true;
		
		System.out.println(x + " " + z);
		
		return false;
	}
	
	private void moveWallBody(float x, float z)
	{
		
		System.out.println("Move to " + (x * TILE_WIDTH) + " " + (z * TILE_WIDTH));
		wallBodies.get(bodiesIterator).setTransform((x * TILE_WIDTH), (z * TILE_WIDTH), wallBodies.get(bodiesIterator).getAngle());
		bodiesIterator++;
		if(bodiesIterator >= wallBodies.size)
			bodiesIterator = 0;
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
	
	public void buildMapShapes(TiledMap tiledMap, String layerName)
	{
		try
		{
			MapBodyBuilder.buildShapes(tiledMap, 1f, world, layerName);
		}
		catch(Exception e)
		{
			System.out.println("Tiledmap missing layer " + layerName + ".");
			e.printStackTrace();
		}
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
	
	public int getBodyTileX()
	{
		return (int)((getBodyX() + TILE_WIDTH / 2f) / TILE_WIDTH);
	}
	
	public int getBodyTileZ()
	{
		return (int)((getBodyY() - 2f) / TILE_WIDTH);
	}
	
	public void update()
	{
		world.step(1f / 60f, 6, 2);
		
		for(int x = -SEARCH_RANGE; x <= SEARCH_RANGE; x++)
		{
			for(int z = -SEARCH_RANGE; z <= SEARCH_RANGE; z++)
			{
				try
				{
					if(collisionMap[z + getBodyTileZ()][x + getBodyTileX()].equals("W")
							&& !collisionAt(x + getBodyTileX(), z + getBodyTileZ()))
						//System.out.println("Wall at (" + (x + getBodyTileX()) + ", " + (z + getBodyTileZ()) + ")");
						moveWallBody(x, z);
				}
				catch(Exception e) {}
			}
		}
		
		//System.out.println("x: " + getBodyTileX() + " z: " + getBodyTileZ());
		
		//System.out.println((int)(getBodyX() / TILE_WIDTH) + " " + (int)(getBodyY() / TILE_WIDTH));
	}
	
	@Override
	public void dispose()
	{
		world.dispose();
	}
}
