package com.towersvault.halloween.screens;

import com.aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.MathHelper;
import com.towersvault.halloween.utils.tween.ActorAccessor;
import com.towersvault.halloween.utils.tween.TweenHandler;
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
	private Image imgSpace;
	private boolean dialogueRunning = false;
	
	public void init()
	{
		skin = new Skin(Gdx.files.internal(Constants.SPRITES_JSON),
				new TextureAtlas(Gdx.files.internal(Constants.SPRITES_ATLAS)));
		
		TweenHandler.inst.init();
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
		
		debugOutput.setVisible(false);
		
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
		//imgLetterbox[0].setY(Gdx.graphics.getHeight() - imgLetterbox[0].getHeight()); // Not visible at this state.
		imgLetterbox[0].setY(Gdx.graphics.getHeight());
		
		imgLetterbox[1] = new Image(skin, "ui_background_3");
		layer.add(imgLetterbox[1]);
		imgLetterbox[1].setHeight(imgLetterbox[0].getHeight());
		imgLetterbox[1].setWidth(imgLetterbox[0].getWidth());
		//imgLetterbox[1].setY(0f);
		imgLetterbox[1].setY(-imgLetterbox[1].getHeight());
		
		lblOutput = new Label("", skin, "dialogue");
		layer.add(lblOutput);
		lblOutput.getStyle().font.getData().setScale(Constants.resize() / 2f);
		
		glyphOutput = new GlyphLayout();
		glyphOutput.setText(lblOutput.getStyle().font, lblOutput.getText().toString());
		lblOutput.setY(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f - imgLetterbox[0].getHeight());
		lblOutput.setX(Gdx.graphics.getWidth() / 2f - glyphOutput.width / 2f);
		
		imgSpace = new Image(skin, "ui_press_space");
		layer.add(imgSpace);
		imgSpace.setHeight(imgSpace.getHeight() * (Constants.resize() / 2f));
		imgSpace.setWidth(imgSpace.getWidth() * (Constants.resize() / 2f));
		imgSpace.setX(Gdx.graphics.getWidth() / 2f + glyphOutput.width / 2f);
		imgSpace.setY(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f - imgLetterbox[1].getHeight() - MathHelper.inst.pxToScreen(1.5f));
		
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
		imgSpace.setVisible(false);
	}
	
	public boolean dialogueOutputFinished()
	{
		return output.equals(lblOutput.getText().toString());
	}
	
	public boolean isDialogueRunning()
	{
		return dialogueRunning;
	}
	
	public void toggleDialogue(boolean visible)
	{
		dialogueRunning = visible;
		
		if(visible)
		{
			lblOutput.setText("");
			glyphOutput.setText(lblOutput.getStyle().font, "Text");
			
			lblOutput.setY(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f - imgLetterbox[1].getHeight());
			
			Tween.to(lblOutput, ActorAccessor.Y_TRANSLATE, 1f)
					.target(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f)
					.start(TweenHandler.inst.manager);
			
			imgLetterbox[0].setY(Gdx.graphics.getHeight());
			Tween.to(imgLetterbox[0], ActorAccessor.Y_TRANSLATE, 1f)
					.target(Gdx.graphics.getHeight() - imgLetterbox[0].getHeight())
					.start(TweenHandler.inst.manager);
			
			imgLetterbox[1].setY(-imgLetterbox[1].getHeight());
			Tween.to(imgLetterbox[1], ActorAccessor.Y_TRANSLATE, 1f)
					.target(0f)
					.start(TweenHandler.inst.manager);
			
			imgSpace.setY(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f - imgLetterbox[1].getHeight() - MathHelper.inst.pxToScreen(1.5f));
			Tween.to(imgSpace, ActorAccessor.Y_TRANSLATE, 1f)
					.target(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f - MathHelper.inst.pxToScreen(1.5f))
					.start(TweenHandler.inst.manager);
			imgSpace.setVisible(false);
		}
		else
		{
			glyphOutput.setText(lblOutput.getStyle().font, "Text");
			lblOutput.setY(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f);
			
			Tween.to(lblOutput, ActorAccessor.Y_TRANSLATE, 1f)
					.target(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f - imgLetterbox[1].getHeight())
					.start(TweenHandler.inst.manager);
			
			imgLetterbox[0].setY(Gdx.graphics.getHeight() - imgLetterbox[0].getHeight());
			Tween.to(imgLetterbox[0], ActorAccessor.Y_TRANSLATE, 1f)
					.target(Gdx.graphics.getHeight())
					.start(TweenHandler.inst.manager);
			
			imgLetterbox[1].setY(0f);
			Tween.to(imgLetterbox[1], ActorAccessor.Y_TRANSLATE, 1f)
					.target(-imgLetterbox[1].getHeight())
					.start(TweenHandler.inst.manager);
			
			imgSpace.setY(MathHelper.inst.pxToScreen(18f) / 2f - glyphOutput.height / 2f - MathHelper.inst.pxToScreen(1.5f));
			Tween.to(imgSpace, ActorAccessor.Y_TRANSLATE, 1f)
					.target(MathHelper.inst.pxToScreen(20f) / 2f - glyphOutput.height / 2f - imgLetterbox[1].getHeight() - MathHelper.inst.pxToScreen(1.5f))
					.start(TweenHandler.inst.manager);
		}
	}
	
	public void toggleBackground()
	{
		imgBackground.setVisible(!imgBackground.isVisible());
	}
	
	public void update()
	{
		TweenHandler.inst.update(Gdx.graphics.getDeltaTime());
		
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
				imgSpace.setX(Gdx.graphics.getWidth() / 2f + glyphOutput.width / 2f + MathHelper.inst.pxToScreen(1f));
			}
			else if(lblOutput.getText().toString().length() > 0
					&& output.equals(lblOutput.getText().toString()))
			{
				if(outputDelta > 0.4f)
				{
					imgSpace.setVisible(!imgSpace.isVisible());
					outputDelta = 0f;
				}
			}
		}
	}
}
