package com.deldaryan.entity.component;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deldaryan.physic.WorldManager.BodyCreator;
import com.deldaryan.physic.userdata.EntityUserData;
import com.deldaryan.utils.PhysicsUtils;

public class BodyComponent implements Component {

	private HashMap<String, Body> bodies;
	private String currentBody;
	
	private boolean onPlatform;
	private long platformId;
	
	public BodyComponent() {
		bodies = new HashMap<String, Body>();
	}
	
	public void createBodyCircle(String key, long entityID,
			Vector2 position, float radius, boolean allowSleep, float angle,
			float density, float friction, float restitution, boolean sensor, short category, short mask) {
		bodies.put(key, BodyCreator.createCircle(position, radius, BodyType.DynamicBody, allowSleep, angle, false, density, friction, restitution, sensor, category, mask, new EntityUserData(entityID)));
	}
	
	
	public void createBodyCircle(String key, long entityID,
			Vector2 position, float radius, boolean allowSleep, float angle,
			float density, float friction, float restitution, boolean sensor) {
		bodies.put(key, BodyCreator.createCircle(position, radius, BodyType.DynamicBody, allowSleep, angle, false, density, friction, restitution, sensor, new EntityUserData(entityID)));
	}
	
	public void createBodyBox(String key, long entityID,
			Vector2 position, float width, float height, boolean allowSleep, float angle,
			float density, float friction, float restitution, boolean sensor, short category, short mask) {
		bodies.put(key, BodyCreator.createPolygon(position, width, height, BodyType.DynamicBody, allowSleep, angle, false, density, friction, restitution, sensor, category, mask, new EntityUserData(entityID)));
	}
	
	
	public void createBodyBox(String key, long entityID,
			Vector2 position, float width, float height, boolean allowSleep, float angle,
			float density, float friction, float restitution, boolean sensor) {
		bodies.put(key, BodyCreator.createPolygon(position, width, height, BodyType.DynamicBody, allowSleep, angle, false, density, friction, restitution, sensor, new EntityUserData(entityID)));
	}
	
	
	
	
	public boolean hasBodies() {
		return bodies.size() != 0 ? true : false;
	}
	
	public Body getBody(String key) {
		return bodies.get(key);
	}
	
	public void changeBody(String key) {
		for (Body body : bodies.values()) {
			if(getCurrentBody() != null) {
				float currentHeight = PhysicsUtils.getBodyBounding(getCurrentBody().getFixtureList().first()).getHeight();
				float newBodyHeight = PhysicsUtils.getBodyBounding(body.getFixtureList().first()).getHeight();
				
				if(currentHeight > newBodyHeight) {
					Vector2 pos = new Vector2(getCurrentBody().getPosition().x, getCurrentBody().getPosition().y - (currentHeight-newBodyHeight));
					body.setTransform(pos, getCurrentBody().getAngle());
				}
				else {
					body.setTransform(getCurrentBody().getPosition(), getCurrentBody().getAngle());
				}
			}
			body.setActive(false);
		}
		
		getBody(key).setActive(true);
		currentBody = key;
	}
	
	public Body getCurrentBody() {
		return getBody(currentBody);
	}
	
	public ArrayList<Body> getBodies() {
		ArrayList<Body> bodies = new ArrayList<Body>();
		
		for (Body body : this.bodies.values()) {
			bodies.add(body);
		}
		
		return bodies;
	}
	
	public ArrayList<Body> getActiveBodies() {
		ArrayList<Body> bodies = new ArrayList<Body>();
		
		for (Body body : getBodies()) {
			if(body.isActive()) {
				bodies.add(body);
			}
		}
		
		return bodies;
	}
	
	
	public void joinPlatform(long id) {
		onPlatform = true;
		platformId = id;
	}
	
	public void leftPlatform() {
		onPlatform = false;
	}
	
	public boolean isOnPlatform() {
		return onPlatform;
	}
	
	public long getPlatformId() {
		return platformId;
	}
}
