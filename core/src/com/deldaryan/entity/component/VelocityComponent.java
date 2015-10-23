package com.deldaryan.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {

	private Vector2 velocity;
	
	public VelocityComponent(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}	
}
