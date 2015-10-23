package com.deldaryan.screen.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deldaryan.main.Main;

public class LaunchScreen implements Screen {

	private Table table;
	private Image deldaryanLogo;
	
	
	@Override
	public void show() {
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
					new String[] {"deldaryan_games"}, // textures
					new String[] {}, // shaders
					new String[] {}, // particleeffects
					new String[] {}, // musics
					new String[] {}, // sounds
					new String[] {}  // maps
			);
		}
		createUI();
	}

	@Override
	public void render(float delta) {
		if(!deldaryanLogo.hasActions()) {
			Main.getScreenManager().changeScreenTo("main");
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
		
		
		deldaryanLogo = new Image(Main.getAssetLoader().getTexture("deldaryan_games"));
		deldaryanLogo.setScale(0.9f);
		deldaryanLogo.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1f, 1f), Actions.delay(3f), Actions.alpha(0f, 1f)));
		
		table.add(deldaryanLogo).expand().padLeft(20).padBottom(20).center();
		
		Main.getGraphicsManager().getStage().clear();
		Main.getGraphicsManager().getStage().addActor(table);
	}
}
