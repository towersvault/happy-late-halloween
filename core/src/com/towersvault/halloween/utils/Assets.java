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
