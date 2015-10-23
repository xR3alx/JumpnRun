package com.deldaryan.entity.component;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.advanced.AdvancedSprite;

public class SpriteComponent implements Component {
	
	private HashMap<String, AdvancedSprite> sprites;
	
	public SpriteComponent() {
		sprites = new HashMap<String, AdvancedSprite>();
	}
	
	public void addSprite(String key, AdvancedSprite sprite) {
		sprites.put(key, sprite);
	}
	
	public void removeSprite(String key) {
		sprites.remove(key);
	}
	
	public AdvancedSprite getSprite(String key) {
		return sprites.get(key);
	}
	
	public ArrayList<AdvancedSprite> getRenderingSprites() {
		ArrayList<AdvancedSprite> advSprites = new ArrayList<AdvancedSprite>();
		
		for (AdvancedSprite advancedSprite : sprites.values()) {
			if(advancedSprite.isRender()) {
				advSprites.add(advancedSprite);
			}
		}
		
		return advSprites;
	}
	
	
	
	
	public void render(SpriteBatch batch, Vector2 position) {
		for (AdvancedSprite sprite : getRenderingSprites()) {
			if(sprite.hasShader()) {
				batch.setShader(sprite.getShader());
			}
			
			sprite.getSprite().setPosition(position.x - (sprite.getSprite().getWidth() / 2) + sprite.getOffsetX(), position.y - (sprite.getSprite().getHeight() / 2) + sprite.getOffsetY());
			sprite.getSprite().setOriginCenter();
			
			batch.begin();
				sprite.getSprite().draw(batch);
			batch.end();
			
			batch.setShader(null);
		}
	}
}
