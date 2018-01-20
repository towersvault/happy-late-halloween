package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Curvature;
import com.bitfire.postprocessing.filters.Combine;
import com.bitfire.postprocessing.filters.CrtScreen;
import com.bitfire.utils.ShaderLoader;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.MathHelper;

public class Scene2DCrt
{
	public static final Scene2DCrt inst = new Scene2DCrt();
	
	private Stage stage;
	private Stack stack;
	
	private PostProcessor postProcessor;
	private CrtMonitor crtMonitor;
	private Curvature curvature;
	
	private Image imgBurger;
	private Image imgBg;
	
	private boolean catchingMouseInput = true;
	
	private float crtDelta = 0f;
	
	public void init()
	{
		ShaderLoader.BasePath = "shaders/";
		postProcessor = new PostProcessor(false, true, true);
		
		int effects = CrtScreen.Effect.TweakContrast.v | CrtScreen.Effect.PhosphorVibrance.v | CrtScreen.Effect.Scanlines.v | CrtScreen.Effect.Tint.v;
		crtMonitor = new CrtMonitor(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(),
				false,
				false,
				CrtScreen.RgbMode.RgbShift,
				effects);
		Combine combine = crtMonitor.getCombinePass();
		combine.setSource1Intensity(0f);
		combine.setSource2Intensity(1f);
		combine.setSource1Saturation(0f);
		combine.setSource2Saturation(1f);
		
		curvature = new Curvature();
		curvature.setZoom(1f);
		
		crtMonitor.enableBlending(GL20.GL_SRC_COLOR, GL20.GL_ONE_MINUS_SRC_COLOR);
		
		postProcessor.addEffect(curvature);
		postProcessor.addEffect(crtMonitor);
		
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stack = new Stack();
		
		stage.clear();
		stack.clear();
		
		stage.addActor(stack);
		stack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Table layer = new Table();
		
		layer.setLayoutEnabled(false);
		
		imgBg = new Image(Scene2DHelper.inst.skin, "ui_background_2");
		layer.add(imgBg);
		imgBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		imgBurger = new Image(Scene2DHelper.inst.skin, "item_burger");
		imgBurger.setSize(imgBurger.getWidth() * Constants.resize(), imgBurger.getHeight() * Constants.resize());
		imgBurger.setX(Gdx.graphics.getWidth() / 2f);
		imgBurger.setY(Gdx.graphics.getHeight() / 2f);
		/*layer.add(imgBurger);*/
		
		Image imgLetterBox1 = new Image(Scene2DHelper.inst.skin, "ui_dock");
		imgLetterBox1.setSize(Gdx.graphics.getWidth(), imgLetterBox1.getHeight() * Constants.resize());
		imgLetterBox1.setX(0f);
		imgLetterBox1.setY(0f);
		layer.add(imgLetterBox1);
		
		Image imgLetterBox2 = new Image(Scene2DHelper.inst.skin, "ui_dock");
		imgLetterBox2.setSize(Gdx.graphics.getWidth(), imgLetterBox2.getHeight() * Constants.resize());
		imgLetterBox2.setX(0f);
		imgLetterBox2.setY(Gdx.graphics.getHeight() - imgLetterBox2.getHeight());
		layer.add(imgLetterBox2);
		
		Image imgHearts[] = new Image[3];
		for(int i = 0; i < imgHearts.length; i++)
		{
			imgHearts[i] = new Image(Scene2DHelper.inst.skin, "ui_heart_full");
			layer.add(imgHearts[i]);
			imgHearts[i].setSize(imgHearts[i].getWidth() * Constants.resize(), imgHearts[i].getHeight() * Constants.resize());
			imgHearts[i].setY(Gdx.graphics.getHeight() - imgHearts[i].getHeight() * 2f - imgLetterBox2.getHeight());
			imgHearts[i].setX(imgHearts[i].getWidth() + (imgHearts[i].getWidth() * i) + MathHelper.inst.pxToScreen(4f + (4f * i)));
		}
		
		Image imgInvento = new Image(Scene2DHelper.inst.skin, "inventomatic2500");
		layer.add(imgInvento);
		imgInvento.setSize(imgInvento.getWidth() * Constants.resize(), imgInvento.getHeight() * Constants.resize());
		imgInvento.setY(Gdx.graphics.getHeight() - imgInvento.getHeight() - MathHelper.inst.pxToScreen(2f));
		imgInvento.setX(Gdx.graphics.getWidth() / 2f - imgInvento.getWidth() / 2f);
		
		/*Image imgMsg = new Image(Scene2DHelper.inst.skin, "ui_messaging_mockup");
		layer.add(imgMsg);
		imgMsg.setSize(imgMsg.getWidth() * Constants.resize(), imgMsg.getHeight() * Constants.resize());
		imgMsg.setY(Gdx.graphics.getHeight() / 2f - imgMsg.getHeight() / 2f);
		imgMsg.setX(Gdx.graphics.getWidth() / 2f - imgMsg.getWidth() / 2f);*/
		
		Image imgSelected = new Image(Scene2DHelper.inst.skin, "ui_selected_tab");
		layer.add(imgSelected);
		imgSelected.setSize(imgSelected.getWidth() * Constants.resize(), imgSelected.getHeight() * Constants.resize());
		
		Image imgSelected2 = new Image(Scene2DHelper.inst.skin, "ui_selected_tab");
		//layer.add(imgSelected2);
		imgSelected2.setSize(imgSelected2.getWidth() * Constants.resize(), imgSelected2.getHeight() * Constants.resize());
		
		Image imgInventory = new Image(Scene2DHelper.inst.skin, "ui_icon_inventory");
		layer.add(imgInventory);
		imgInventory.setSize(imgInventory.getWidth() * Constants.resize(), imgInventory.getHeight() * Constants.resize());
		imgInventory.setX(Gdx.graphics.getWidth() - imgSelected.getWidth() * 3f);
		imgInventory.setY(imgHearts[0].getY() - MathHelper.inst.pxToScreen(2f));
		
		Image imgChat = new Image(Scene2DHelper.inst.skin, "ui_icon_chat");
		layer.add(imgChat);
		imgChat.setSize(imgChat.getWidth() * Constants.resize(), imgChat.getHeight() * Constants.resize());
		imgChat.setX(imgInventory.getX() + imgSelected.getWidth());
		imgChat.setY(imgInventory.getY());
		
		imgSelected.setX(imgInventory.getX() - MathHelper.inst.pxToScreen(2f));
		imgSelected.setY(imgInventory.getY() - MathHelper.inst.pxToScreen(2f));
		
		imgSelected2.setX(imgChat.getX() - MathHelper.inst.pxToScreen(2f));
		imgSelected2.setY(imgChat.getY() - MathHelper.inst.pxToScreen(2f));
		
		Label lblView = new Label("Inventory", Scene2DHelper.inst.skin, "default");
		layer.add(lblView);
		lblView.getStyle().font.getData().setScale(Constants.resize());
		
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(lblView.getStyle().font, lblView.getText().toString());
		lblView.setX(Gdx.graphics.getWidth() / 4f - glyphLayout.width / 2f);
		lblView.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(4f));
		
		Label lblName = new Label("Burger", Scene2DHelper.inst.skin, "smaller");
		layer.add(lblName);
		lblName.getStyle().font.getData().setScale(Constants.resize());
		lblName.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblName.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(10f));
		
		Label lblExplanation = new Label("A meaty meat disc", Scene2DHelper.inst.skin, "smaller");
		layer.add(lblExplanation);
		lblExplanation.getStyle().font.getData().setScale(Constants.resize());
		
		lblExplanation.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblExplanation.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(26f));
		
		Label lblExp1 = new Label("encased from top to", Scene2DHelper.inst.skin, "smaller");
		layer.add(lblExp1);
		lblExp1.getStyle().font.getData().setScale(Constants.resize());
		
		lblExp1.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblExp1.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(33f));
		
		Label lblExp2 = new Label("bottom by the world's", Scene2DHelper.inst.skin, "smaller");
		layer.add(lblExp2);
		lblExp2.getStyle().font.getData().setScale(Constants.resize());
		
		lblExp2.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblExp2.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(40f));
		
		Label lblExp3 = new Label("softest bun(tm)", Scene2DHelper.inst.skin, "smaller");
		layer.add(lblExp3);
		lblExp3.getStyle().font.getData().setScale(Constants.resize());
		
		lblExp3.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblExp3.setY(imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(47f));
		
		
		Image imgItemSelect = new Image(Scene2DHelper.inst.skin, "ui_selected_item");
		layer.add(imgItemSelect);
		imgItemSelect.setSize(imgItemSelect.getWidth() * Constants.resize(), imgItemSelect.getHeight() * Constants.resize());
		
		Image[][] imgItems = new Image[3][3];
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				imgItems[y][x] = new Image(Scene2DHelper.inst.skin, "item_burger");
				layer.add(imgItems[y][x]);
				imgItems[y][x].setSize(imgItems[y][x].getWidth() * Constants.resize(), imgItems[y][x].getHeight() * Constants.resize());
				imgItems[y][x].setX((Gdx.graphics.getWidth() / 4f - glyphLayout.width / 2f) + imgItems[y][x].getWidth() * (float)x + MathHelper.inst.pxToScreen(4f * (float)(x + 1)));
				imgItems[y][x].setY((imgHearts[0].getY() - glyphLayout.height - MathHelper.inst.pxToScreen(24f)) - imgItems[y][x].getHeight() * (float)y - MathHelper.inst.pxToScreen(4f * (float)(y + 1)));
			}
		}
		
		imgItemSelect.setX(imgItems[0][1].getX() - MathHelper.inst.pxToScreen(1f));
		imgItemSelect.setY(imgItems[0][1].getY() - MathHelper.inst.pxToScreen(1f));
		
		Image imgUse = new Image(Scene2DHelper.inst.skin, "ui_use_highlight");
		layer.add(imgUse);
		imgUse.setSize(imgUse.getWidth() * Constants.resize(), imgUse.getHeight() * Constants.resize());
		imgUse.setX(lblName.getX());
		imgUse.setY(imgLetterBox2.getHeight() * 2f);
		
		Image imgDestroy = new Image(Scene2DHelper.inst.skin, "ui_destroy_highlight");
		layer.add(imgDestroy);
		imgDestroy.setSize(imgDestroy.getWidth() * Constants.resize(), imgDestroy.getHeight() * Constants.resize());
		imgDestroy.setX(imgUse.getX() + imgUse.getWidth() + MathHelper.inst.pxToScreen(4f));
		imgDestroy.setY(imgUse.getY());
		
		stack.add(layer);
	}
	
	public boolean catchingCursor()
	{
		return catchingMouseInput;
	}
	
	public void render(float delta)
	{
		if(crtDelta == 0.01f)
			crtDelta = 0.1f;
		else
			crtDelta = 0.01f;
		
		stage.act(delta);
		crtMonitor.setTime(crtDelta);
		
		postProcessor.capture();
		stage.draw();
		postProcessor.render();
		
		if(imgBg.isVisible() && catchingMouseInput)
		{
			catchingMouseInput = false;
			Gdx.input.setCursorCatched(false);
		}
		else if(!imgBg.isVisible() && !catchingMouseInput)
		{
			catchingMouseInput = true;
			Gdx.input.setCursorCatched(true);
		}
	}
}
