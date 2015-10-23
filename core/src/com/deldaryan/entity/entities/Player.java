package com.deldaryan.entity.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.entity.EntityType;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.LightComponent;
import com.deldaryan.entity.component.VelocityComponent;

public class Player extends Entity {

	public Player(Vector2 position, Engine engine) {
		engine.addEntity(this);
		
		EntityComponent entityComp = new EntityComponent(EntityType.PLAYER, 20, 20, new Vector2(220f, 220f));
		entityComp.setIdentifier("player");
		add(entityComp);
		
		VelocityComponent veloComp = new VelocityComponent(new Vector2(0, 0));
		add(veloComp);
		
		BodyComponent bodyComp = new BodyComponent();
		bodyComp.createBody("body", getId(), position, 0.45f, true, 0, 1f, 0f, 0f, false);
		bodyComp.changeBody("body");
		add(bodyComp);
	
		LightComponent lightComp = new LightComponent(5f, 60, new Color(1f, 0.9f, 0.2f, 0.4f));
		lightComp.getLight().setIgnoreAttachedBody(true);
		lightComp.getLight().attachToBody(bodyComp.getCurrentBody());
		add(lightComp);
	}
}
