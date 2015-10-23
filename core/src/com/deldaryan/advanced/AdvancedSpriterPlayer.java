package com.deldaryan.advanced;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;
import com.deldaryan.main.Main;
import com.deldaryan.utils.SpriterDrawer;
import com.deldaryan.utils.SpriterLoader;

@SuppressWarnings("unused")
public class AdvancedSpriterPlayer extends Player {

	private String shaderProgram;
	private SpriterLoader loader;
	private SpriterDrawer drawer;
	
	
	
	public AdvancedSpriterPlayer(Entity entity, SpriterLoader loader, SpriterDrawer drawer) {
		super(entity);
		this.loader = loader;
		this.drawer = drawer;
	}
	
	public boolean hasShaderProgram() {
		return Main.getAssetLoader().hasShader(shaderProgram);
	}
	
	public void setShaderProgram(String shaderProgram) {
		this.shaderProgram = shaderProgram;
	}
	
	public String getShaderProgram() {
		return shaderProgram;
	}
	
	
	public void draw(SpriteBatch spriteBatch) {
		drawer.setBatch(spriteBatch);
		spriteBatch.begin();
			if(hasShaderProgram()) {
				spriteBatch.setShader(Main.getAssetLoader().getShader(getShaderProgram()));
			}
			else {
				spriteBatch.setShader(null);
			}
			
			drawer.draw(this);
		spriteBatch.end();
	}
	
	
	
	public SpriterDrawer getDrawer() {
		return drawer;
	}
	
	public SpriterLoader getLoader() {
		return loader;
	}
}
