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
		
		/*Image imgDock = new Image(skin, "ui_dock");
		layerInventory.add(imgDock);
		imgDock.setSize(Gdx.graphics.getWidth(), imgDock.getHeight() * Constants.resize());
		imgDock.setX(0f);
		imgDock.setY(0f);
		
		imgItemIcon = new Image(skin, "item_burger");
		layerInventory.add(imgItemIcon);
		imgItemIcon.setSize(imgItemIcon.getWidth() * Constants.resize(), imgItemIcon.getHeight() * Constants.resize());
		imgItemIcon.setY(MathHelper.inst.pxToScreen(2f));
		imgItemIcon.setX(MathHelper.inst.pxToScreen(2f));*/
		
		/*Image imgLetterBox1 = new Image(skin, "ui_dock");
		layerInventory.add(imgLetterBox1);
		imgLetterBox1.setSize(Gdx.graphics.getWidth(), imgLetterBox1.getHeight() * Constants.resize());
		imgLetterBox1.setX(0f);
		imgLetterBox1.setY(0f);
		
		Image imgLetterBox2 = new Image(skin, "ui_dock");
		layerInventory.add(imgLetterBox2);
		imgLetterBox2.setSize(Gdx.graphics.getWidth(), imgLetterBox2.getHeight() * Constants.resize());
		imgLetterBox2.setX(0f);
		imgLetterBox2.setY(Gdx.graphics.getHeight() - imgLetterBox2.getHeight());
		
		Image imgHearts[] = new Image[3];
		for(int i = 0; i < imgHearts.length; i++)
		{
			imgHearts[i] = new Image(skin, "ui_heart_full");
			layerInventory.add(imgHearts[i]);
			imgHearts[i].setSize(imgHearts[i].getWidth() * Constants.resize(), imgHearts[i].getHeight() * Constants.resize());
			imgHearts[i].setY(Gdx.graphics.getHeight() - imgHearts[i].getHeight() * 2f - imgLetterBox2.getHeight());
			imgHearts[i].setX(imgHearts[i].getWidth() + (imgHearts[i].getWidth() * i) + MathHelper.inst.pxToScreen(4f + (4f * i)));
		}*/
		
		Image imgBg = new Image(skin, "ui_background");
		layerInventory.add(imgBg);
		imgBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		imgBg.setColor(imgBg.getColor().r, imgBg.getColor().g, imgBg.getColor().b, 0.9f);
		
		return layerInventory;
	}
}
