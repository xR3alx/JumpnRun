package com.deldaryan.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class PhysicsUtils {

	public static BoundingBox getBodyBounding(Fixture fixture) {
		PolygonShape shape = (PolygonShape) fixture.getShape();
		
		Vector2 tmp = new Vector2();
		shape.getVertex(0, tmp);
		tmp = fixture.getBody().getWorldPoint(tmp);
		BoundingBox boundingBox = new BoundingBox(new Vector3(tmp, 0), new Vector3(tmp, 0));
		for (int i = 1; i < shape.getVertexCount(); i++) {
			shape.getVertex(i, tmp);
			boundingBox.ext(new Vector3(fixture.getBody().getWorldPoint(tmp), 0));
		}
		
		return boundingBox;
	}
	
}
