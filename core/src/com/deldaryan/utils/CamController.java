package com.deldaryan.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.deldaryan.main.Main;

public class CamController implements InputProcessor {

	private OrthographicCamera camera;
	private int dragX, dragY;
	
	public CamController(OrthographicCamera camera) {
		this.camera = camera;
	}


	@Override
	public boolean keyDown(int keycode) {
		if(Main.DEBUG) {
			if(keycode == Keys.F1) {
				if(Gdx.input.isKeyJustPressed(keycode)) {
					Main.getGraphicsManager().getGameCamera().setMovementLocked(!Main.getGraphicsManager().getGameCamera().isMovementLocked());
				}
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		dragX = screenX;
//		dragY = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		float dX = (float)(screenX-dragX)/(float)Gdx.graphics.getWidth();
//		float dY = (float)(dragY-screenY)/(float)Gdx.graphics.getHeight();
//		dragX = screenX;
//		dragY = screenY;
//		
//		camera.position.x -= dX * 300f * camera.zoom;
//		camera.position.y -= dY * 50f * camera.zoom;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(camera.zoom + amount * 0.25f * camera.zoom > 0) {
			camera.zoom += amount * 0.25f * camera.zoom;
		}
		return false;
	}
}
