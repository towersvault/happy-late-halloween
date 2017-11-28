package com.towersvault.halloween.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class WorldHandler implements Disposable
{
	public static final WorldHandler inst = new WorldHandler();
	
	private World world;
	
	public final float TILE_WIDTH = 20f;
	
	private Body playerBody;
	
	public void init()
	{
		world = new World(new Vector2(0, 0), false);
		
		// Player body
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.DynamicBody;
		bd.position.x = 0f;
		bd.position.y = 0f;
		bd.gravityScale = 0f;
		
		playerBody = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(3f, 3f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		
		playerBody.createFixture(fd);
		playerBody.setSleepingAllowed(false);
		
		shape.dispose();
		
		// Wall
		BodyDef bd2 = new BodyDef();
		bd2.type = BodyDef.BodyType.StaticBody;
		
		Body wall = world.createBody(bd2);

		EdgeShape es = new EdgeShape();
		es.set(10f, -10f, -30f, -10f);
		
		FixtureDef fd2 = new FixtureDef();
		fd2.shape = es;
		
		wall.createFixture(fd2);
		wall.setSleepingAllowed(false);
		
		es.dispose();
		
		// Wall 2
		BodyDef bd3 = new BodyDef();
		bd3.type = BodyDef.BodyType.StaticBody;
		
		Body wall2 = world.createBody(bd3);

		EdgeShape es2 = new EdgeShape();
		es2.set(-30f, -10f, -30f, 10f);
		
		FixtureDef fd3 = new FixtureDef();
		fd3.shape = es2;
		
		wall2.createFixture(fd3);
		wall2.setSleepingAllowed(false);
		
		es2.dispose();
		
		// Wall 3
		BodyDef bd4 = new BodyDef();
		bd4.type = BodyDef.BodyType.StaticBody;
		
		Body wall3 = world.createBody(bd4);

		EdgeShape es3 = new EdgeShape();
		es3.set(-30f, 10f, -10f, 10f);
		
		FixtureDef fd4 = new FixtureDef();
		fd4.shape = es3;
		
		wall3.createFixture(fd4);
		wall3.setSleepingAllowed(false);
		
		es3.dispose();
	}
	
	public Body createWallBody(Body body, BodyDef bd)
	{
		body = world.createBody(bd);
		
		return body;
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
	
	public void update()
	{
		world.step(1f / 60f, 6, 2);
		
		//System.out.println((int)getBodyX() + " " + (int)getBodyY());
	}
	
	@Override
	public void dispose()
	{
		world.dispose();
	}
}
