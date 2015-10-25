package com.deldaryan.advanced;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;
import com.deldaryan.main.Main;
import com.deldaryan.utils.SpriterDrawer;
import com.deldaryan.utils.SpriterLoader;

public class AdvancedSpriterPlayer extends Player {

	private SpriterLoader loader;
	private SpriterDrawer drawer;
	

	private float offsetX, offsetY;
	private String shader;
	
	
	
	public AdvancedSpriterPlayer(Entity entity, SpriterLoader loader, SpriterDrawer drawer) {
		super(entity);
		this.loader = loader;
		this.drawer = drawer;
	}
	
	public boolean hasShader() {
		return Main.getAssetLoader().hasShader(shader);
	}
	
	public void setShader(String shaderProgram) {
		this.shader = shaderProgram;
	}
	
	public String getShader() {
		return shader;
	}
	
	
	public void draw(SpriteBatch spriteBatch) {
		drawer.setBatch(spriteBatch);
		drawer.draw(this);
	}
	
	
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public SpriterDrawer getDrawer() {
		return drawer;
	}
	
	public SpriterLoader getLoader() {
		return loader;
	}
}
