package com.deldaryan.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deldaryan.entity.EntityManager;
import com.deldaryan.file.AssetLoader;
import com.deldaryan.file.ConfigFile;
import com.deldaryan.file.LanguageFile;
import com.deldaryan.graphic.GraphicsManager;
import com.deldaryan.map.MapManager;
import com.deldaryan.physic.WorldManager;
import com.deldaryan.screen.ScreenManager;
import com.deldaryan.screen.screens.GameScreen;
import com.deldaryan.screen.screens.LaunchScreen;
import com.deldaryan.screen.screens.MainScreen;
import com.deldaryan.screen.screens.SettingsScreen;
import com.deldaryan.utils.CamController;
import com.deldaryan.utils.DataObject;
import com.deldaryan.utils.DebugDisplay;

public class Main extends Game {

	private static WorldManager worldManager;
	private static EntityManager entityManager;
	private static GraphicsManager graphicsManager;
	private static MapManager mapManager;
	private static ScreenManager screenManager;
	private static AssetLoader assetLoader;
	
	private static LanguageFile languageFile;
	private static ConfigFile configFile;
	private static DataObject sessionData;
	
	private CamController cameraController;
	private static InputMultiplexer inputMultiplexer;
	
	public static boolean DEBUG = true,
							DRAW_PIXELGRID = false;
	private static DebugDisplay debugDisplay;
	
	public static String DESKTOP_PATH_MODIFIER;
	public static final int WORLD_WIDTH = 128, WORLD_HEIGHT = 128,
							UI_WIDTH = 364, UI_HEIGHT = 256;
	
	public Main(String desktopPathModifier) {
		DESKTOP_PATH_MODIFIER = desktopPathModifier;
	}
	
	@Override
	public void create() {
		inputMultiplexer = new InputMultiplexer();

		languageFile = new LanguageFile();
		configFile = new ConfigFile();
		languageFile.setLangPrefix(configFile.get("lang"));
		sessionData = new DataObject();
		
		worldManager = new WorldManager();
		entityManager = new EntityManager();
		graphicsManager = new GraphicsManager(new FitViewport(WORLD_WIDTH * 2, WORLD_HEIGHT * 2));
		mapManager = new MapManager();
		screenManager = new ScreenManager();
		assetLoader = new AssetLoader();
		
		cameraController = new CamController(graphicsManager.getGameCamera());
		inputMultiplexer.addProcessor(cameraController);
		
		
		screenManager.addScreen("main", new MainScreen());
		screenManager.addScreen("settings", new SettingsScreen());
		screenManager.addScreen("game", new GameScreen());
		screenManager.addScreen("launch", new LaunchScreen());
		screenManager.changeScreenTo("launch");
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		graphicsManager.update();
		super.render();
		graphicsManager.render();
		
		
		if(DEBUG) {
			if(debugDisplay == null) {
				debugDisplay = new DebugDisplay();
			}
			debugDisplay.render();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		configFile.save();
		graphicsManager.dispose();
		assetLoader.dispose();
		worldManager.dispose();
		entityManager.dispose();
		mapManager.dispose();
		
		if(debugDisplay != null) {
			debugDisplay.dispose();
		}
	}
	
	
	
	public static LanguageFile getLanguageFile() {
		return languageFile;
	}
	
	public static ConfigFile getConfigFile() {
		return configFile;
	}
	
	public static InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}
	
	public static ScreenManager getScreenManager() {
		return screenManager;
	}
	
	public static MapManager getMapManager() {
		return mapManager;
	}
	
	public static AssetLoader getAssetLoader() {
		return assetLoader;
	}
	
	public static WorldManager getWorldManager() {
		return worldManager;
	}
	
	public static EntityManager getEntityManager() {
		return entityManager;
	}
	
	public static GraphicsManager getGraphicsManager() {
		return graphicsManager;
	}
	
	
	
	
	public static DebugDisplay getDebugDisplay() {
		return debugDisplay;
	}
	
	public static DataObject getSessionData() {
		return sessionData;
	}
}