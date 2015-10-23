package com.deldaryan.advanced;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class AdvancedSprite {

	private float offsetX, offsetY;
	private boolean render;
	private Sprite sprite;
	private ShaderProgram shader;
	
	public AdvancedSprite(Texture texture) {
		sprite = new Sprite(texture);
	}
	
	public AdvancedSprite(AtlasRegion findRegion) {
		sprite = new Sprite(findRegion);
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	public boolean hasShader() {
		return shader == null ? false : true;
	}
	
	public ShaderProgram getShader() {
		return shader;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public boolean isRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
}
