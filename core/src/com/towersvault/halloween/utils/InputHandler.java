package com.towersvault.halloween.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.towersvault.halloween.render.Renderer;

public class InputHandler implements ControllerListener
{
	// For camera movement, see Renderer class.
	
	public static final InputHandler inst = new InputHandler();
	
	private class Xbox360Pad
	{
		public static final int BUTTON_X = 2;
		public static final int BUTTON_Y = 3;
		public static final int BUTTON_A = 0;
		public static final int BUTTON_B = 1;
		public static final int BUTTON_BACK = 6;
		public static final int BUTTON_START = 7;
		public static final int BUTTON_LB = 4;
		public static final int BUTTON_L3 = 8;
		public static final int BUTTON_RB = 5;
		public static final int BUTTON_R3 = 9;
		public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
		public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
		public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
		public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
		public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
		public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
	}
	
	private Controller controller;
	private boolean controllerConnected = false;
	
	public void init()
	{
		Controllers.addListener(this);
		if(Controllers.getControllers().size > 0)
		{
			Renderer.CONTROLLER_X = 0;
			Renderer.CONTROLLER_Y = 0;
			controllerConnected = true;
			System.out.println("Controller connected.");
			controller = Controllers.getControllers().first();
		}
	}
	
	public void checkInput()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.V))
			Constants.DYNAMIC_PHYSICS = !Constants.DYNAMIC_PHYSICS;
		
		if(isControllerConnected())
		{
			if(controller.getAxis(Xbox360Pad.AXIS_LEFT_X) > 0.2f ||
					controller.getAxis(Xbox360Pad.AXIS_LEFT_X) < -0.2f)
				Renderer.inst.updateCameraControllerX(controller.getAxis(Xbox360Pad.AXIS_LEFT_X));
			if(controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) > 0.2f ||
					controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) < -0.2f)
				Renderer.inst.updateCameraControllerY(controller.getAxis(Xbox360Pad.AXIS_LEFT_Y));
			
			if(controller.getAxis(Xbox360Pad.AXIS_RIGHT_X) > 0.2f ||
					controller.getAxis(Xbox360Pad.AXIS_RIGHT_X) < -0.2f)
				Renderer.inst.rotateCamera(controller.getAxis(Xbox360Pad.AXIS_RIGHT_X) * -1.2f);
		}
	}
	
	public boolean isControllerConnected()
	{
		return controllerConnected;
	}
	
	@Override
	public void connected(Controller controller)
	{
		Renderer.CONTROLLER_X = 0;
		Renderer.CONTROLLER_Y = 0;
		System.out.println("Controller connected.");
	}
	
	@Override
	public void disconnected(Controller controller)
	{
		Renderer.CONTROLLER_X = 0;
		Renderer.CONTROLLER_Y = 0;
		System.out.println("Controller disconnected.");
	}
	
	@Override
	public boolean buttonDown(Controller controller, int buttonCode)
	{
		return false;
	}
	
	@Override
	public boolean buttonUp(Controller controller, int buttonCode)
	{
		return false;
	}
	
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value)
	{
		/*System.out.println("----\nInput Caught:");
		if(axisCode == 0)
		{
			if(value > 0f)
				Renderer.CONTROLLER_Y = 1;
			else if(value < 0f)
				Renderer.CONTROLLER_Y = -1;
			else
				Renderer.CONTROLLER_Y = 0;
			System.out.println("Y: " + Renderer.CONTROLLER_Y);
		}
		if(axisCode == 1)
		{
			if(value > 0f)
				Renderer.CONTROLLER_X = 1;
			else if(value < 0f)
				Renderer.CONTROLLER_X = -1;
			else
				Renderer.CONTROLLER_X = 0;
			System.out.println("X: " + Renderer.CONTROLLER_X);
		}
		controller.getButton(0);*/
		
		return false;
	}
	
	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value)
	{
		return false;
	}
	
	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
	{
		return false;
	}
	
	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
	{
		return false;
	}
	
	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
	{
		return false;
	}
}
