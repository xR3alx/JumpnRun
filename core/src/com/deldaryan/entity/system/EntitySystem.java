package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.deldaryan.entity.AnimationType;
import com.deldaryan.entity.EntityType;
import com.deldaryan.entity.component.AnimationComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.LightComponent;
import com.deldaryan.entity.component.WeaponComponent;
import com.deldaryan.main.Main;
import com.deldaryan.screen.screens.GameScreen;

public class EntitySystem extends IteratingSystem {

	public EntitySystem(Family family) {
		super(family);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EntityComponent entityComp = entity.getComponent(EntityComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		WeaponComponent weaponComp = entity.getComponent(WeaponComponent.class);
		AnimationComponent animComp = entity.getComponent(AnimationComponent.class);
		
		if(!entityComp.isRemoved()
				&& !GameScreen.PAUSE
				&& !GameScreen.CHANGE_TO_NEXT_MAP) {
			entityComp.setTimeExist(entityComp.getTimeExist()+1);
			
			
			if(entityComp.getEntityType() == EntityType.PLAYER) {
				if(animComp != null) {
					if(animComp.hasAnimation(AnimationType.WALK)
							&& animComp.getAnimation(AnimationType.WALK).isRender()) {
						if(entityComp.getStepTime() >= 21) {
							entityComp.setStepTime(0);
							
	//						Utils.playSound("step", 0.15f);
						}
						else {
							entityComp.setStepTime(entityComp.getStepTime()+1);
						}
					}
				}
			}
			
			
//			if(entityComp.getEntityType() == EntityType.NORMAL_PROJECTILE) {
//				ProjectileComponent projComp = entity.getComponent(ProjectileComponent.class);
//				
//				if(entityComp.getTimeExist() >= projComp.timeExistMax) {
//					entityComp.setRemove(true);
//				}
//			}
			

			if(entityComp.getHealth() <= 0) {
				for (Body body : bodyComp.getActiveBodies()) {
					body.setActive(false);
				}
				
				if(entity.getComponent(LightComponent.class) != null) {
					entity.getComponent(LightComponent.class).getLight().setActive(false);
				}
			}
			
			if(entityComp.isRemoveWhenHeal0()
					&& entityComp.getHealth() <= 0) {
				entityComp.setRemove(true);
			}
			
			if(entityComp.isRemove()) {
				entityComp.setRemoved(true);
				if(entity.getComponent(LightComponent.class) != null) {
					entity.getComponent(LightComponent.class).getLight().remove();
					entity.remove(LightComponent.class);
				}

				Main.getEntityManager().removeEntity(entity);
			}
			
			
			if(weaponComp != null) {
				weaponComp.updateAll();
			}
		}
	}
}
