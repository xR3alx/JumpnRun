package com.deldaryan.entity;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.deldaryan.entity.component.AnimationComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.LightComponent;
import com.deldaryan.entity.component.ProjectileComponent;
import com.deldaryan.entity.component.SpriteComponent;
import com.deldaryan.entity.component.VelocityComponent;
import com.deldaryan.entity.component.WeaponComponent;
import com.deldaryan.entity.system.AnimationSystem;
import com.deldaryan.entity.system.EntitySystem;
import com.deldaryan.entity.system.RenderSystem;
import com.deldaryan.entity.system.VelocitySystem;
import com.deldaryan.main.Main;

public class EntityManager {

	private Engine engine;
	
	@SuppressWarnings("unchecked")
	public EntityManager() {
		engine = new Engine();
		
		engine.addSystem(new VelocitySystem(Family.all(EntityComponent.class, BodyComponent.class, VelocityComponent.class).get()));
		engine.addSystem(new AnimationSystem(Family.all(EntityComponent.class, AnimationComponent.class, BodyComponent.class, VelocityComponent.class).get()));
		engine.addSystem(new RenderSystem(Family.one(AnimationComponent.class, SpriteComponent.class, LightComponent.class, BodyComponent.class).get()));
		engine.addSystem(new EntitySystem(Family.one(AnimationComponent.class, BodyComponent.class, EntityComponent.class, ProjectileComponent.class,
						SpriteComponent.class, VelocityComponent.class, WeaponComponent.class).get()));
//		engine.addSystem(new AISystem(Family.one(AnimationComponent.class, SpriteComponent.class, WeaponComponent.class)
//				.all(EntityComponent.class, AIComponent.class, VelocityComponent.class, BodyComponent.class).get()));
	}

	public void render() {
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	
	
	
	
	public void addEntity(Entity entity) {
		engine.addEntity(entity);
	}

	public ArrayList<Entity> getEntities(int entityType) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for (Entity entity : engine.getEntities()) {
			if(entity.getComponent(EntityComponent.class).getEntityType() == entityType) {
				entities.add(entity);
			}
		}
		
		return entities;
	}
	
	public Entity getEntity(String identifier) {
		for (Entity entity : engine.getEntities()) {
			if(entity.getComponent(EntityComponent.class).getIdentifier().equals(identifier)) {
				return entity;
			}
		}
		return null;
	}
	
	public Entity getEntity(long id) {
		return engine.getEntity(id);
	}

	public void removeEntity(Entity entity) {
		for (Body body : entity.getComponent(BodyComponent.class).getBodies()) {
			Main.getWorldManager().removeBody(body);
		}
		
		engine.removeEntity(entity);
	}
	
	public void removeAll() {
		engine.removeAllEntities();
	}
	
	
	
	
	
	public Engine getEngine() {
		return engine;
	}
	
	public int getEntityCount() {
		return engine.getEntities().size();
	}

	
	public void dispose() {
		((RenderSystem) engine.getSystem(RenderSystem.class)).dispose();
	}
}
