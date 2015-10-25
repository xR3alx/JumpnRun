package com.deldaryan.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class EntityComponent implements Component {

	private String identifier = "";
	private int entityType, stepTime;
	private float health, maxHealth, jumpStrength, jumpLength, jumpTime, timeExist;
	private boolean isOnGround, jumping, removed, removeWhenHeal0, remove, fallen, jumpSoundPlayed, canJump;
	private Vector2 speed;
	
	public EntityComponent(int entityType, float health, float maxHealth, Vector2 speed) {
		this.entityType = entityType;
		this.health = health;
		this.maxHealth = maxHealth;
		this.speed = speed;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public float getJumpStrength() {
		return jumpStrength;
	}

	public float getJumpLength() {
		return jumpLength;
	}

	public float getJumpTime() {
		return jumpTime;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public boolean isJumping() {
		return jumping;
	}

	public boolean isFallen() {
		return fallen;
	}

	public boolean isJumpSoundPlayed() {
		return jumpSoundPlayed;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public void setJumpStrength(float jumpStrength) {
		this.jumpStrength = jumpStrength;
	}

	public void setJumpLength(float jumpLength) {
		this.jumpLength = jumpLength;
	}
	
	public void setJumpTime(float jumpTime) {
		this.jumpTime = jumpTime;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setFallen(boolean fallen) {
		this.fallen = fallen;
	}

	public void setJumpSoundPlayed(boolean jumpSoundPlayed) {
		this.jumpSoundPlayed = jumpSoundPlayed;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	
	public boolean isRemove() {
		return remove;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public int getEntityType() {
		return entityType;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	public Vector2 getSpeed() {
		return speed;
	}
	
	public void setSpeed(Vector2 speed) {
		this.speed = speed;
	}
	
	public float getTimeExist() {
		return timeExist;
	}
	
	public void setTimeExist(float timeExist) {
		this.timeExist = timeExist;
	}
	
	public boolean isRemoveWhenHeal0() {
		return removeWhenHeal0;
	}
	
	public void setRemoveWhenHeal0(boolean removeWhenHeal0) {
		this.removeWhenHeal0 = removeWhenHeal0;
	}
	
	public int getStepTime() {
		return stepTime;
	}
	
	public void setStepTime(int stepTime) {
		this.stepTime = stepTime;
	}
}
