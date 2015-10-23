package com.deldaryan.screen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deldaryan.entity.component.BodyComponent;
import com.deldaryan.entity.component.EntityComponent;
import com.deldaryan.entity.component.VelocityComponent;
import com.deldaryan.entity.component.WeaponComponent;
import com.deldaryan.entity.entities.Player;
import com.deldaryan.main.Main;
import com.deldaryan.utils.Utils;

public class GameScreen implements Screen, ContactListener {

	private Table table, pauseTable, gameOverTable, bigTextTable;
	private TextButton restartTextButton, restart2TextButton, continueTextButton, mainMenuTextButton, mainMenu2TextButton, closeBigTextTextButton;
	private ProgressBar healthProgressBar, bossProgressBar;
	private Label pauseLabel, gameOverLabel, bigTextLabel;
	private Image bigTextImage;
	
	private GameUI gameUI;
	
	private int lastHealth;
	private boolean bossfight;
	
	public static boolean PAUSE = false;
	public static boolean CHANGE_TO_NEXT_MAP = false;
	
	
	
	@Override
	public void show() {
		Main.getGraphicsManager().getGameCamera().setMessureSpeed(false);
		PAUSE = false;
		CHANGE_TO_NEXT_MAP = false;
		Main.getSessionData().booleans.remove("finished_" + Main.getConfigFile().get("gameprogress") + "_animation");
		Main.getSessionData().booleans.remove("finished_" + Main.getConfigFile().get("gameprogress"));
		Main.getGraphicsManager().getGameCamera().clearSequences();
		
		Main.getAssetLoader().unloadAllAtlases();
		Main.getAssetLoader().unloadAllParticleEffects();
		Main.getAssetLoader().unloadAllShaders();
		Main.getAssetLoader().unloadAllTextures();
		Main.getAssetLoader().unloadAllTiledMaps();
		Main.getAssetLoader().load(
				new String[] {"projectiles", "doors", "story_imgs"}, // atlases
				new String[] {"enemy", "enemy_shield", "enemy_boss", "player", "love_particle", "pickups"}, // animations
				new String[] {}, // SCML animations
				new String[] {"gamebackground", "platform"}, // textures
				new String[] {}, // shaders
				new String[] {"blood"}, // particleeffects
				new String[] {}, // musics
				new String[] {"jump", "key_pickup", "menu_hover", "normalprojectile_hit", "specialprojectile_hit", "step", "player_damage"}, // sounds
				new String[] {} // maps
		);
		Main.getAssetLoader().loadAllTiledMaps();
		Main.getMapManager().reset();
		Main.getMapManager().load();

		Main.getGraphicsManager().getGameCamera().zoom = 1f;

		Main.getWorldManager().getWorld().setContactListener(this);
		
		gameUI = new GameUI();
		gameUI.createUI();
	}

	@Override
	public void render(float delta) {
		if(!PAUSE
				&& !CHANGE_TO_NEXT_MAP) {
			updateInput();
			Main.getWorldManager().update();
		}
		Main.getMapManager().setLayersToRender(new int[] {0, 1});
		Main.getMapManager().render();
		Main.getEntityManager().render();
		Main.getWorldManager().render();
		
		updateGame();
		gameUI.updateUI();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
	
	

	
	
	public void updateGame() {
//		TextureMapObject backgroundObject = ((TextureMapObject) Main.getMapManager().getTiledMap().getLayers().get("background").getObjects().get(0));
//		backgroundObject.setX(
//				(Main.getGraphicsManager().getGameCamera().position.x * WorldManager.PIXELS_PER_METER) - backgroundObject.getProperties().get("width", Float.class) / 2
//			);
//		backgroundObject.setY(
//				(Main.getGraphicsManager().getGameCamera().position.y * WorldManager.PIXELS_PER_METER) - backgroundObject.getProperties().get("height", Float.class) / 2
//			);
		
//		if(Main.getEntityManager().getEntity("player").getComponent(EntityComponent.class).getHealth() <= 0
//				&& !Main.getSessionData().booleans.containsKey("finished_" + Main.getConfigFile().get("gameprogress"))) {
//			Main.getGraphicsManager().getGameCamera().setBodyToFollow(null);
//			Main.getGraphicsManager().getGameCamera().setCameraBorderBox(null);
//			Main.getSessionData().booleans.put("finished_" + Main.getConfigFile().get("gameprogress"), true);
//			
//			Main.getGraphicsManager().getGameCamera().addSequence(new CameraSequence(
//					Main.getEntityManager().getEntity("player").getComponent(BodyComponent.class).getCurrentBody().getPosition()
//						.add(0, 0.45f),
//					0.4f,
//					40f));
//		}
		
//		if(CHANGE_TO_NEXT_MAP
//				&& !Main.getSessionData().booleans.containsKey("finished_" + Main.getConfigFile().get("gameprogress") + "_animation")) {
//			Main.getSessionData().booleans.put("finished_" + Main.getConfigFile().get("gameprogress") + "_animation", true);
//			Main.getGraphicsManager().getGameCamera().setBodyToFollow(null);
//			Main.getGraphicsManager().getGameCamera().setCameraBorderBox(null);
//			
//			Main.getGraphicsManager().getGameCamera().addSequence(new CameraSequence(
//					new Vector2(Main.getEntityManager().getEntity("player").getComponent(BodyComponent.class).getCurrentBody().getPosition())
//						.add(0, 0.8f),
//					0.45f,
//					40f));
//			Main.getGraphicsManager().getGameCamera().addSequence(new CameraSequence(
//					new Vector2(Main.getEntityManager().getEntity("player").getComponent(BodyComponent.class).getCurrentBody().getPosition())
//						.add(0, 0.8f),
//					0.45f,
//					Float.MAX_VALUE));
//		}
//		
//		
//		
//		if(lastHealth > Main.getEntityManager().getEntity("player").getComponent(EntityComponent.class).getHealth()) {
//			Utils.playSound("player_damage", 0.6f);
//		}
//		lastHealth = (int) Main.getEntityManager().getEntity("player").getComponent(EntityComponent.class).getHealth();
	}
	
	
	
	private void updateInput() {
		Player player = (Player) Main.getEntityManager().getEntity("player");
		
		if(player.getComponent(EntityComponent.class).getHealth() > 0
				&& !PAUSE
				&& !CHANGE_TO_NEXT_MAP) {
			if(Gdx.input.isKeyPressed(Integer.parseInt(Main.getConfigFile().get("key_moveleft")))) {
				player.getComponent(VelocityComponent.class).setVelocity(
						-player.getComponent(EntityComponent.class).getSpeed().x * Gdx.graphics.getDeltaTime(),
						player.getComponent(VelocityComponent.class).getVelocity().y);
			}
			else if(Gdx.input.isKeyPressed(Integer.parseInt(Main.getConfigFile().get("key_moveright")))) {
				player.getComponent(VelocityComponent.class).setVelocity(
						player.getComponent(EntityComponent.class).getSpeed().x * Gdx.graphics.getDeltaTime(),
						player.getComponent(VelocityComponent.class).getVelocity().y);
			}
			else {
				player.getComponent(VelocityComponent.class).setVelocity(
						0,
						player.getComponent(VelocityComponent.class).getVelocity().y);
			}
			
			if(Gdx.input.isKeyPressed(Integer.parseInt(Main.getConfigFile().get("key_moveup")))) {
				player.getComponent(VelocityComponent.class).setVelocity(
						player.getComponent(VelocityComponent.class).getVelocity().x,
						player.getComponent(EntityComponent.class).getSpeed().y * Gdx.graphics.getDeltaTime());
			}
			else if(Gdx.input.isKeyPressed(Integer.parseInt(Main.getConfigFile().get("key_movedown")))) {
				player.getComponent(VelocityComponent.class).setVelocity(
						player.getComponent(VelocityComponent.class).getVelocity().x,
						-player.getComponent(EntityComponent.class).getSpeed().y * Gdx.graphics.getDeltaTime());
			}
			else {
				player.getComponent(VelocityComponent.class).setVelocity(
						player.getComponent(VelocityComponent.class).getVelocity().x,
						0);
			}
			
			
			
			
			if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				table.addAction(Actions.color(new Color(1f, 1f, 1f, 0.5f), 0.7f));
				showPauseTable();
			}
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void beginContact(Contact contact) {
		Body body1 = contact.getFixtureA().getBody();
		Body body2 = contact.getFixtureB().getBody();
		
		for (Body bodyA : new Body[] {body1, body2}) {
			for (Body bodyB : new Body[] {body1, body2}) {
				if(bodyA != bodyB) {
					Object objA = bodyA.getUserData();
					Object objB = bodyB.getUserData();
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Body body1 = contact.getFixtureA().getBody();
		Body body2 = contact.getFixtureB().getBody();
		
		for (Body bodyA : new Body[] {body1, body2}) {
			for (Body bodyB : new Body[] {body1, body2}) {
				if(bodyA != bodyB) {
					Object objA = bodyA.getUserData();
					Object objB = bodyB.getUserData();
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void showPauseTable() {
		pauseLabel.addAction(Actions.sequence(Actions.moveBy(-200f, 0f), Actions.show(), Actions.moveBy(200f, 0f, 0.25f)));
		continueTextButton.addAction(Actions.sequence(Actions.moveBy(200f, 0f), Actions.delay(0.15f), Actions.show(), Actions.moveBy(-200f, 0f, 0.25f)));
		restartTextButton.addAction(Actions.sequence(Actions.moveBy(-200f, 0f), Actions.delay(0.3f), Actions.show(), Actions.moveBy(200f, 0f, 0.25f)));
		mainMenuTextButton.addAction(Actions.sequence(Actions.moveBy(200f, 0f), Actions.delay(0.45f), Actions.show(), Actions.moveBy(-200f, 0f, 0.25f)));
		
		pauseTable.setVisible(true);
		PAUSE = true;
	}
	
	public void hidePauseTable() {
		pauseLabel.addAction(Actions.sequence(Actions.moveBy(200f, 0f, 0.25f), Actions.hide(), Actions.moveBy(-200f, 0f)));
		continueTextButton.addAction(Actions.sequence(Actions.delay(0.15f), Actions.moveBy(-200f, 0f, 0.25f), Actions.hide(), Actions.moveBy(200f, 0f)));
		restartTextButton.addAction(Actions.sequence(Actions.delay(0.3f), Actions.moveBy(200f, 0f, 0.25f), Actions.hide(), Actions.moveBy(-200f, 0f)));
		mainMenuTextButton.addAction(Actions.sequence(Actions.delay(0.45f), Actions.moveBy(-200f, 0f, 0.25f), Actions.hide(), Actions.moveBy(200f, 0f),
				Actions.run(
						new Runnable() {
							public void run() {
								pauseTable.setVisible(false);
								PAUSE = false;
							}
						})
		));
	}
	
	
	public void displayBigText(String link, String imgName) {
		PAUSE = true;
		
		if(Main.getAssetLoader().hasAtlas("story_imgs")) {
			if(Main.getAssetLoader().getAtlas("story_imgs").findRegion(imgName) != null) {
				bigTextImage.setDrawable(new TextureRegionDrawable(Main.getAssetLoader().getAtlas("story_imgs").findRegion(imgName)));
			}
		}
		
		bigTextLabel.setText(Utils.fillAliases(Main.getLanguageFile().get(link)));
		bigTextTable.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.7f)));
		table.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.hide()));
	}
	
	
	private class GameUI {
		
		private int changeToMenu;
		
		public void createUI() {
			changeToMenu = 0;
			
			//////////////////////////////////////////////////////////////// INGAME //////////////////////////////////////////////////////
			table = new Table();
			table.setSize(Main.UI_WIDTH, Main.UI_HEIGHT);
			
			healthProgressBar = new ProgressBar(0, 10f, 0.1f, false, Main.getAssetLoader().getSkin(), "health");
			bossProgressBar = new ProgressBar(0, 10f, 0.1f, false, Main.getAssetLoader().getSkin(), "health");
			bossProgressBar.setVisible(false);
			
			Table upperTable = new Table();
			upperTable.add(healthProgressBar).expand().right().width(50f).height(12f);
			
			table.add(upperTable).expandX().top().left().fillX().row();
			table.add(bossProgressBar).expand().width(Main.WORLD_WIDTH).top().fillX().row();
			table.setVisible(false);
			Main.getGraphicsManager().getStage().clear();
			Main.getGraphicsManager().getStage().addActor(table);
			
			
			///////////////////////////////////////////////////////////////// PAUSE //////////////////////////////////////////////////////////////
			pauseTable = new Table();
			pauseTable.setSize(Main.UI_WIDTH, Main.UI_HEIGHT);
			pauseTable.setBackground(Main.getAssetLoader().getSkin().getDrawable("bg1"));
			
			pauseLabel = new Label(Main.getLanguageFile().get("INGAME_PAUSE"), Main.getAssetLoader().getSkin(), "trans18");
			continueTextButton = new TextButton(Main.getLanguageFile().get("CONTINUEGAME"), Main.getAssetLoader().getSkin(), "button16");
			restartTextButton = new TextButton(Main.getLanguageFile().get("INGAME_RESTARTGAME"), Main.getAssetLoader().getSkin(), "button16");
			mainMenuTextButton = new TextButton(Main.getLanguageFile().get("INGAME_MAINMENU"), Main.getAssetLoader().getSkin(), "button16");
			
			pauseLabel.setVisible(false);
			continueTextButton.setVisible(false);
			restartTextButton.setVisible(false);
			mainMenuTextButton.setVisible(false);
			
			
			Table buttons = new Table();
			buttons.add(pauseLabel).padBottom(15).row();
			buttons.add(continueTextButton).padBottom(15f).padLeft(15).row();
			buttons.add(restartTextButton).padBottom(15f).padRight(25).row();
			buttons.add(mainMenuTextButton).padBottom(15f).padLeft(10).row();
			
			pauseTable.add(buttons).expand();
			pauseTable.setVisible(false);
			Main.getGraphicsManager().getStage().addActor(pauseTable);
			
			
			////////////////////////////////////////////////////////////////// GAME OVER ///////////////////////////////////////////////////////////////////
			gameOverTable = new Table();
			gameOverTable.setSize(Main.UI_WIDTH, Main.UI_HEIGHT);
			gameOverTable.setBackground(Main.getAssetLoader().getSkin().getDrawable("bg1"));
			
			gameOverLabel = new Label(Main.getLanguageFile().get("INGAME_GAMEOVER"), Main.getAssetLoader().getSkin(), "trans18");
			restart2TextButton = new TextButton(Main.getLanguageFile().get("INGAME_RESTARTGAME"), Main.getAssetLoader().getSkin(), "button16");
			mainMenu2TextButton = new TextButton(Main.getLanguageFile().get("INGAME_MAINMENU"), Main.getAssetLoader().getSkin(), "button16");
			
			Table buttons2 = new Table();
			buttons2.add(gameOverLabel).padBottom(15).row();
			buttons2.add(restart2TextButton).padBottom(7.5f).row();
			buttons2.add(mainMenu2TextButton).padBottom(7.5f).row();
			
			gameOverTable.add(buttons2).expand();
			gameOverTable.setVisible(false);
			Main.getGraphicsManager().getStage().addActor(gameOverTable);
			
			
			
			/////////////////////////////////////////////////////////////// BIG TEXT /////////////////////////////////////////////////////////////////////////////////
//			bigTextTable = new Table();
//			bigTextTable.setSize(Main.UI_WIDTH, Main.UI_WIDTH);
//			
//			bigTextImage = new Image();
//			bigTextLabel = new Label("", Main.getAssetLoader().getSkin(), "trans16");
//			bigTextLabel.setAlignment(Align.topLeft);
//			bigTextLabel.setWrap(true);
//			closeBigTextTextButton = new TextButton(Main.getLanguageFile().get("INGAME_CLOSESTORY"), Main.getAssetLoader().getSkin(), "trans14");
//			
//			Table table = new Table();
//			table.setBackground(Main.getAssetLoader().getSkin().getDrawable("bg1"));
//			
//			table.add(bigTextImage).padBottom(1f).row();
//			table.add(bigTextLabel).width(Main.WORLD_WIDTH-8).maxHeight(Main.WORLD_HEIGHT-19).row();
//			table.add(closeBigTextTextButton).row();
//			
//			bigTextTable.add(table).expand();
//			bigTextTable.setVisible(false);
//			Main.getGraphicsManager().getStage().addActor(bigTextTable);
			
			
			
			
			
			
			
			UIClickListener clickListener = new UIClickListener();
			continueTextButton.addListener(clickListener);
			restartTextButton.addListener(clickListener);
			restart2TextButton.addListener(clickListener);
			mainMenuTextButton.addListener(clickListener);
			mainMenu2TextButton.addListener(clickListener);
//			closeBigTextTextButton.addListener(clickListener);
			
			UIInputListener inputListener = new UIInputListener();
			continueTextButton.addListener(inputListener);
			restartTextButton.addListener(inputListener);
			restart2TextButton.addListener(inputListener);
			mainMenuTextButton.addListener(inputListener);
			mainMenu2TextButton.addListener(inputListener);
//			closeBigTextTextButton.addListener(inputListener);
		}
			
		private class UIClickListener extends ClickListener {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor().getColor().a != 0) {
					if(event.getListenerActor().equals(continueTextButton)) {
						table.addAction(Actions.color(new Color(1f, 1f, 1f, 1f), 0.7f));
						hidePauseTable();
					}
					else if(event.getListenerActor().equals(restartTextButton)
							|| event.getListenerActor().equals(restart2TextButton)) {
						Main.getScreenManager().changeScreenTo("game");
					}
					else if(event.getListenerActor().equals(mainMenu2TextButton)) {
						changeToMenu = 1;
						// TODO add hide game over
					}
					else if(event.getListenerActor().equals(mainMenuTextButton)) {
						changeToMenu = 1;
						hidePauseTable();
					}
					else if(event.getListenerActor().equals(closeBigTextTextButton)) {
						PAUSE = false;
						table.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.7f)));
						bigTextTable.addAction(Actions.sequence(Actions.fadeOut(0.7f), Actions.hide()));
					}
				}
			}
		}
			
		private class UIInputListener extends InputListener {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(event.getListenerActor() instanceof TextButton) {
//					Main.getAssetLoader().getSound("menu_hover").play(Float.parseFloat(Main.getConfigFile().get("mastervolume")) * 0.15f);
				}
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			}
		}
			
		public void updateUI() {
			
			if(changeToMenu == 1) {
				if(!mainMenuTextButton.hasActions()) {
					Main.getScreenManager().changeScreenTo("main");
				}
			}
			
			
//			Player player = (Player) Main.getEntityManager().getEntity("player");
//			
//			float percent = 100 * player.getComponent(EntityComponent.class).getHealth() / player.getComponent(EntityComponent.class).getMaxHealth();
//			healthProgressBar.setValue(percent * 0.1f);
//			
//			if(percent <= 0) {
//				table.setVisible(false);
//				pauseTable.setVisible(false);
//				finishedTable.setVisible(false);
//				
//				if(player.getComponent(EntityComponent.class).getMaxHealth() <= 1
//						|| checkpoint == null) {
//					restartAtCheckpointTextButton.setColor(0.6f, 0.6f, 0.6f, 0.4f);
//				}
//				gameOverTable.setVisible(true);
//			}
//			
//			
//			if(bossfight) {
//				Entity boss = Main.getEntityManager().getEntity("boss");
//				float percentBoss = 100 * boss.getComponent(EntityComponent.class).getHealth() / boss.getComponent(EntityComponent.class).getMaxHealth();
//				bossProgressBar.setValue(percentBoss * 0.1f);
//				
//				if(!finishedGameTable.isVisible()
//						&& boss.getComponent(EntityComponent.class).getHealth() <= 0) {
//					table.setVisible(false);
//					pauseTable.setVisible(false);
//					gameOverTable.setVisible(false);
//					storyTable.setVisible(false);
//					finishedGameTable.setVisible(true);
//				}
//			}
//			
//			
//			if(CHANGE_TO_NEXT_MAP) {
//				table.addAction(Actions.sequence(Actions.fadeOut(0.7f), Actions.hide()));
//				finishedTable.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.7f)));
//			}
		}
	}
}
