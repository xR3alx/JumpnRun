package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.advanced.AdvancedAnimation;
import com.deldaryan.entity.AnimationType;
import com.deldaryan.entity.component.AnimationComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.SkeletonAnimationComponent;
import com.deldaryan.entity.component.VelocityComponent;
import com.deldaryan.main.Main;
import com.deldaryan.utils.Utils;

public class AnimationSystem extends IteratingSystem {

	public AnimationSystem(Family family) {
		super(family);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EntityComponent entityComp = entity.getComponent(EntityComponent.class);
		AnimationComponent animation = entity.getComponent(AnimationComponent.class);
		SkeletonAnimationComponent skeletonAnimation = entity.getComponent(SkeletonAnimationComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		VelocityComponent veloComp = entity.getComponent(VelocityComponent.class);
//		AIComponent aiComp = entity.getComponent(AIComponent.class);
		
		if(bodyComp.hasBodies()) {
			if(animation != null) {
				if(animation.isSomethingRendering()) {
					if(entityComp.getHealth() > 0) {
						if(veloComp.getVelocity().x != 0
								&& bodyComp.getCurrentBody().getLinearVelocity().y < 0.5
								&& bodyComp.getCurrentBody().getLinearVelocity().y > -0.5) {
							animation.hideAll();
							animation.getAnimation(AnimationType.WALK).setRender(true);
						}
						else {
							animation.hideAll();
							animation.getAnimation(AnimationType.IDLE).setRender(true);
						}
					}
					else if(animation.hasAnimation(AnimationType.DEAD)) {
						animation.hideAll();
						animation.getAnimation(AnimationType.DEAD).setRender(true);
					}
					
					for (AdvancedAnimation anim : animation.getRenderingAnimations()) {
						anim.setPosition(bodyComp.getCurrentBody().getPosition().add(anim.getOffsetX(), anim.getOffsetY()));
					}
				}
			}
			else if(skeletonAnimation != null) {
				if(entityComp.getHealth() > 0) {
					if(veloComp.getVelocity().x != 0
							&& bodyComp.getCurrentBody().getLinearVelocity().y < 0.5
							&& bodyComp.getCurrentBody().getLinearVelocity().y > -0.5) {
						skeletonAnimation.getPlayer().setAnimation(AnimationType.WALK);
					}
					else {
						if(bodyComp.getCurrentBody().getLinearVelocity().y > 0) {
							skeletonAnimation.getPlayer().setAnimation(AnimationType.JUMPING);
						}
						else if(entityComp.isFallen() && !entityComp.isOnGround()) {
							skeletonAnimation.getPlayer().setAnimation(AnimationType.FALLING);
						}
						else if(entityComp.isOnGround()){
							skeletonAnimation.getPlayer().setAnimation(AnimationType.IDLE);
						}
					}
				}
				else {
					skeletonAnimation.getPlayer().setAnimation(AnimationType.DEAD);
				}
				
				
				if(veloComp.getVelocity().x > 0) {
					if(skeletonAnimation.getPlayer().flippedX() < 0) {
						skeletonAnimation.getPlayer().flip(true, false);
					}
				}
				else if(veloComp.getVelocity().x < 0) {
					if(skeletonAnimation.getPlayer().flippedX() > 0) {
						skeletonAnimation.getPlayer().flip(true, false);
					}
				}
				skeletonAnimation.getPlayer().setPosition(bodyComp.getCurrentBody().getPosition().x + skeletonAnimation.getPlayer().getOffsetX(), bodyComp.getCurrentBody().getPosition().y + skeletonAnimation.getPlayer().getOffsetY());
//				skeletonAnimation.getPlayer().setBone("bone_001", (float) Utils.lookAt(bodyComp.getCurrentBody().getPosition(), new Vector2(Gdx.input.getX(), Gdx.input.getY()), Main.getGraphicsManager().getGameCamera()) * MathUtils.radiansToDegrees);
			}
		}
	}
}
