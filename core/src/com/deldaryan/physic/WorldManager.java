package com.deldaryan.physic;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.deldaryan.main.Main;

import box2dLight.RayHandler;

public class WorldManager {

	private World world;
	private RayHandler rayHandler;
	private Box2DDebugRenderer debugRenderer;
	
	private ArrayList<Body> bodiesToRemove;
	
	public static final int PIXELS_PER_METER = 64;
	
	private static final Vector2 GRAVITY = new Vector2(0, 0);
	private static final boolean DO_SLEEP = true;
	private static final int POSITION_ITERATION = 7, VELOCITY_ITERATION = 9;
	private static final float TIMESTEP = 1 / 60f;
	
	public WorldManager() {
		world = new World(GRAVITY, DO_SLEEP);
		RayHandler.useDiffuseLight(true);
		rayHandler = new RayHandler(world);
		rayHandler.setBlur(false);
		debugRenderer = new Box2DDebugRenderer();
		bodiesToRemove = new ArrayList<Body>();
	}
	
	public void update() {
		world.step(TIMESTEP, VELOCITY_ITERATION, POSITION_ITERATION);
		rayHandler.update();
		
		for (Body body : bodiesToRemove) {
			world.destroyBody(body);
		}
		bodiesToRemove.clear();
	}
	
	public void render() {
		rayHandler.setCombinedMatrix(Main.getGraphicsManager().getGameCamera());
		rayHandler.render();
		
		if(Main.DEBUG) {
			debugRenderer.render(world, Main.getGraphicsManager().getGameCamera().combined);
		}
	}
	
	
	
	public World getWorld() {
		return world;
	}
	
	public RayHandler getRayHandler() {
		return rayHandler;
	}
	
	
	
	public void removeBody(Body body) {
		body.setActive(false);
		bodiesToRemove.add(body);
	}
	
	public int getBodyCount() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		
		return bodies.size;
	}
	
	
	
	public void removeAllBodiesAndLights() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			world.destroyBody(body);
		}
		
		rayHandler.removeAll();
	}
	
	public void dispose() {
		removeAllBodiesAndLights();
		rayHandler.dispose();
	}
	
	
	
	
	public static class BodyCreator {
		public static Body createPolygon(Vector2 position, float width, float height, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor, short category, short mask,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			fixdef.filter.categoryBits = category;
			fixdef.filter.maskBits = mask;
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width / 2, height / 2);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
		
		public static Body createPolygon(Vector2 position, float width, float height, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width / 2, height / 2);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
		
		
		
		public static Body createCircle(Vector2 position, float radius, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor, short category, short mask,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			fixdef.filter.categoryBits = category;
			fixdef.filter.maskBits = mask;
			
			CircleShape shape = new CircleShape();
			shape.setRadius(radius);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
		
		public static Body createCircle(Vector2 position, float radius, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			
			CircleShape shape = new CircleShape();
			shape.setRadius(radius);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
		
		
		
		public static Body createChain(Vector2 position, float[] vertices, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor, short category, short mask,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			fixdef.filter.categoryBits = category;
			fixdef.filter.maskBits = mask;
			
			
			ChainShape shape = new ChainShape();
			shape.createChain(vertices);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
		
		public static Body createChain(Vector2 position, float[] vertices, BodyType bodyType, boolean allowSleep, float angle, boolean fixedRotation,
				float density, float friction, float restitution, boolean sensor,
				Object userData) {
			BodyDef bodydef = new BodyDef();
			bodydef.type = bodyType;
			bodydef.allowSleep = allowSleep;
			bodydef.angle = angle;
			bodydef.fixedRotation = fixedRotation;
			bodydef.position.set(position);
			
			FixtureDef fixdef = new FixtureDef();
			fixdef.density = density;
			fixdef.friction = friction;
			fixdef.restitution = restitution;
			fixdef.isSensor = sensor;
			
			
			ChainShape shape = new ChainShape();
			shape.createChain(vertices);
			
			fixdef.shape = shape;
			
			Body body = Main.getWorldManager().getWorld().createBody(bodydef);
			body.createFixture(fixdef);
			body.setUserData(userData);

			shape.dispose();
			return body;
		}
	}
}
