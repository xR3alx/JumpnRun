package com.deldaryan.advanced;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class AdvancedAnimation extends Animation {

	private boolean flipX, flipY, render;
	private float width, height,
				offsetX, offsetY,
				time,
				degrees;
	private Color tint;
	private ShaderProgram shader;
	private Vector2 position;
	
	public AdvancedAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);
	}

	public AdvancedAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
		super(frameDuration, keyFrames, playMode);
	}
	
	public AdvancedAnimation(float frameDuration, PlayMode playMode, TextureRegion...keyFrames) {
		super(frameDuration, keyFrames);
		setPlayMode(playMode);
	}
	
	
	public float getTime() {
		return time;
	}
	
	public void increaseTime(float amount) {
		time+=amount;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	public boolean isRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
	
	public ShaderProgram getShader() {
		return shader;
	}
	
	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	public boolean hasShader() {
		return shader != null ? true : false;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public void setOffset(float x, float y) {
		setOffsetX(x);
		setOffsetY(y);
	}
	
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}
	
	public void setTint(Color tint) {
		this.tint = tint;
	}
	
	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}
	
	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}
	
	public void setFlip(boolean y, boolean x) {
		setFlipX(x);
		setFlipY(y);
	}
	
	public boolean isFlipX() {
		return flipX;
	}
	
	public boolean isFlipY() {
		return flipY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public Color getTint() {
		return tint;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public float getDegrees() {
		return degrees;
	}
	
	public void setDegrees(float degrees) {
		this.degrees = degrees;
	}
}
