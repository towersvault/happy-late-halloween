package com.towersvault.halloween.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.towersvault.halloween.world.BoxesHandler.BoxType;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapLoader
{
	public static final MapLoader inst = new MapLoader();
	
	public enum Map
	{
		WORLD1
	}
	
	private TiledMap tiledMap;
	
	public void loadMap(Map map)
	{
		try
		{
			tiledMap.dispose();
		}
		catch(Exception e)
		{
			
		}
		
		tiledMap = new TmxMapLoader().load("maps/world1.tmx");
		//WorldHandler.inst.buildMapShapes(tiledMap, "Collision");
		
		TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
		Cell cell;
		
		for(int x = 0; x < layer.getWidth(); x++)
		{
			for(int y = 0; y < layer.getHeight(); y++)
			{
				cell = layer.getCell(x, y);
				
				try
				{
					if(cell.getTile().getProperties().containsKey("wall"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.WALL, true, x, y, 0f);
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
				
				try
				{
					if(cell.getTile().getProperties().containsKey("ceiling"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.CEILING, false, x, y, 0f);
					}
					else if(cell.getTile().getProperties().containsKey("wall"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.WALL, false, x, y, BoxesHandler.TILE_SIZE);
					}
					else if(cell.getTile().getProperties().containsKey("grass_side"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.GRASS_SIDE, false, x, y, 0f);
					}
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
					/*else if(cell.getTile().getProperties().containsKey("chimney"))
					{
						BoxesHandler.inst.loadBox(cell.getTile().getTextureRegion(), BoxType.WALL, false, x, y, BoxesHandler.TILE_SIZE * 2f);
					}*/
				}
				catch(Exception e)
				{
				
				}
			}
		}
		
		BoxesHandler.inst.optimizeBoxes();
	}
}
