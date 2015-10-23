package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.entity.EntityType;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.VelocityComponent;

public class VelocitySystem extends IteratingSystem {

	public VelocitySystem(Family family) {
		super(family);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EntityComponent entityComp = entity.getComponent(EntityComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		VelocityComponent veloComp = entity.getComponent(VelocityComponent.class);
		

		if(bodyComp.hasBodies()) {
			///////////////////////////////////////////// APPLY VELOCITY /////////////////////////////////////////////////
			if(entityComp.getEntityType() == EntityType.PLAYER
					/*
					|| entityComp.getEntityType() == EntityType.ENEMY
					|| entityComp.getEntityType() == EntityType.ENEMY_SHIELD
					|| entityComp.getEntityType() == EntityType.ENEMY_BOSS
					*/
					) {
				Vector2 velocity = new Vector2(veloComp.getVelocity().x, veloComp.getVelocity().y) ;
	
				
				float accStrength = 0.1f;
				float currentSpeed = bodyComp.getCurrentBody().getLinearVelocity().x;
				
				velocity.x *= velocity.x < 0 ? -1 : 1;
				currentSpeed *= currentSpeed < 0 ? -1 : 1;
				
				currentSpeed = currentSpeed + (velocity.x * accStrength);
				if(currentSpeed > velocity.x) { currentSpeed = velocity.x; }
				currentSpeed *= veloComp.getVelocity().x > 0 ? 1 : -1;
				
				velocity.set(currentSpeed, velocity.y);
				bodyComp.getCurrentBody().setLinearVelocity(velocity);
			}
			else {
				bodyComp.getCurrentBody().setLinearVelocity(veloComp.getVelocity());
			}
		}
	}
}
