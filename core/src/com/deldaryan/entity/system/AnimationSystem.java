package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.deldaryan.entity.AnimationType;
import com.deldaryan.entity.component.AnimationComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.VelocityComponent;

public class AnimationSystem extends IteratingSystem {

	public AnimationSystem(Family family) {
		super(family);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EntityComponent entityComp = entity.getComponent(EntityComponent.class);
		AnimationComponent animation = entity.getComponent(AnimationComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		VelocityComponent veloComp = entity.getComponent(VelocityComponent.class);
//		AIComponent aiComp = entity.getComponent(AIComponent.class);
		
		if(animation.isSomethingRendering() && bodyComp.hasBodies()) {
			// get right animation to render
			if(entityComp.getHealth() > 0) {
				if(veloComp.getVelocity().x != 0
						|| veloComp.getVelocity().y != 0) {
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
		}
	}
}
