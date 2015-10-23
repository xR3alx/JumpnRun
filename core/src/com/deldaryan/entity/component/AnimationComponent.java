package com.deldaryan.entity.component;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.advanced.AdvancedAnimation;
import com.deldaryan.screen.screens.GameScreen;

public class AnimationComponent implements Component {

	private HashMap<String, AdvancedAnimation> animations;
	
	public AnimationComponent() {
		animations = new HashMap<String, AdvancedAnimation>();
	}
	
	public void registerAnimation(String key, AdvancedAnimation animation) {
		animations.put(key, animation);
	}
	
	public boolean hasAnimation(String key) {
		return animations.containsKey(key);
	}
	
	public AdvancedAnimation getAnimation(String key) {
		return animations.get(key);
	}
	
	public ArrayList<AdvancedAnimation> getRenderingAnimations() {
		ArrayList<AdvancedAnimation> anims = new ArrayList<AdvancedAnimation>();
		
		for (AdvancedAnimation advancedAnimation : animations.values()) {
			if(advancedAnimation.isRender()) {
				anims.add(advancedAnimation);
			}
		}
		
		return anims;
	}
	
	public boolean isSomethingRendering() {
		return getRenderingAnimations().size() != 0 ? true : false;
	}
	
	
	public void renderAll() {
		for (AdvancedAnimation anim : animations.values()) {
			anim.setRender(true);
		}
	}
	
	public void hideAll() {
		for (AdvancedAnimation anim : animations.values()) {
			anim.setRender(false);
		}
	}
	
	
	public void render(SpriteBatch spriteBatch, Vector2 position, float degrees) {
		if(isSomethingRendering()) {
			for (AdvancedAnimation animation : getRenderingAnimations()) {
				if(animation.getKeyFrame(animation.getTime()) != null) {
					if(animation.hasShader()) {
						spriteBatch.setShader(animation.getShader());
					}
					else {
						spriteBatch.setShader(null);
					}
				
					Sprite frame = new Sprite(animation.getKeyFrame(animation.getTime()));
					frame.setSize(animation.getWidth(), animation.getHeight());
					frame.setPosition(position.x - (animation.getWidth() / 2) + (animation.getOffsetX() * (animation.isFlipX() ? -1 : 1)), position.y - (animation.getHeight() / 2) + (animation.getOffsetY() * (animation.isFlipY() ? -1 : 1)));
					frame.setRotation(degrees);
					if(animation.getTint() != null) {
						frame.setColor(animation.getTint());
					}
					frame.setFlip(animation.isFlipX(), animation.isFlipY());
					
					frame.draw(spriteBatch);
					
					if(!GameScreen.PAUSE
							&& !GameScreen.CHANGE_TO_NEXT_MAP) {
						animation.increaseTime(Gdx.graphics.getDeltaTime());
					}
				}
			}
		}
	}
}
