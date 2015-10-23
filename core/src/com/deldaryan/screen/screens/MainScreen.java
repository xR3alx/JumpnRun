package com.deldaryan.screen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deldaryan.main.Main;

public class MainScreen implements Screen {

	private Table table;
	private TextButton playTextButton, settingsTextButton, exitTextButton;
	
	private int changeToMenu;
	
	
	@Override
	public void show() {
		changeToMenu = 0;
		
		if(Main.getScreenManager().getLastScreen().equals("game")
				|| Main.getScreenManager().getLastScreen().equals("")) {
			Main.getAssetLoader().unloadAllAtlases();
			Main.getAssetLoader().unloadAllParticleEffects();
			Main.getAssetLoader().unloadAllShaders();
			Main.getAssetLoader().unloadAllSounds();
			Main.getAssetLoader().unloadAllTextures();
			Main.getAssetLoader().unloadAllTiledMaps();
			Main.getAssetLoader().load(
					new String[] {}, // atlases
					new String[] {}, // animations
					new String[] {}, // SCML animations
					new String[] {}, // textures
					new String[] {}, // shaders
					new String[] {}, // particleeffects
					new String[] {}, // musics
					new String[] {}, // sounds
					new String[] {}  // maps
			);
		}
		createUI();
		Main.getGraphicsManager().getGameCamera().setCameraBorderBox(null);
		
//		if(!Main.getAssetLoader().getMusic("soundtrack").isPlaying()) {
//			Main.getAssetLoader().getMusic("soundtrack").setLooping(true);
//			Main.getAssetLoader().getMusic("soundtrack").play();
//		}
//		Main.getAssetLoader().getMusic("soundtrack").setVolume(Utils.getVolumeWithMasterVolume(0.75f));
	}

	@Override
	public void render(float delta) {
		if(changeToMenu != 0) {
			if(!exitTextButton.hasActions()) {
				if(changeToMenu == 1) {
					Main.getScreenManager().changeScreenTo("settings");
				}
				else if(changeToMenu == 2) {
					Main.getScreenManager().changeScreenTo("game");
				}
				else if(changeToMenu == 3) {
					Gdx.app.exit();
				}
			}
		}
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
	
	
	
	private void createUI() {
		table = new Table();
		table.setSize(Main.UI_WIDTH, Main.UI_HEIGHT);
		table.setBackground(Main.getAssetLoader().getSkin().getDrawable("bg1"));
		
//		labelAnimation = new AdvancedAnimation(1f / 4f,
//				Main.getAssetLoader().getAtlas("label_anim").getRegions(),
//				PlayMode.LOOP);
//		backgroundSprite = new AdvancedSprite(Main.getAssetLoader().getTexture("menubackground"));
		
		playTextButton = new TextButton(Main.getLanguageFile().get("MAIN_PLAY"), Main.getAssetLoader().getSkin(), "button16");
		settingsTextButton = new TextButton(Main.getLanguageFile().get("MAIN_SETTINGS"), Main.getAssetLoader().getSkin(), "button16");
		exitTextButton = new TextButton(Main.getLanguageFile().get("EXIT"), Main.getAssetLoader().getSkin(), "button16");
		
		Table buttons = new Table();
		buttons.add(playTextButton).padBottom(20).padTop(100).width(140).row();
		buttons.add(settingsTextButton).padBottom(20).padRight(30).width(140).row();
		buttons.add(exitTextButton).width(140).padLeft(20);
		
		table.add(buttons).expand();
		openAnimation();
		
		Main.getGraphicsManager().getStage().clear();
		Main.getGraphicsManager().getStage().addActor(table);
		
		
		UIClickListener clickListener = new UIClickListener();
		playTextButton.addListener(clickListener);
		settingsTextButton.addListener(clickListener);
		exitTextButton.addListener(clickListener);
		
		UIInputListener inputListener = new UIInputListener();
		playTextButton.addListener(inputListener);
		settingsTextButton.addListener(inputListener);
		exitTextButton.addListener(inputListener);
	}
	
	private class UIClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if(event.getListenerActor().equals(exitTextButton)) {
				closeAnimation();
				changeToMenu = 3;
			}
			else if(event.getListenerActor().equals(settingsTextButton)) {
				closeAnimation();
				changeToMenu = 1;
			}
			else if(event.getListenerActor().equals(playTextButton)) {
				closeAnimation();
				changeToMenu = 2;
			}
		}
	}
	
	private class UIInputListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			if(event.getListenerActor() instanceof TextButton) {
				if(!((TextButton) event.getListenerActor()).isDisabled()) {
//					Main.getAssetLoader().getSound("menu_hover").play(Float.parseFloat(Main.getConfigFile().get("mastervolume")) * 0.15f);
				}
			}
		}
		
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		}
	}
	
	
	
	
	
	
	
	
	
	public void openAnimation() {
		playTextButton.setVisible(false);
		playTextButton.addAction(Actions.sequence(Actions.hide(), Actions.moveBy(-200f, 0f), Actions.show(), Actions.moveBy(200f, 0f, 0.15f)));
		settingsTextButton.setVisible(false);
		settingsTextButton.addAction(Actions.sequence(Actions.hide(), Actions.moveBy(200f, 0f), Actions.delay(0.15f), Actions.show(), Actions.moveBy(-200f, 0f, 0.15f)));
		exitTextButton.setVisible(false);
		exitTextButton.addAction(Actions.sequence(Actions.hide(), Actions.moveBy(-200f, 0f), Actions.delay(0.3f), Actions.show(), Actions.moveBy(200f, 0f, 0.15f)));
	}
	
	public void closeAnimation() {
		playTextButton.addAction(Actions.sequence(Actions.moveBy(200f, 0f, 0.15f), Actions.hide()));
		settingsTextButton.addAction(Actions.sequence(Actions.delay(0.15f), Actions.moveBy(-200f, 0f, 0.15f), Actions.hide()));
		exitTextButton.addAction(Actions.sequence(Actions.delay(0.3f), Actions.moveBy(200f, 0f, 0.15f), Actions.hide()));
	}
}
