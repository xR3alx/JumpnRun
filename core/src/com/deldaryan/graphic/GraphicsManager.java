package com.deldaryan.graphic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deldaryan.advanced.AdvancedCamera;
import com.deldaryan.main.Main;

public class GraphicsManager {

	private Stage stage;
	private OrthographicCamera uiCamera;
	private AdvancedCamera gameCamera;
	
	private RenderManager renderManager;
	private Matrix4 defaultProjectionMatrix4;
	private SpriteBatch spriteBatch;
	
	public GraphicsManager(Viewport viewport) {
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
		viewport.setCamera(uiCamera);
		
		gameCamera = new AdvancedCamera();
		gameCamera.setToOrtho(false, 23.99f, 23.99f);
		
		spriteBatch = new SpriteBatch();
		renderManager = new RenderManager(spriteBatch);
		renderManager.addLayer("ui");
		renderManager.setLayerOrder(new String[] {"ui"});
		
		defaultProjectionMatrix4 = new Matrix4(spriteBatch.getProjectionMatrix());
		stage = new Stage(viewport);
		Main.getInputMultiplexer().addProcessor(stage);
		
		
		renderManager.getLayer("ui").addRenderObject(stage);
		renderManager.getLayer("ui").setProjectionMatrix(uiCamera.combined);
	}
	
	public void update() {
		gameCamera.update();
		uiCamera.update();
		stage.act();
	}
	
	public void render() {
		renderManager.render();
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
	
	public RenderManager getRenderManager() {
		return renderManager;
	}
	
	
	public void dispose() {
		stage.dispose();
		spriteBatch.dispose();
	}
}
