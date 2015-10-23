package com.deldaryan.advanced;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deldaryan.main.Main;

public class AdvancedCamera extends OrthographicCamera {
	
	private float rotation;
	private Vector2 cameraSpeed, lastPosMove,
					shakeStrength;
	private Body bodyToFollow;
	private boolean shaked, messureSpeed;
	private int shakeAmount;
	private ArrayList<CameraSequence> sequences;
	
	private Rectangle cameraBorderBox;
	
	private boolean movementLocked = true;
	
	public AdvancedCamera() {
		lastPosMove = new Vector2();
		cameraSpeed = new Vector2();
		shakeStrength = new Vector2();
		sequences = new ArrayList<AdvancedCamera.CameraSequence>();
	}
	
	@Override
	public void update() {
		super.update();
		
		
		if(isMovementLocked()
				&& sequences.size() == 0) {
			if(bodyToFollow != null) {
				position.set(bodyToFollow.getPosition(), 0);
			}
			
			if(cameraBorderBox != null) {
				if(position.x < cameraBorderBox.x) {
					position.x = cameraBorderBox.x;
				}
				else if(position.x > cameraBorderBox.x + cameraBorderBox.getWidth()) {
					position.x = cameraBorderBox.x + cameraBorderBox.getWidth();
				}
				
				if(position.y < cameraBorderBox.y) {
					position.y = cameraBorderBox.y;
				}
				else if(position.y > cameraBorderBox.y + cameraBorderBox.getHeight()) {
					position.y = cameraBorderBox.y + cameraBorderBox.getHeight();
				}
			}
		}
		
		for (CameraSequence cameraSequence : sequences) {
			if(cameraSequence.time < cameraSequence.length) {
				zoom -= cameraSequence.zoomSpeed;
				position.add(cameraSequence.speed.x, cameraSequence.speed.y, 0);
				cameraSequence.time++;
			}
			else {
				cameraSequence.finished = true;
			}
		}
		
		for (int i = 0; i < sequences.size(); i++) {
			if(sequences.get(i).finished) {
				sequences.remove(i);
			}
		}
		

		if(messureSpeed) {
			if(lastPosMove.x != position.x || lastPosMove.y != position.y) {
				cameraSpeed.x = new Float(lastPosMove.x - position.x);
				cameraSpeed.y = new Float(lastPosMove.y - position.y);
				
				lastPosMove.x = new Float(position.x);
				lastPosMove.y = new Float(position.y);
			}
			else {
				cameraSpeed.x = 0;
				cameraSpeed.y = 0;
			}
		}
		else {
			setMessureSpeed(true);
		}
		
		
		
		if(shakeAmount != 0) {
			if(MathUtils.isPowerOfTwo(shakeAmount)) {
				position.x -= shaked ? shakeStrength.x * 2 : shakeStrength.x;
				position.y += shaked ? shakeStrength.y * 2 : shakeStrength.y;
			}
			else {
				position.x += shaked ? shakeStrength.x * 2 : shakeStrength.x;
				position.y -= shaked ? shakeStrength.y * 2 : shakeStrength.y;
			}
			
			shaked = true;
			shakeAmount --;
		}
	}
	
	
	public void shake(int amount, Vector2 strength) {
		shakeAmount = amount;
		shakeStrength = strength;
	}
	
	
	@Override
	public void rotate(float angle) {
		super.rotate(angle);
		rotation += angle;
	}

	public float getRotation() {
		return rotation;
	}

	public Vector2 getCameraSpeed() {
		return cameraSpeed;
	}
	
	public Body getBodyToFollow() {
		return bodyToFollow;
	}
	
	public void setBodyToFollow(Body bodyToFollow) {
		this.bodyToFollow = bodyToFollow;
	}
	
	public Rectangle getCameraBorderBox() {
		return cameraBorderBox;
	}
	
	public void setCameraBorderBox(Rectangle cameraBorderBox) {
		this.cameraBorderBox = cameraBorderBox;
	}
	
	
	public void setMovementLocked(boolean movementLocked) {
		this.movementLocked = movementLocked;
	}
	
	public boolean isMovementLocked() {
		return movementLocked;
	}
	
	public void addSequence(CameraSequence sequence) {
		sequences.add(sequence);
	}
	
	public void clearSequences() {
		sequences.clear();
	}
	
	public boolean isMessureSpeed() {
		return messureSpeed;
	}
	
	public void setMessureSpeed(boolean messureSpeed) {
		lastPosMove.x = new Float(position.x);
		lastPosMove.y = new Float(position.y);
		cameraSpeed.x = 0;
		cameraSpeed.y = 0;
		
		this.messureSpeed = messureSpeed;
	}
	
	
	
	public static class CameraSequence {
		public Vector2 speed;
		public float zoomSpeed, time, length;
		public boolean finished;
		
		public CameraSequence(Vector2 position, float zoom, float length) {
			this.length = length;
			
			float distanceX = new Vector2(position.x, 0).dst(Main.getGraphicsManager().getGameCamera().position.x, 0);
			float speedX = distanceX / length;
			float distanceY = new Vector2(0, position.y).dst(0, Main.getGraphicsManager().getGameCamera().position.y);
			float speedY = distanceY / length;
			speed = new Vector2(speedX, speedY);
			speed.x *= Main.getGraphicsManager().getGameCamera().position.x > position.x ? -1 : 1;
			speed.y *= Main.getGraphicsManager().getGameCamera().position.y > position.y ? -1 : 1;
			
			float zoomDist = Main.getGraphicsManager().getGameCamera().zoom - zoom;
			zoomSpeed = zoomDist / length;
		}
	}
}
