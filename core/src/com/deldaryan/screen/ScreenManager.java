package com.deldaryan.screen;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class ScreenManager {

	private HashMap<String, Screen> screens;
	private String lastScreen, currentScreen;
	
	public ScreenManager() {
		screens = new HashMap<String, Screen>();
	}

	
	public void addScreen(String key, Screen screen) {
		screens.put(key, screen);
	}
	
	public boolean hasScreen(String key) {
		return screens.containsKey(key);
	}
	
	public void changeScreenTo(String key) {
		if(hasScreen(key)) {
			lastScreen = new String(currentScreen != null ? currentScreen : "");
			currentScreen = key;
			((Game) Gdx.app.getApplicationListener()).setScreen(screens.get(key));
		}
		else {
			System.out.println("Screen " + key + " doesn't exist");
			Gdx.app.exit();
		}
	}
	
	
	public String getCurrentScreen() {
		return currentScreen;
	}
	
	public String getLastScreen() {
		return lastScreen;
	}
	
	public Screen getScreen(String key) {
		return screens.get(key);
	}
}
