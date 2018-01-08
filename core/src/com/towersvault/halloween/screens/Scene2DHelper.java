package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.MathHelper;

public class Scene2DHelper
{
	public static final Scene2DHelper inst = new Scene2DHelper();
	
	public Skin skin;
	
	// Inventory Icons
	private Table layerInventory;
	private Label lblItemQty;
	private Image imgItemIcon;
	private Image imgItemLeft, imgItemRight;
	
	public void init()
	{
		skin = new Skin(Gdx.files.internal(Constants.SPRITES_JSON),
				new TextureAtlas(Gdx.files.internal(Constants.SPRITES_ATLAS)));
	}
	
	public Table buildInventoryIcons()
	{
		layerInventory = new Table();
		
		layerInventory.setLayoutEnabled(false);
		
		imgItemIcon = new Image(skin, "item_burger");
		layerInventory.add(imgItemIcon);
		imgItemIcon.setSize(imgItemIcon.getWidth() * Constants.resize(), imgItemIcon.getHeight() * Constants.resize());
		imgItemIcon.setY(Gdx.graphics.getHeight() - imgItemIcon.getHeight() - MathHelper.inst.pxToScreen(4f));
		imgItemIcon.setX(Gdx.graphics.getWidth() - imgItemIcon.getWidth() - MathHelper.inst.pxToScreen(4f));
		
		return layerInventory;
	}
}
