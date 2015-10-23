package com.deldaryan.graphic;

import java.util.HashMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deldaryan.advanced.AdvancedCamera;
import com.deldaryan.advanced.AdvancedSprite;
import com.deldaryan.main.Main;

public class GraphicsManager {

	private Stage stage;
	private OrthographicCamera uiCamera;
	private AdvancedCamera gameCamera;
	
	private HashMap<Integer, AdvancedSprite> sprites;
	private Matrix4 defaultProjectionMatrix4;
	private SpriteBatch spriteBatch;
	
	public GraphicsManager(Viewport viewport) {
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
		viewport.setCamera(uiCamera);
		
		gameCamera = new AdvancedCamera();
		gameCamera.setToOrtho(false, 23.99f, 23.99f);
		
		
		spriteBatch = new SpriteBatch();
		defaultProjectionMatrix4 = new Matrix4(spriteBatch.getProjectionMatrix());
		sprites = new HashMap<Integer, AdvancedSprite>();
		
		stage = new Stage(viewport);
		Main.getInputMultiplexer().addProcessor(stage);
	}
	
	public void update() {
		gameCamera.update();
		uiCamera.update();
		stage.act();
	}
	
	public void render() {
		renderSprites();
		stage.draw();
	}
	
	private void renderSprites() {
		for (int i = 0; i < sprites.size(); i++) {
			AdvancedSprite sprite = sprites.get(i);
			if(sprite.hasShader()) {
				spriteBatch.setShader(sprite.getShader());
			}
			
			spriteBatch.setProjectionMatrix(gameCamera.combined);
			spriteBatch.begin();
				sprite.getSprite().draw(spriteBatch);
			spriteBatch.end();
			
			spriteBatch.setShader(null);
		}
	}
	
	
	public Stage getStage() {
		return stage;
	}
	
	public OrthographicCamera getUiCamera() {
		return uiCamera;
	}
	
	public AdvancedCamera getGameCamera() {
		return gameCamera;
	}
	
	public Matrix4 getDefaultProjectionMatrix4() {
		return defaultProjectionMatrix4;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public void addSprite(int id, AdvancedSprite sprite) {
		sprites.put(id, sprite);
	}
	
	public boolean removeSprite(int id) {
		if(sprites.containsKey(id)) {
			sprites.remove(id);
			return true;
		}
		return false;
	}
	
	
	public void dispose() {
		stage.dispose();
		spriteBatch.dispose();
	}
}
