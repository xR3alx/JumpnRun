package com.deldaryan.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.deldaryan.entity.component.AIComponent;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.VelocityComponent;
import com.deldaryan.entity.component.WeaponComponent;

public class AISystem extends IteratingSystem {

	public AISystem(Family family) {
		super(family);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		EntityComponent entityComp = entity.getComponent(EntityComponent.class);
		BodyComponent bodyComp = entity.getComponent(BodyComponent.class);
		VelocityComponent veloComp = entity.getComponent(VelocityComponent.class);
		WeaponComponent weaponComp = entity.getComponent(WeaponComponent.class);
		AIComponent aiCom = entity.getComponent(AIComponent.class);
		
//		Player player = (Player) Main.getEntityManager().getEntity("player");
		
//		if(!entityComp.isRemoved()
//				&& aiCom != null
//				&& entityComp.getHealth() > 0
//				&& !GameScreen.PAUSE
//				&& !GameScreen.CHANGE_TO_NEXT_MAP) {
//
//			if(entityComp.getEntityType() != EntityType.ENEMY_BOSS
//					|| !((GameScreen) Main.getScreenManager().getScreen("game")).isBossfight()) {
//				normalEntityAI(player, entityComp, aiCom, bodyComp, veloComp, weaponComp, deltaTime);
//			}
//			else {
//				bossAI(player, entityComp, aiCom, bodyComp, veloComp, weaponComp, deltaTime);
//			}
//		}
	}
	
	
	
//	private void normalEntityAI(final Player player, final EntityComponent entityComp, final AIComponent aiCom,
//			final BodyComponent bodyComp, final VelocityComponent veloComp, final WeaponComponent weaponComp,
//			float deltaTime) {
/////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// NORMAL AI ////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
//		if(aiCom.currentAIState == AIState.WAITING
//				|| aiCom.inLove) {
//			if(!aiCom.timerSet) {
//				aiCom.timerSet = true;
//				aiCom.stateTimer = MathUtils.random(50, 80);
//			}
//			else {
//				aiCom.stateTimer--;
//				
//				if(aiCom.stateTimer <= 0) {
//					aiCom.currentAIState = AIState.WALKING;
//					entityComp.setLookRight(MathUtils.random(0, 1));
//					aiCom.timerSet = false;
//				}
//			}
//		}
//		else if(aiCom.currentAIState == AIState.WALKING
//				&& !aiCom.inLove) {
//			if(!aiCom.timerSet) {
//				aiCom.timerSet = true;
//				aiCom.stateTimer = MathUtils.random(120, 180);
//			}
//			else {
//				aiCom.stateTimer--;
//				
//				if(aiCom.stateTimer <= 0) {
//					aiCom.currentAIState = AIState.WAITING;
//					aiCom.timerSet = false;
//				}
//			}
//		}
//		
//		
//
/////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// SHOULD ATTACK? /////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
//		float distanceToPlayer = bodyComp.getCurrentBody().getPosition().dst2(player.getComponent(BodyComponent.class).getCurrentBody().getPosition());
//		if(aiCom.currentAIState != AIState.ATTACKPLAYER) {
//			boolean makeRayCast = false;
//			if(entityComp.getLookRight() == 1
//					&& player.getComponent(BodyComponent.class).getCurrentBody().getPosition().x > bodyComp.getCurrentBody().getPosition().x) { makeRayCast = true; }
//			if(entityComp.getLookRight() == 0
//					&& player.getComponent(BodyComponent.class).getCurrentBody().getPosition().x < bodyComp.getCurrentBody().getPosition().x) { makeRayCast = true; }
//			
//			if(aiCom.inLove) {
//				makeRayCast = false;
//			}
//			
//			if(makeRayCast) {
//				if(distanceToPlayer < 10f
//						&& distanceToPlayer > 0.7f) {
//					if(Main.DEBUG) {
//						Main.getDebugDisplay().addDebugLine(new DebugLine(bodyComp.getCurrentBody().getPosition(), player.getComponent(BodyComponent.class).getCurrentBody().getPosition()));
//					}
//					RayCastCallback callback = new RayCastCallback() {
//						@Override
//						public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//							Body body = fixture.getBody();
//							if(body.getUserData() instanceof EntityUserData) {
//								Entity entity = Main.getEntityManager().getEntity(((EntityUserData) body.getUserData()).entityID);
//								
//								if(entity.getComponent(EntityComponent.class).getEntityType() == EntityType.PLAYER) {
//									aiCom.currentAIState = AIState.ATTACKPLAYER;
//								}
//							}
//							return 0;
//						}
//					};
//					Main.getWorldManager().getWorld().rayCast(callback, bodyComp.getCurrentBody().getPosition(), player.getComponent(BodyComponent.class).getCurrentBody().getPosition());
//				}
//			}
//		}
//		else {
//			if(player.getComponent(BodyComponent.class).getCurrentBody().getPosition().x > bodyComp.getCurrentBody().getPosition().x) {
//				entityComp.setLookRight(1);
//			}
//			else {
//				entityComp.setLookRight(0);
//			}
//			
//			RayCastCallback callback = new RayCastCallback() {
//				@Override
//				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//					Body body = fixture.getBody();
//					if(!(body.getUserData() instanceof EntityUserData)) {
//						aiCom.currentAIState = AIState.WALKING;
//					}
//					else {
//						Entity entity = Main.getEntityManager().getEntity(((EntityUserData) body.getUserData()).entityID);
//						
//						if(entity.getComponent(EntityComponent.class).getEntityType() != EntityType.PLAYER) {
//							aiCom.currentAIState = AIState.WALKING;
//						}
//					}
//					return 0;
//				}
//			};
//			Main.getWorldManager().getWorld().rayCast(callback, bodyComp.getCurrentBody().getPosition(), player.getComponent(BodyComponent.class).getCurrentBody().getPosition());
//		}
//		
//		
//		if(aiCom.currentAIState == AIState.WAITING) {
//			veloComp.setVelocity(0, 0);
//		}
/////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// WALKING ////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
//		else if(aiCom.currentAIState == AIState.WALKING) {
//			// up
//			Vector2 up = new Vector2(bodyComp.getCurrentBody().getPosition()).add(0.4f * (entityComp.getLookRight() == 1 ? 1 : -1), 1f);
//			if(Main.DEBUG) {
//				Main.getDebugDisplay().addDebugLine(new DebugLine(new Vector2(bodyComp.getCurrentBody().getPosition()).add(0f, 0.5f), up));
//			}
//			aiCom.canJump = true;
//			RayCastCallback callbackUp = new RayCastCallback() {
//				@Override
//				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//					Body body = fixture.getBody();
//					if(body.getUserData() instanceof GroundUserData) {
//						aiCom.canJump = false;
//					}
//					return 0;
//				}
//			};
//			Main.getWorldManager().getWorld().rayCast(callbackUp, new Vector2(bodyComp.getCurrentBody().getPosition()).add(0f, 0.5f), up);
//			
//			// straight
//			Vector2 straight = new Vector2(bodyComp.getCurrentBody().getPosition()).add(0.3f * (entityComp.getLookRight() == 1 ? 1 : -1), -0.15f);
//			if(Main.DEBUG) {
//				Main.getDebugDisplay().addDebugLine(new DebugLine(new Vector2(bodyComp.getCurrentBody().getPosition()).sub(0f, 0.15f), straight));
//			}
//			RayCastCallback callbackStraight = new RayCastCallback() {
//				@Override
//				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//					Body body = fixture.getBody();
//					if(body.getUserData() instanceof GroundUserData) {
//						if(!aiCom.canJump) {
//							entityComp.setLookRight(entityComp.getLookRight() == 0 ? 1 : 0);
//						}
//						else if(entityComp.isOnGround()
//								&& !entityComp.isJumping()) {
//							entityComp.setJumping(true);
//						}
//					}
//					else if(body.getUserData() instanceof TriggerUserData) {
//						if(Main.getTriggerManager().getTrigger(((TriggerUserData) body.getUserData()).id).getType() == TriggerType.TURN) {
//							entityComp.setLookRight(entityComp.getLookRight() == 0 ? 1 : 0);
//						}
//					}
//					return 0;
//				}
//			};
//			Main.getWorldManager().getWorld().rayCast(callbackStraight, new Vector2(bodyComp.getCurrentBody().getPosition()).sub(0f, 0.15f), straight);
//
//			veloComp.setVelocity(entityComp.getSpeed().x * deltaTime * (entityComp.getLookRight() == 1 ? 1 : -1), 0f);
//		}
/////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// ATTACKING ////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
//		else if(aiCom.currentAIState == AIState.ATTACKPLAYER) {
//			if(distanceToPlayer > 0.7f) {
//				// up
//				Vector2 up = new Vector2(bodyComp.getCurrentBody().getPosition()).add(0.4f * (entityComp.getLookRight() == 1 ? 1 : -1), 1f);
//				if(Main.DEBUG) {
//					Main.getDebugDisplay().addDebugLine(new DebugLine(new Vector2(bodyComp.getCurrentBody().getPosition()).add(0f, 0.5f), up));
//				}
//				aiCom.canJump = true;
//				RayCastCallback callbackUp = new RayCastCallback() {
//					@Override
//					public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//						Body body = fixture.getBody();
//						if(body.getUserData() instanceof EntityUserData
//								|| body.getUserData() instanceof GroundUserData) {
//							aiCom.canJump = false;
//						}
//						return 0;
//					}
//				};
//				Main.getWorldManager().getWorld().rayCast(callbackUp, new Vector2(bodyComp.getCurrentBody().getPosition()).add(0f, 0.5f), up);
//				
//				// straight
//				Vector2 straight = new Vector2(bodyComp.getCurrentBody().getPosition()).add(0.3f * (entityComp.getLookRight() == 1 ? 1 : -1), -0.15f);
//				if(Main.DEBUG) {
//					Main.getDebugDisplay().addDebugLine(new DebugLine(new Vector2(bodyComp.getCurrentBody().getPosition()).sub(0f, 0.15f), straight));
//				}
//				RayCastCallback callbackStraight = new RayCastCallback() {
//					@Override
//					public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//						Body body = fixture.getBody();
//						if(body.getUserData() instanceof GroundUserData) {
//							if(!aiCom.canJump) {
//								entityComp.setLookRight(entityComp.getLookRight() == 0 ? 1 : 0);
//							}
//							else if(entityComp.isOnGround()
//									&& !entityComp.isJumping()) {
//								entityComp.setJumping(true);
//							}
//						}
//						else if(body.getUserData() instanceof TriggerUserData) {
//							if(Main.getTriggerManager().getTrigger(((TriggerUserData) body.getUserData()).id).getType() == TriggerType.TURN) {
//								entityComp.setLookRight(entityComp.getLookRight() == 0 ? 1 : 0);
//								aiCom.currentAIState = AIState.WALKING;
//							}
//						}
//						return 0;
//					}
//				};
//				Main.getWorldManager().getWorld().rayCast(callbackStraight, new Vector2(bodyComp.getCurrentBody().getPosition()).sub(0f, 0.15f), straight);
//
//				veloComp.setVelocity(entityComp.getSpeed().x * deltaTime * (entityComp.getLookRight() == 1 ? 1 : -1), 0f);
//			}
//			else {
//				veloComp.setVelocity(0, 0f);
//			}
//
/////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// SHOOT AI ////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
//			if(distanceToPlayer < 10f) {
//				Vector2 straight = new Vector2(player.getComponent(BodyComponent.class).getCurrentBody().getPosition().x, bodyComp.getCurrentBody().getPosition().y);
//				if(Main.DEBUG) {
//					Main.getDebugDisplay().addDebugLine(new DebugLine(bodyComp.getCurrentBody().getPosition(), straight));
//				}
//				RayCastCallback callbackStraight = new RayCastCallback() {
//					@Override
//					public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//						Body body = fixture.getBody();
//						if(body.getUserData() instanceof EntityUserData) {
//							if(aiCom.canJump && entityComp.isOnGround()
//									&& !entityComp.isJumping()) {
//								Entity entity = Main.getEntityManager().getEntity(((EntityUserData) body.getUserData()).entityID);
//								
//								if(entity.getComponent(EntityComponent.class).getEntityType() == EntityType.PLAYER) {
//									weaponComp.getWeapon("normal").fire(bodyComp.getCurrentBody().getPosition(), entityComp.getLookRight(), 8f);
//								}
//							}
//						}
//						return 0;
//					}
//				};
//				Main.getWorldManager().getWorld().rayCast(callbackStraight, new Vector2(bodyComp.getCurrentBody().getPosition()), straight);
//			}
//		}
//	}
}
