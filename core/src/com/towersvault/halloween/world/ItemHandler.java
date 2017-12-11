package com.towersvault.halloween.world;

public class ItemHandler
{
	public static final ItemHandler inst = new ItemHandler();
	
	public enum Item
	{
		COLA,
		BURGER
	}
	
	/**
	 * 0 -> Cola
	 * 1 -> Burger
	 */
	private int inventory[] = new int[2];
	
	public void inventoryAdd(Item item)
	{
		inventoryTranslateAmount(item, 1);
	}
	
	public void inventoryRemove(Item item)
	{
		inventoryTranslateAmount(item, -1);
	}
	
	public void createItem(Item item, int x, int z)
	{
		WorldHandler.inst.createInteractionBody(x, z);
		BoxesHandler.inst.createItemDecal(x, z, item);
	}
	
	public void destroyItem(int x, int z)
	{
		BoxesHandler.inst.destroyItemDecal(x, z);
	}
	
	private void inventoryTranslateAmount(Item item, int translateBy)
	{
		switch(item)
		{
			case COLA:
				inventory[0] += translateBy;
				break;
			case BURGER:
				inventory[1] += translateBy;
				break;
			default:
				break;
		}
	}
}
