package com.deldaryan.graphic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deldaryan.advanced.AdvancedAnimation;
import com.deldaryan.advanced.AdvancedSprite;
import com.deldaryan.advanced.AdvancedSpriterPlayer;
import com.deldaryan.main.Main;
import com.deldaryan.physic.WorldManager;
import com.deldaryan.screen.screens.GameScreen;

import box2dLight.RayHandler;

public class Layer {

	private ArrayList<Object> renderObjs;
	private Matrix4 projectionMatrix;
	
	private boolean enabled;
	
	public Layer() {
		renderObjs = new ArrayList<Object>();
	}
	
	public void render(SpriteBatch batch) {
		if(isEnabled()) {
			batch.setProjectionMatrix(projectionMatrix);
			
			for (Object object : renderObjs) {
				if(object instanceof AdvancedSprite) {
					AdvancedSprite advSprite = (AdvancedSprite) object;
					
					if(advSprite.isRender()) {
						if(advSprite.hasShader()) {
							batch.setShader(advSprite.getShader());
						}
						else {
							batch.setShader(null);
						}
						
						advSprite.getSprite().draw(batch);
					}
				}
				else if(object instanceof AdvancedAnimation) {
					AdvancedAnimation animation = (AdvancedAnimation) object;
						if(animation.getKeyFrame(animation.getTime()) != null) {
							if(animation.hasShader()) {
								batch.setShader(animation.getShader());
							}
							else {
								batch.setShader(null);
							}
						
							Sprite frame = new Sprite(animation.getKeyFrame(animation.getTime()));
							frame.setSize(animation.getWidth(), animation.getHeight());
							frame.setPosition(animation.getPosition().x - (animation.getWidth() / 2) + (animation.getOffsetX() * (animation.isFlipX() ? -1 : 1)), animation.getPosition().y - (animation.getHeight() / 2) + (animation.getOffsetY() * (animation.isFlipY() ? -1 : 1)));
							frame.setRotation(animation.getDegrees());
							if(animation.getTint() != null) {
								frame.setColor(animation.getTint());
							}
							frame.setFlip(animation.isFlipX(), animation.isFlipY());
							
							frame.draw(batch);
							
							if(!GameScreen.PAUSE
									&& !GameScreen.CHANGE_TO_NEXT_MAP) {
								animation.increaseTime(Gdx.graphics.getDeltaTime());
							}
					}
				}
				else if(object instanceof AdvancedSpriterPlayer) {
					AdvancedSpriterPlayer spriterPlayer = (AdvancedSpriterPlayer) object;
					if(spriterPlayer.hasShader()) {
						batch.setShader(Main.getAssetLoader().getShader(spriterPlayer.getShader()));
					}
					else {
						batch.setShader(null);
					}
					
					spriterPlayer.draw(batch);
				}
				else if(object instanceof Stage) {
					Stage stage = (Stage) object;
					stage.draw();
				}
				else if(object instanceof RayHandler) {
					RayHandler rayHandler = (RayHandler) object;
					rayHandler.render();
				}
				else if(object instanceof TextureMapObject) {
					TextureMapObject texObj = (TextureMapObject) object;
					
					if(Main.getGraphicsManager().getGameCamera().getCameraSpeed().x > 0.01f
							|| Main.getGraphicsManager().getGameCamera().getCameraSpeed().x < -0.01f) {
						float parallax_x = 0;
						if(texObj.getProperties().containsKey("parallax_x")) {
							parallax_x = Float.parseFloat(texObj.getProperties().get("parallax_x").toString());
						}
						texObj.setX(texObj.getX() - (Main.getGraphicsManager().getGameCamera().getCameraSpeed().x * parallax_x));
					}

					if(Main.getGraphicsManager().getGameCamera().getCameraSpeed().y > 0.01f
							|| Main.getGraphicsManager().getGameCamera().getCameraSpeed().y < -0.01f) {
						float parallax_y = 0;
						if(texObj.getProperties().containsKey("parallax_y")) {
							parallax_y = Float.parseFloat(texObj.getProperties().get("parallax_y").toString());
						}
						texObj.setY(texObj.getY() - (Main.getGraphicsManager().getGameCamera().getCameraSpeed().y * parallax_y));
					}
					
					Sprite sprite = new Sprite(texObj.getTextureRegion());
					sprite.setSize(
							texObj.getProperties().get("width", Float.class) / WorldManager.PIXELS_PER_METER,
							texObj.getProperties().get("height", Float.class) / WorldManager.PIXELS_PER_METER);
					sprite.setPosition(
							(texObj.getX() / WorldManager.PIXELS_PER_METER),
							(texObj.getY() / WorldManager.PIXELS_PER_METER));
					
					if(texObj.getProperties().containsKey("tint")) {
						String[] splited = texObj.getProperties().get("tint").toString().split(",");
						float red = Float.parseFloat(splited[0]);
						float green = Float.parseFloat(splited[1]);
						float blue = Float.parseFloat(splited[2]);
						float alpha = Float.parseFloat(splited[3]);
						
						sprite.setColor(red, green, blue, alpha);
					}
					
					sprite.draw(batch);
				}
			}
		}
	}

	
	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	public void addRenderObject(Object obj) {
		renderObjs.add(obj);
	}
	
	public ArrayList<Object> getRenderObjs() {
		return renderObjs;
	}
	
	public void removeRenderObj(int index) {
		renderObjs.remove(index);
	}
	
	public void setProjectionMatrix(Matrix4 projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}
	
}
