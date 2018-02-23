package com.towersvault.halloween.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.towersvault.halloween.world.BoxesHandler.BoxType;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import javax.swing.*;

public class MapLoader
{
	public static final MapLoader inst = new MapLoader();
	
	public enum Map
	{
		WORLD1
	}
	
	private TiledMap tiledMap;
	
	private float mapHeight = 0f;
	private float mapWidth = 0f;
	
	public void loadMap(Map map)
	{
		try
		{
			tiledMap.dispose();
		}
		catch(Exception e)
		{
			
		}
		
		tiledMap = new TmxMapLoader().load("maps/wicker.tmx");
		
		TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
		Cell cell;
		
		mapHeight = layer.getHeight();
		mapWidth = layer.getWidth();
		
		String[][] collisionMap0 = new String[layer.getHeight()][layer.getWidth()];
		String[][] collisionMap1 = new String[layer.getHeight()][layer.getWidth()];
		
		Array<TileData> tileData = new Array<TileData>();
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get(4);
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				try
				{
					if(cell.getTile().getProperties().containsKey("1"))
						tileData.add(new TileData(1, x, 0, y));
					else if(cell.getTile().getProperties().containsKey("2"))
						tileData.add(new TileData(2, x, 0, y));
					else if(cell.getTile().getProperties().containsKey("3"))
						tileData.add(new TileData(3, x, 0, y));
				}
				catch(Exception e) {}
			}
		}
		LightRenderer.inst.setTileData(tileData);
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				collisionMap0[y][x] = "";
				
				try
				{
					if(cell.getTile().getProperties().containsKey("wall"))
					{
						//BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.WALL, true, x, y, 0f);
						collisionMap0[y][x] = "W";
					}
					else if(cell.getTile().getProperties().containsKey("floor"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.FLOOR, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("bush"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.BUSH, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("sidewalk"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SIDEWALK, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("road"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.ROAD, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("grass"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.GRASS, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("fire_hydrant"))
						BoxesHandler.inst.loadBox(null, BoxType.FIRE_HYDRANT, false, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("stop_n"))
						BoxesHandler.inst.loadBox(null, BoxType.STOP_N, false, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("stop_s"))
						BoxesHandler.inst.loadBox(null, BoxType.STOP_S, false, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("stop_e"))
						BoxesHandler.inst.loadBox(null, BoxType.STOP_E, false, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("stop_w"))
						BoxesHandler.inst.loadBox(null, BoxType.STOP_W, false, x, y, 0f);
					
					else if(cell.getTile().getProperties().containsKey("fence_tl"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_TL, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_t"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_T, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_tr"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_TR, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_cl"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_CL, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_cr"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_CR, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_bl"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_BL, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_b"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_B, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("fence_br"))
						BoxesHandler.inst.loadBox(null, BoxType.FENCE_BR, true, x, y, 0f);
					
					else if(cell.getTile().getProperties().containsKey("corn"))
						BoxesHandler.inst.loadBox(null, BoxType.CORN, true, x, y, 0f);
					else if(cell.getTile().getProperties().containsKey("ol pump"))
						BoxesHandler.inst.loadBox(null, BoxType.SCARECROW, true, x, y, 0f);
					
					else if(cell.getTile().getProperties().containsKey("sliding_door"))
					{
						if(cell.getTile().getProperties().containsKey("n"))
							BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SLIDING_DOOR_N, true, x, y, 0f);
						else if(cell.getTile().getProperties().containsKey("e"))
							BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SLIDING_DOOR_E, true, x, y, 0f);
						else if(cell.getTile().getProperties().containsKey("s"))
							BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SLIDING_DOOR_S, true, x, y, 0f);
						else if(cell.getTile().getProperties().containsKey("w"))
							BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SLIDING_DOOR_W, true, x, y, 0f);
						else
							BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.SLIDING_DOOR, true, x, y, 0f);
					}
					
					else if(cell.getTile().getProperties().containsKey("water"))
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.WATER, true, x, y, 0f);

					else if(cell.getTile().getProperties().containsKey("crate"))
						BoxesHandler.inst.loadBox(null, BoxType.CRATE, true, x, y, 0f);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				collisionMap1[y][x] = "";
				
				try
				{
					if(cell.getTile().getProperties().containsKey("ceiling"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.CEILING, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("wall"))
					{
						if(cell.getTile().getProperties().containsKey("shop"))
							collisionMap1[y][x] = "WS";
						else
							collisionMap1[y][x] = "W";
					}
					else if(cell.getTile().getProperties().containsKey("grass_side"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.GRASS_SIDE, false, x, y, 0f);
					}
					
					else if(cell.getTile().getProperties().containsKey("vending"))
						BoxesHandler.inst.loadBox(null, BoxType.VENDING_MACHINE, false, x, y, 0f);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get(2);
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				try
				{
					if(cell.getTile().getProperties().containsKey("roof"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.ROOF, false, x, y, 0f);
					}
				}
				catch(Exception e)
				{
				
				}
			}
		}
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get(3);
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				try
				{
					if(cell.getTile().getProperties().containsKey("burger"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.ITEM_BURGER, false, x, y, 0f);
					}
				}
				catch(Exception e)
				{
				
				}
			}
		}
		
		BoxesHandler.inst.optimizeBoxes(collisionMap0, collisionMap1);
		BoxesHandler.inst.loadWallBoxes(collisionMap0, collisionMap1, layer.getWidth(), layer.getHeight());
		BoxesHandler.inst.updateShadows();
		
		//BoxesHandler.inst.printHash();
		
		WorldHandler.inst.setCollsionMap(collisionMap0);
	}
	
	public TextureRegion getTexture(int layer, int x, int y)
	{
		TiledMapTileLayer mapLayer = (TiledMapTileLayer)tiledMap.getLayers().get(layer);
		return mapLayer.getCell(x, y).getTile().getTextureRegion();
	}
	
	public float getMapHeight()
	{
		return mapHeight;
	}
	
	public float getMapWidth()
	{
		return mapWidth;
	}
}
