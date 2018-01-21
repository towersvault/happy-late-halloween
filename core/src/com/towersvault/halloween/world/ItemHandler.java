package com.towersvault.halloween.world;

public class ItemHandler
{
	public static final ItemHandler inst = new ItemHandler();
	
	public enum Item
	{
		COLA("item_cola", "Cola", "Refreshingly loaded with sugars and colourants to give you that extra boost."),
		BURGER("item_burger", "Burger", "A meaty meat disc encased by the world's softest bun."),
		BLANK("item_blank", "", "");
		
		public String drawable;
		public String name;
		public String description;
		
		Item(String drawable, String name, String description)
		{
			this.drawable = drawable;
			this.name = name;
			this.description = description;
		}
	}
	
	/**
	 * 0 -> Cola
	 * 1 -> Burger
	 */
	private Item[][] inventory = {{Item.BURGER, Item.BLANK, Item.BLANK},
			{Item.BLANK, Item.BLANK, Item.COLA},
			{Item.BLANK, Item.BLANK, Item.BLANK}};
	
	/**
	 * Used only by dropped items.
	 * @param item - Enum of the item.
	 * @return true if space in inventory.
	 */
	public boolean inventoryAdd(Item item)
	{
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				if(inventory[y][x].equals(Item.BLANK))
				{
					inventory[y][x] = item;
					return true;
				}
			}
		}
		return false;
	}
	
	public void inventoryAdd(Item item, int x, int y)
	{
		inventory[y][x] = item;
	}
	
	public void inventoryRemove(int x, int y)
	{
	
	}
	
	public Item getItemAt(int x, int y)
	{
		return inventory[y][x];
	}
	
	public void createItem(Item item, int x, int z)
	{
		WorldHandler.inst.createInteractionBody(x, z, item);
		BoxesHandler.inst.createItemDecal(x, z, item);
	}
	
	public void destroyItem(int x, int z)
	{
		BoxesHandler.inst.destroyItemDecal(x, z);
	}
	
}
