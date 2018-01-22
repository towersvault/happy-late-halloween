package com.towersvault.halloween.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Curvature;
import com.bitfire.postprocessing.filters.Combine;
import com.bitfire.postprocessing.filters.CrtScreen;
import com.bitfire.utils.ShaderLoader;
import com.towersvault.halloween.render.Renderer;
import com.towersvault.halloween.utils.Constants;
import com.towersvault.halloween.utils.GameData;
import com.towersvault.halloween.utils.MathHelper;
import com.towersvault.halloween.world.ItemHandler;

public class Scene2DCrt
{
	public static final Scene2DCrt inst = new Scene2DCrt();
	
	private Stage stage;
	private Stack stack;
	
	private Table uiLayer;
	
	private PostProcessor postProcessor;
	private CrtMonitor crtMonitor;
	private Curvature curvature;
	
	private Image imgBg;
	
	private Image[][] imgInventoryIcon = new Image[3][3];
	private Button[][] btnInventoryIconOverlay = new Button[3][3];
	private Image imgItemSelect;
	private Label lblItemName;
	private Label[] lblItemDescription = new Label[4];
	private Button btnUse;
	private Button btnDestroy;
	private Image[] imgHearts = new Image[3];
	
	private GlyphLayout glyphItemDescription;
	
	private boolean catchingMouseInput = true;
	
	private float crtDelta = 0f;
	
	private int cursorX = 0;
	private int cursorY = 0;
	
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
		
		Gdx.input.setInputProcessor(stage);
		catchingMouseInput = false;
		
		uiLayer = new Table();
		
		uiLayer.setLayoutEnabled(false);
		
		imgBg = new Image(Scene2DHelper.inst.skin, "ui_background_2");
		uiLayer.add(imgBg);
		imgBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Image imgLetterBox1 = new Image(Scene2DHelper.inst.skin, "ui_dock");
		imgLetterBox1.setSize(Gdx.graphics.getWidth(), imgLetterBox1.getHeight() * Constants.resize());
		imgLetterBox1.setX(0f);
		imgLetterBox1.setY(0f);
		uiLayer.add(imgLetterBox1);
		
		Image imgLetterBox2 = new Image(Scene2DHelper.inst.skin, "ui_dock");
		imgLetterBox2.setSize(Gdx.graphics.getWidth(), imgLetterBox2.getHeight() * Constants.resize());
		imgLetterBox2.setX(0f);
		imgLetterBox2.setY(Gdx.graphics.getHeight() - imgLetterBox2.getHeight());
		uiLayer.add(imgLetterBox2);
		
		for(int i = 0; i < imgHearts.length; i++)
		{
			imgHearts[i] = new Image(Scene2DHelper.inst.skin, "ui_heart_full");
			uiLayer.add(imgHearts[i]);
			imgHearts[i].setSize(imgHearts[i].getWidth() * Constants.resize(), imgHearts[i].getHeight() * Constants.resize());
			imgHearts[i].setY(Gdx.graphics.getHeight() - imgHearts[i].getHeight() * 2f - imgLetterBox2.getHeight());
			imgHearts[i].setX(imgHearts[i].getWidth() + (imgHearts[i].getWidth() * i) + MathHelper.inst.pxToScreen(4f + (4f * i)));
		}
		
		Image imgTitle = new Image(Scene2DHelper.inst.skin, "inventomatic2500");
		uiLayer.add(imgTitle);
		imgTitle.setSize(imgTitle.getWidth() * Constants.resize(), imgTitle.getHeight() * Constants.resize());
		imgTitle.setY(Gdx.graphics.getHeight() - imgTitle.getHeight() - MathHelper.inst.pxToScreen(2f));
		imgTitle.setX(Gdx.graphics.getWidth() / 2f - imgTitle.getWidth() / 2f);
		
		Image imgSelected = new Image(Scene2DHelper.inst.skin, "ui_selected_tab");
		uiLayer.add(imgSelected);
		imgSelected.setSize(imgSelected.getWidth() * Constants.resize(), imgSelected.getHeight() * Constants.resize());
		
		Image imgInventory = new Image(Scene2DHelper.inst.skin, "ui_icon_inventory");
		uiLayer.add(imgInventory);
		imgInventory.setSize(imgInventory.getWidth() * Constants.resize(), imgInventory.getHeight() * Constants.resize());
		imgInventory.setX(Gdx.graphics.getWidth() - imgSelected.getWidth() * 3f);
		imgInventory.setY(imgHearts[0].getY() - MathHelper.inst.pxToScreen(2f));
		
		Image imgChat = new Image(Scene2DHelper.inst.skin, "ui_icon_chat");
		uiLayer.add(imgChat);
		imgChat.setSize(imgChat.getWidth() * Constants.resize(), imgChat.getHeight() * Constants.resize());
		imgChat.setX(imgInventory.getX() + imgSelected.getWidth());
		imgChat.setY(imgInventory.getY());
		
		imgSelected.setX(imgInventory.getX() - MathHelper.inst.pxToScreen(2f));
		imgSelected.setY(imgInventory.getY() - MathHelper.inst.pxToScreen(2f));
		
		Label lblView = new Label("Inventory", Scene2DHelper.inst.skin, "default");
		uiLayer.add(lblView);
		lblView.getStyle().font.getData().setScale(Constants.resize());
		
		glyphItemDescription = new GlyphLayout();
		glyphItemDescription.setText(lblView.getStyle().font, lblView.getText().toString());
		lblView.setX(Gdx.graphics.getWidth() / 4f - glyphItemDescription.width / 2f);
		lblView.setY(imgHearts[0].getY() - glyphItemDescription.height - MathHelper.inst.pxToScreen(4f));
		
		lblItemName = new Label("Burger", Scene2DHelper.inst.skin, "smaller");
		uiLayer.add(lblItemName);
		lblItemName.getStyle().font.getData().setScale(Constants.resize());
		lblItemName.setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
		lblItemName.setY(imgHearts[0].getY() - glyphItemDescription.height - MathHelper.inst.pxToScreen(10f));
		
		for(int i = 0; i < lblItemDescription.length; i++)
		{
			lblItemDescription[i] = new Label("set me", Scene2DHelper.inst.skin, "smaller");
			uiLayer.add(lblItemDescription[i]);
			lblItemDescription[i].getStyle().font.getData().setScale(Constants.resize());
			lblItemDescription[i].setX(Gdx.graphics.getWidth() / 2f + MathHelper.inst.pxToScreen(2f));
			lblItemDescription[i].setY(imgHearts[0].getY() - glyphItemDescription.height - MathHelper.inst.pxToScreen(26f + (float)i * 7f));
		}
		
		imgItemSelect = new Image(Scene2DHelper.inst.skin, "ui_selected_item");
		uiLayer.add(imgItemSelect);
		imgItemSelect.setSize(imgItemSelect.getWidth() * Constants.resize(), imgItemSelect.getHeight() * Constants.resize());
		
		imgInventoryIcon = new Image[3][3];
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				imgInventoryIcon[y][x] = new Image(Scene2DHelper.inst.skin, "item_burger");
				uiLayer.add(imgInventoryIcon[y][x]);
				imgInventoryIcon[y][x].setSize(imgInventoryIcon[y][x].getWidth() * Constants.resize(), imgInventoryIcon[y][x].getHeight() * Constants.resize());
				imgInventoryIcon[y][x].setX((Gdx.graphics.getWidth() / 4f - glyphItemDescription.width / 2f) + imgInventoryIcon[y][x].getWidth() * (float)x + MathHelper.inst.pxToScreen(4f * (float)(x + 1)));
				imgInventoryIcon[y][x].setY((imgHearts[0].getY() - glyphItemDescription.height - MathHelper.inst.pxToScreen(24f)) - imgInventoryIcon[y][x].getHeight() * (float)y - MathHelper.inst.pxToScreen(4f * (float)(y + 1)));
			}
		}
		
		float padding = 0f;
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				btnInventoryIconOverlay[y][x] = new Button(Scene2DHelper.inst.skin, "blank");
				uiLayer.add(btnInventoryIconOverlay[y][x]);
				btnInventoryIconOverlay[y][x].setSize(imgInventoryIcon[y][x].getWidth(), imgInventoryIcon[y][x].getHeight());
				
				if(x == 2)
					padding = MathHelper.inst.pxToScreen(0.5f);
				else if(x == 0)
					padding = MathHelper.inst.pxToScreen(1f);
				
				btnInventoryIconOverlay[y][x].setX(imgInventoryIcon[y][x].getX() + padding);
				btnInventoryIconOverlay[y][x].setY(imgInventoryIcon[y][x].getY());
			}
		}
		
		initItemClickListeners();
		
		imgItemSelect.setX(imgInventoryIcon[0][1].getX() - MathHelper.inst.pxToScreen(1f));
		imgItemSelect.setY(imgInventoryIcon[0][1].getY() - MathHelper.inst.pxToScreen(1f));
		
		btnUse = new Button(Scene2DHelper.inst.skin, "use_item");
		uiLayer.add(btnUse);
		btnUse.setSize(btnUse.getWidth() * Constants.resize(), btnUse.getHeight() * Constants.resize());
		btnUse.setX(lblItemName.getX());
		btnUse.setY(imgLetterBox2.getHeight() * 2f);
		btnUse.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				System.out.println("Use click");
			}
		});
		
		btnDestroy = new Button(Scene2DHelper.inst.skin, "destroy_item");
		uiLayer.add(btnDestroy);
		btnDestroy.setSize(btnDestroy.getWidth() * Constants.resize(), btnDestroy.getHeight() * Constants.resize());
		btnDestroy.setX(btnUse.getX() + btnUse.getWidth() + MathHelper.inst.pxToScreen(4f));
		btnDestroy.setY(btnUse.getY());
		
		Image imgActionLine = new Image(Scene2DHelper.inst.skin, "ui_dock");
		uiLayer.add(imgActionLine);
		imgActionLine.setSize(Gdx.graphics.getWidth(), MathHelper.inst.pxToScreen(1f));
		imgActionLine.setX(btnUse.getX());
		imgActionLine.setY(btnUse.getY() + btnUse.getHeight() + MathHelper.inst.pxToScreen(2f));
		
		Label lblClose = new Label("Press ESC to Close", Scene2DHelper.inst.skin, "smaller");
		uiLayer.add(lblClose);
		lblClose.getStyle().font.getData().setScale(Constants.resize());
		lblClose.setX(MathHelper.inst.pxToScreen(2f));
		lblClose.setY(imgLetterBox2.getHeight());
		
		stack.add(uiLayer);
		
		updateItems();
	}
	
	public void updateItems()
	{
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				imgInventoryIcon[y][x].setDrawable(Scene2DHelper.inst.skin, ItemHandler.inst.getItemAt(x, y).drawable);
			}
		}
	}
	
	private void clickOnItem(int x, int y)
	{
		imgItemSelect.setX(imgInventoryIcon[y][x].getX() - MathHelper.inst.pxToScreen(1f));
		imgItemSelect.setY(imgInventoryIcon[y][x].getY() - MathHelper.inst.pxToScreen(1f));
		lblItemName.setText(ItemHandler.inst.getItemAt(x, y).name);
		if(ItemHandler.inst.getItemAt(x, y).equals(ItemHandler.Item.BLANK))
		{
			btnUse.setVisible(false);
			btnDestroy.setVisible(false);
		}
		else
		{
			btnUse.setVisible(true);
			btnDestroy.setVisible(true);
		}
		
		for(int i = 0; i < lblItemDescription.length; i++)
			lblItemDescription[i].setText("");
		
		if(ItemHandler.inst.getItemAt(x, y).description.length() > 0)
		{
			Array<String> text = new Array<String>();
			text.addAll(ItemHandler.inst.getItemAt(x, y).description.split(" "));
			String line = "";
			
			overflowText:
			for(int yIndex = 0; yIndex < 4; yIndex++)
			{
				linePopulate:
				for(int xLine = 0; xLine < text.size; xLine++)
				{
					line += text.get(xLine) + " ";
					glyphItemDescription.setText(lblItemDescription[yIndex].getStyle().font, line);
					if(glyphItemDescription.width > MathHelper.inst.pxToScreen(100f))
					{
						line = "";
						for(int i = 0; i < xLine; i++)
						{
							line += text.get(0) + " ";
							text.removeIndex(0);
						}
						lblItemDescription[yIndex].setText(line);
						line = "";
						break linePopulate;
					}
				}
				if(line.length() > 0)
				{
					lblItemDescription[yIndex].setText(line);
					break overflowText;
				}
			}
		}
	}
	
	public void updateHealth()
	{
		// Set health.
		for(int i = 0; i < imgHearts.length; i++)
		{
			if(i < GameData.health)
				imgHearts[i].setDrawable(Scene2DHelper.inst.skin, "ui_heart_full");
			else
				imgHearts[i].setDrawable(Scene2DHelper.inst.skin, "ui_heart_empty");
		}
	}
	
	public boolean catchingCursor()
	{
		return catchingMouseInput;
	}
	
	public void setCursorX(int x)
	{
		cursorX = x;
	}
	
	public void toggleInventory()
	{
		if(!uiLayer.isVisible())
		{
			catchingMouseInput = false;
			Gdx.input.setCursorCatched(false);
			updateHealth();
			clickOnItem(1, 1);
			Gdx.input.setInputProcessor(stage);
			Gdx.input.setCursorPosition((int)(Gdx.graphics.getWidth() / 2f), (int)(Gdx.graphics.getHeight() / 2f));
		}
		else
		{
			catchingMouseInput = true;
			Gdx.input.setCursorCatched(true);
			Gdx.input.setCursorPosition(cursorX, cursorY);
			Renderer.inst.setAsInputProcessor();
		}
		uiLayer.setVisible(!uiLayer.isVisible());
		Scene2DHelper.inst.toggleBackground();
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
		
		/*if(imgBg.isVisible() && catchingMouseInput)
		{
			catchingMouseInput = false;
			Gdx.input.setCursorCatched(false);
		}
		else if(!imgBg.isVisible() && !catchingMouseInput)
		{
			catchingMouseInput = true;
			Gdx.input.setCursorCatched(true);
		}*/
	}
	
	private void initItemClickListeners()
	{
		btnInventoryIconOverlay[0][0].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(0, 0);
			}
		});
		
		btnInventoryIconOverlay[0][1].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(1, 0);
			}
		});
		
		btnInventoryIconOverlay[0][2].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(2, 0);
			}
		});
		
		btnInventoryIconOverlay[1][0].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(0, 1);
			}
		});
		
		btnInventoryIconOverlay[1][1].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(1, 1);
			}
		});
		
		btnInventoryIconOverlay[1][2].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(2, 1);
			}
		});
		
		btnInventoryIconOverlay[2][0].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(0, 2);
			}
		});
		
		btnInventoryIconOverlay[2][1].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(1, 2);
			}
		});
		
		btnInventoryIconOverlay[2][2].addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				clickOnItem(2, 2);
			}
		});
	}
}
