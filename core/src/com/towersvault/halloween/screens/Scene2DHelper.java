package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.MathHelper;
import com.towersvault.halloween.world.WorldHandler;

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
	
	private Label debugOutput;
	
	// Dialogue UI
	private Image[] imgLetterbox;
	private Label lblOutput;
	private GlyphLayout glyphOutput;
	private String output = "";
	private float outputDelta = 0f;
	
	public void init()
	{
		skin = new Skin(Gdx.files.internal(Constants.SPRITES_JSON),
				new TextureAtlas(Gdx.files.internal(Constants.SPRITES_ATLAS)));
	}
	
	public Table buildDebug()
	{
		Table layer = new Table();
		
		layer.setLayoutEnabled(false);
		
		debugOutput = new Label("output", skin, "smaller");
		layer.add(debugOutput);
		debugOutput.getStyle().font.getData().setScale(Constants.resize() / 2f);
		debugOutput.setX(MathHelper.inst.pxToScreen(8f));
		debugOutput.setY(MathHelper.inst.pxToScreen(8f));
		
		return layer;
	}
	
	public Table buildDialogue()
	{
		Table layer = new Table();
		
		layer.setLayoutEnabled(false);
		
		imgLetterbox = new Image[2];
		
		imgLetterbox[0] = new Image(skin, "ui_background_3");
		layer.add(imgLetterbox[0]);
		imgLetterbox[0].setHeight(MathHelper.inst.pxToScreen(16f));
		imgLetterbox[0].setWidth(Gdx.graphics.getWidth());
		imgLetterbox[0].setY(Gdx.graphics.getHeight() - imgLetterbox[0].getHeight()); // Not visible at this state.
		
		imgLetterbox[1] = new Image(skin, "ui_background_3");
		layer.add(imgLetterbox[1]);
		imgLetterbox[1].setHeight(imgLetterbox[0].getHeight());
		imgLetterbox[1].setWidth(imgLetterbox[0].getWidth());
		imgLetterbox[1].setY(0f);
		
		lblOutput = new Label("", skin, "dialogue");
		layer.add(lblOutput);
		lblOutput.getStyle().font.getData().setScale(Constants.resize() / 2f);
		
		glyphOutput = new GlyphLayout();
		glyphOutput.setText(lblOutput.getStyle().font, lblOutput.getText().toString());
		lblOutput.setY(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f);
		lblOutput.setX(Gdx.graphics.getWidth() / 2f - glyphOutput.width / 2f);
		
		return layer;
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
	
	public void setDialogueText(String text)
	{
		output = text;
		lblOutput.setText("");
		outputDelta = 0f;
	}
	
	public void toggleBackground()
	{
		imgBackground.setVisible(!imgBackground.isVisible());
	}
	
	public void update()
	{
		debugOutput.setText("ram: " + (Gdx.app.getJavaHeap() / 1000000) + "m x:" + WorldHandler.inst.getBodyTileX() + " z:" + WorldHandler.inst.getBodyTileZ());
		
		if(imgLetterbox[1].getY() >= 0f)
		{
			outputDelta += Gdx.graphics.getDeltaTime();
			
			if(!output.equals(lblOutput.getText().toString())
					&& outputDelta >= 0.1f)
			{
				lblOutput.setText(output.substring(0, lblOutput.getText().toString().length() + 1));
				glyphOutput.setText(lblOutput.getStyle().font, lblOutput.getText().toString());
				lblOutput.setY(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f);
				lblOutput.setX(Gdx.graphics.getWidth() / 2f - glyphOutput.width / 2f);
				outputDelta = 0f;
			}
		}
	}
}
