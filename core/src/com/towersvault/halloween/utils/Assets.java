package com.towersvault.halloween.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener
{
	public static final Assets inst = new Assets();
	
	private AssetManager am;
	
	public StaticSprite staticSprite;
	public ItemSprite itemSprite;
	public EntitySprite entitySprite;
	
	public class StaticSprite
	{
		public AtlasRegion moon;
		public AtlasRegion grassBlades;
		public AtlasRegion grassSide;
		public AtlasRegion pavementSide;
		public AtlasRegion bush;
		public AtlasRegion fireHydrant;
		public AtlasRegion floorGrass;
		public AtlasRegion stopFront;
		public AtlasRegion stopBack;
		public AtlasRegion stopBoxBack;
		public AtlasRegion stopBoxSide;
		public AtlasRegion fence;
		public AtlasRegion vendingMachineFront;
		public AtlasRegion vendingMachineSide;
		
		public AtlasRegion farmland;
		public AtlasRegion corn;
		public AtlasRegion scarecrow;
		public AtlasRegion scarecrowOn;
		
		public AtlasRegion brickWall;
		public AtlasRegion carpet;
		
		public AtlasRegion slidingDoorLeft;
		public AtlasRegion slidingDoorRight;

		public AtlasRegion crateExterior;
		public AtlasRegion crateInterior;

		public AtlasRegion debugX, debugZ;

		public AtlasRegion roof, roofEdgeLeft, roofEdgeRight;
		
		public StaticSprite(TextureAtlas atlas)
		{
			moon = atlas.findRegion("static_moon");
			grassBlades = atlas.findRegion("static_grass_blades");
			grassSide = atlas.findRegion("static_grass_side");
			pavementSide = atlas.findRegion("static_side_walk");
			bush = atlas.findRegion("static_bush");
			fireHydrant = atlas.findRegion("static_fire_hydrant");
			floorGrass = atlas.findRegion("static_floor_grass");
			stopFront = atlas.findRegion("static_stop_front");
			stopBack = atlas.findRegion("static_stop_back");
			stopBoxBack = atlas.findRegion("static_stop_box_back");
			stopBoxSide = atlas.findRegion("static_stop_box_side");
			fence = atlas.findRegion("static_fence");
			vendingMachineFront = atlas.findRegion("static_vending_machine_front");
			vendingMachineSide = atlas.findRegion("static_vending_machine_side");
			
			farmland = atlas.findRegion("static_farmland");
			corn = atlas.findRegion("static_corn");
			scarecrow = atlas.findRegion("static_scarecrow");
			scarecrowOn = atlas.findRegion("static_scarecrow_on");
			
			brickWall = atlas.findRegion("static_brick_wall");
			carpet = atlas.findRegion("static_carpet");
			
			slidingDoorLeft = atlas.findRegion("static_sliding_door_left");
			slidingDoorRight = atlas.findRegion("static_sliding_door_right");

			crateExterior = atlas.findRegion("static_crate_exterior");
			crateInterior = atlas.findRegion("static_crate_interior");

			debugX = atlas.findRegion("debug_x");
			debugZ = atlas.findRegion("debug_z");

			roof = atlas.findRegion("static_roof");
		}
	}
	
	public class ItemSprite
	{
		public AtlasRegion burger;
		
		public ItemSprite(TextureAtlas atlas)
		{
			burger = atlas.findRegion("item_burger");
		}
	}
	
	public class EntitySprite
	{
		public AtlasRegion planeSide;
		public AtlasRegion planeWingFront;
		public AtlasRegion planeWingBack;
		public AtlasRegion planeWingVert;
		public AtlasRegion planeProp;
		public AtlasRegion planeFront;
		public AtlasRegion planeBack;
		public AtlasRegion planeShadow;
		public AtlasRegion bomb;
		public AtlasRegion skeletonIdle;
		
		public EntitySprite(TextureAtlas atlas)
		{
			planeSide = atlas.findRegion("plane_side");
			planeWingFront = atlas.findRegion("plane_wing");
			planeWingBack = atlas.findRegion("plane_wing_rear");
			planeWingVert = atlas.findRegion("plane_wing_vert");
			planeProp = atlas.findRegion("plane_propeller");
			planeFront = atlas.findRegion("plane_front");
			planeBack = atlas.findRegion("plane_back");
			planeShadow = atlas.findRegion("plane_shadow");
			bomb = atlas.findRegion("entity_bomb");
			skeletonIdle = atlas.findRegion("entity_skeleton_idle");
		}
	}
	
	public void init()
	{
		this.am = new AssetManager();
		
		am.setErrorListener(this);
		am.load(Constants.SPRITES_ATLAS, TextureAtlas.class);
		am.finishLoading();
		
		TextureAtlas atlas = am.get(Constants.SPRITES_ATLAS);
		
		for(Texture t : atlas.getTextures())
			t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		
		staticSprite = new StaticSprite(atlas);
		itemSprite = new ItemSprite(atlas);
		entitySprite = new EntitySprite(atlas);
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable)
	{
	
	}
	
	@Override
	public void dispose()
	{
		am.dispose();
	}
}
