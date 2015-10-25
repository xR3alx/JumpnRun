package com.deldaryan.graphic;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderManager {

	private HashMap<String, Layer> layers;
	private ArrayList<String> layerOrder;
	private SpriteBatch spriteBatch;
	
	public RenderManager(SpriteBatch spriteBatch) {
		layers = new HashMap<String, Layer>();
		layerOrder = new ArrayList<String>();
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
	
	public Layer addLayer(String key, int priority) {
		checkLayerOrder(key, priority);
		return layers.put(key, new Layer(priority));
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
		this.layerOrder.clear();
		for (String string : layerOrder) {
			this.layerOrder.add(string);
		}
	}
	
	public ArrayList<String> getLayerOrder() {
		return layerOrder;
	}
	

	public void dispose() {
		spriteBatch.dispose();
	}
	
	
	
	private void checkLayerOrder(String key, int priority) {
		if(priority >= layerOrder.size()) {
			layerOrder.add(key);
		}
		else {
			layerOrder.set(priority, key);
			checkLayerOrder(key, priority+1);
		}
	}
}
