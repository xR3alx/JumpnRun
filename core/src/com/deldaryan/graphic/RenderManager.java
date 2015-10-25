package com.deldaryan.graphic;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderManager {

	private HashMap<String, Layer> layers;
	private String[] layerOrder;
	private SpriteBatch spriteBatch;
	
	public RenderManager(SpriteBatch spriteBatch) {
		layers = new HashMap<String, Layer>();
		this.spriteBatch = spriteBatch;
	}
	
	public RenderManager() {
		layers = new HashMap<String, Layer>();
		spriteBatch = new SpriteBatch();
	}

	
	public void render() {
		for (String string : layerOrder) {
			if(hasLayer(string)) {
				getLayer(string).render(spriteBatch);
			}
		}
	}
	
	
	
	public void enableAll() {
		for (Layer layer : layers.values()) {
			layer.setEnabled(true);
		}
	}
	
	public void disableAll() {
		for (Layer layer : layers.values()) {
			layer.setEnabled(false);
		}
	}
	
	public Layer addLayer(String key) {
		return layers.put(key, new Layer());
	}
	
	public boolean hasLayer(String key) {
		return layers.containsKey(key);
	}
	
	public Layer getLayer(String key) {
		return layers.get(key);
	}
	
	public void removeLayer(String key) {
		layers.remove(key);
	}

	public void setLayerOrder(String[] layerOrder) {
		this.layerOrder = layerOrder;
	}
	
	public String[] getLayerOrder() {
		return layerOrder;
	}
	

	public void dispose() {
		spriteBatch.dispose();
	}
}
