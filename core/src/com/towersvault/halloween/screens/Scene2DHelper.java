package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
	
	private Image imgBackground;
	
	public void init()
	{
		skin = new Skin(Gdx.files.internal(Constants.SPRITES_JSON),
				new TextureAtlas(Gdx.files.internal(Constants.SPRITES_ATLAS)));
	}
	
	public Table buildInventoryIcons()
	{
		layerInventory = new Table();
		
		layerInventory.setLayoutEnabled(false);
		
		imgBackground = new Image(skin, "ui_background");
		layerInventory.add(imgBackground);
		imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		imgBackground.setColor(imgBackground.getColor().r, imgBackground.getColor().g, imgBackground.getColor().b, 0.9f);
		
		return layerInventory;
	}
	
	public void toggleBackground()
	{
		imgBackground.setVisible(!imgBackground.isVisible());
	}
}
