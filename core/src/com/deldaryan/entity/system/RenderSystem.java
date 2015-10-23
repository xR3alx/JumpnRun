package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.deldaryan.entity.component.AnimationComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.SpriteComponent;
import com.deldaryan.main.Main;

public class RenderSystem extends IteratingSystem {

	private SpriteBatch spriteBatch;
	
	public RenderSystem(Family family) {
		super(family);
		
		spriteBatch = new SpriteBatch();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AnimationComponent animation = entity.getComponent(AnimationComponent.class);
		SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		
		if(bodyComp.hasBodies()) {
			spriteBatch.setProjectionMatrix(Main.getGraphicsManager().getGameCamera().combined);
			if(animation != null) {
				spriteBatch.begin();
					animation.render(spriteBatch, bodyComp.getCurrentBody().getPosition(), bodyComp.getCurrentBody().getAngle() * MathUtils.radiansToDegrees);
				spriteBatch.end();
			}
			else if(sprite != null) {
				sprite.render(spriteBatch, bodyComp.getCurrentBody().getPosition());
			}
		}
	}
	
	
	public void dispose() {
		spriteBatch.dispose();
	}
}
