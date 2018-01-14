package com.towersvault.halloween.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.towersvault.halloween.utils.Assets;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.CustomCameraStrategy;
import com.towersvault.halloween.utils.MathHelper;
import com.towersvault.halloween.world.BoxesHandler;
import com.towersvault.halloween.world.ItemHandler;
import com.towersvault.halloween.world.MapLoader;
import com.towersvault.halloween.world.WorldHandler;
import com.badlogic.gdx.Input.Keys;
import com.towersvault.halloween.world.entities.EntityController;

public class Renderer implements Disposable
{
	public static final Renderer inst = new Renderer();
	
	private PerspectiveCamera camera;
	
	private OrthographicCamera orthographicCamera;
	private SpriteBatch spriteBatch;
	private Sprite sprSky;
	private Sprite sprVignette;
	private SpriteBatch uiBatch;
	
	private static final float MOVEMENT_SPEED = 55f;
	
	public static int CONTROLLER_X = 0;
	public static int CONTROLLER_Y = 0;
	
	/*private float cameraRotation = 0f;*/
	private Vector2 listener = new Vector2();
	
	private ShaderProgram shader;
	
	private Sprite testUi;
	
	public void init()
	{
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0f, 8f, 0f);
		camera.near = 1f;
		camera.far = 500f;
		camera.update();
		
		TextureRegion tSky = new TextureRegion(new Texture(Gdx.files.internal("sky.png")));
		
		sprSky = new Sprite(tSky);
		sprSky.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.65f);
		sprSky.setX(-Gdx.graphics.getWidth() / 2f);
		sprSky.setY(-Gdx.graphics.getHeight() / 2f);
		
		TextureRegion tVignette = new TextureRegion(new Texture(Gdx.files.internal("vignette.png")));
		
		sprVignette = new Sprite(tVignette);
		sprVignette.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sprVignette.setX(-Gdx.graphics.getWidth() / 2f);
		sprVignette.setY(-Gdx.graphics.getHeight() / 2f);
		
		spriteBatch = new SpriteBatch();
		
		orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthographicCamera.position.set(0f, 0f, 0f);
		orthographicCamera.update();
		
		WorldHandler.inst.init();
		WorldHandler.inst.setPlayerPosition(55f * BoxesHandler.TILE_SIZE, 24f * BoxesHandler.TILE_SIZE);
		
		BoxesHandler.inst.init(new DecalBatch(new CustomCameraStrategy(camera)));
		
		MapLoader.inst.loadMap(MapLoader.Map.WORLD1);
		
		Gdx.input.setInputProcessor(new InputProcessor()
		{
			private int dragX;
			float rotateSpeed = 0.14f;
			
			@Override
			public boolean keyDown(int keycode) { return false; }
			
			@Override
			public boolean keyUp(int keycode) { return false; }
			
			@Override
			public boolean keyTyped(char character) { return false; }
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				float x = dragX - screenX;
				
				camera.rotate(Vector3.Y, x * rotateSpeed);
				camera.update();
				
				dragX = screenX;
				
				return true;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
				float x = dragX - screenX;
				
				camera.rotate(Vector3.Y, x * rotateSpeed);
				camera.update();
				
				dragX = screenX;
				
				/*cameraRotation += x * rotateSpeed;
				cameraRotation = cameraRotation % 360f;*/
				
				listener.rotate(x * rotateSpeed);
				listener.sub(WorldHandler.inst.getBodyPosition()).nor();
				
				return true;
			}
			
			@Override
			public boolean scrolled(int amount) { return false; }
		});
		
		ItemHandler.inst.createItem(ItemHandler.Item.BURGER, 5, 5);
		
		EntityController.inst.init();
		
		uiBatch = new SpriteBatch();
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shaders/vignette.vsh"), Gdx.files.internal("shaders/vignette.fsh"));
		uiBatch.setShader(shader);
		
		System.out.println(shader.isCompiled() ? "Shader works!" : shader.getLog());
		
		testUi = new Sprite(Assets.inst.entitySprite.bomb);
		testUi.setSize(testUi.getWidth() * Constants.resize(), testUi.getHeight() * Constants.resize());
	}
	
	public void render()
	{
		shader.begin();
		shader.setUniformf("u_distort", MathUtils.random(4), MathUtils.random(4), 0);
		shader.end();
		
		spriteBatch.setProjectionMatrix(orthographicCamera.combined);
		spriteBatch.begin();
		sprSky.draw(spriteBatch);
		spriteBatch.end();
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glCullFace(GL20.GL_NONE);
		
		BoxesHandler.inst.render(camera.position, camera);
		
		spriteBatch.setProjectionMatrix(orthographicCamera.combined);
		spriteBatch.begin();
		sprVignette.draw(spriteBatch);
		spriteBatch.end();
		
		updateCameraMovement();
		
		WorldHandler.inst.update();
		WorldHandler.inst.resetBodyVelocity();
		
		uiBatch.setProjectionMatrix(camera.combined);
		uiBatch.begin();
		testUi.draw(uiBatch);
		uiBatch.end();
	}
	
	public Vector2 getListener()
	{
		/*if(cameraRotation < 0f)
			return 360 + cameraRotation;
		return MathUtils.degreesToRadians * cameraRotation;*/
		
		return listener;
	}
	
	public void setCameraRotation(float rotation)
	{
		camera.direction.set(0, 0, 1);
		camera.up.set(0, 1, 0);
		camera.update();
	}
	
	public void rotateCamera(float yRotation)
	{
		camera.rotate(Vector3.Y, yRotation);
		camera.update();
	}
	
	/**
	 * For Xbox360 Controller
	 * @param xMultiplier
	 */
	public void updateCameraControllerX(float xMultiplier)
	{
		if(xMultiplier > 0f)
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.rotate(Vector3.Y, -90);
			v.x *= (MOVEMENT_SPEED * xMultiplier) * Gdx.graphics.getDeltaTime();
			v.z *= (MOVEMENT_SPEED * xMultiplier) * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		else
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.rotate(Vector3.Y, 90);
			v.x *= (MOVEMENT_SPEED * (xMultiplier * -1f)) * Gdx.graphics.getDeltaTime();
			v.z *= (MOVEMENT_SPEED * (xMultiplier * -1f)) * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		
		camera.position.x = WorldHandler.inst.getBodyX();
		camera.position.z = WorldHandler.inst.getBodyY();
		camera.update();
	}
	
	/**
	 * For Xbox360 Controller
	 * @param yMultiplier
	 */
	public void updateCameraControllerY(float yMultiplier)
	{
		if(yMultiplier < 0f)
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.x *= (MOVEMENT_SPEED * (yMultiplier * -1f)) * Gdx.graphics.getDeltaTime();
			v.z *= (MOVEMENT_SPEED * (yMultiplier * -1f)) * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		else
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.x = -v.x;
			v.z = -v.z;
			v.x *= (MOVEMENT_SPEED * yMultiplier) * Gdx.graphics.getDeltaTime();
			v.z *= (MOVEMENT_SPEED * yMultiplier) * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		
		camera.position.x = WorldHandler.inst.getBodyX();
		camera.position.z = WorldHandler.inst.getBodyY();
		camera.update();
	}
	
	/**
	 * For WASD Input
	 */
	private void updateCameraMovement()
	{
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.x *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			v.z *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.x = -v.x;
			v.z = -v.z;
			v.x *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			v.z *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.rotate(Vector3.Y, -90);
			v.x *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			v.z *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			Vector3 v = camera.direction.cpy();
			v.y = 0f;
			v.rotate(Vector3.Y, 90);
			v.x *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			v.z *= MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
			WorldHandler.inst.moveBody(v.x, v.z);
		}
		
		camera.position.x = WorldHandler.inst.getBodyX();
		camera.position.z = WorldHandler.inst.getBodyY();
		camera.update();
	}
	
	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		BoxesHandler.inst.dispose();
		WorldHandler.inst.dispose();
	}
}
