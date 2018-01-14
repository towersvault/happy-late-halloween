package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
		
		Image imgBg = new Image(Scene2DHelper.inst.skin, "ui_background_2");
		layer.add(imgBg);
		imgBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		imgBurger = new Image(Scene2DHelper.inst.skin, "item_burger");
		imgBurger.setSize(imgBurger.getWidth() * Constants.resize(), imgBurger.getHeight() * Constants.resize());
		imgBurger.setX(Gdx.graphics.getWidth() / 2f);
		imgBurger.setY(Gdx.graphics.getHeight() / 2f);
		layer.add(imgBurger);
		
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
		
		stack.add(layer);
	}
	
	public void render(float delta)
	{
		stage.act(delta);
		crtMonitor.setTime(delta);
		
		postProcessor.capture();
		stage.draw();
		postProcessor.render();
	}
}
