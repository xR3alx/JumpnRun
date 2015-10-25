package com.deldaryan.screen.screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deldaryan.main.Main;

public class SettingsScreen implements Screen, InputProcessor {

	private Table table;
	private Image titleBackgroundImage, buttonsBackgroungImage;
	private Label controlsLabel;
	private TextButton languageTextButton, backTextButton, controlLeftTextButton, controlRightTextButton, controlUpTextButton,
						controlDownTextButton, mastervolumeTextButton;
	
	private boolean listenToNextKey, changeToMain;
	private int controlListeningTo;
	
	@Override
	public void show() {
		changeToMain = false;
		createUI();
		Main.getGraphicsManager().getGameCamera().setCameraBorderBox(null);
		Main.getInputMultiplexer().addProcessor(this);
	}

	@Override
	public void render(float delta) {
		if(changeToMain) {
			if(!backTextButton.hasActions()) {
				Main.getScreenManager().changeScreenTo("main");
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
		
		
		titleBackgroundImage = new Image(Main.getAssetLoader().getTexture("title_bg"));
		buttonsBackgroungImage = new Image(Main.getAssetLoader().getTexture("menu_bg"));
		buttonsBackgroungImage.setScale(0.75f, 1.4f);
		buttonsBackgroungImage.setPosition(Main.UI_WIDTH / 2 - buttonsBackgroungImage.getWidth() * 0.75f / 2, -20);
		languageTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_LANG") + Main.getLanguageFile().getLangPrefix(), Main.getAssetLoader().getSkin(), "button14");
		mastervolumeTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_MASTERVOLUME") + Main.getConfigFile().get("mastervolume"), Main.getAssetLoader().getSkin(), "button14");
		controlsLabel = new Label(Main.getLanguageFile().get("SETTINGS_CONTROL"), Main.getAssetLoader().getSkin(), "trans14");
		controlLeftTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_LEFT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveleft"))), Main.getAssetLoader().getSkin(), "button14");
		controlRightTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_RIGHT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveright"))), Main.getAssetLoader().getSkin(), "button14");
		controlUpTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_UP") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveup"))), Main.getAssetLoader().getSkin(), "button14");
		controlDownTextButton = new TextButton(Main.getLanguageFile().get("SETTINGS_DOWN") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_movedown"))), Main.getAssetLoader().getSkin(), "button14");
		backTextButton = new TextButton(Main.getLanguageFile().get("BACK"), Main.getAssetLoader().getSkin(), "button14");
		
		buildTable();
		
		Main.getGraphicsManager().getStage().clear();
		Main.getGraphicsManager().getStage().addActor(titleBackgroundImage);
		Main.getGraphicsManager().getStage().addActor(buttonsBackgroungImage);
		Main.getGraphicsManager().getStage().addActor(table);
		

		UIClickListener clickListener = new UIClickListener();
		languageTextButton.addListener(clickListener);
		mastervolumeTextButton.addListener(clickListener);
		controlLeftTextButton.addListener(clickListener);
		controlRightTextButton.addListener(clickListener);
		controlUpTextButton.addListener(clickListener);
		controlDownTextButton.addListener(clickListener);
		backTextButton.addListener(clickListener);
		
		UIInputListener inputListener = new UIInputListener();
		languageTextButton.addListener(inputListener);
		mastervolumeTextButton.addListener(inputListener);
		controlLeftTextButton.addListener(inputListener);
		controlRightTextButton.addListener(inputListener);
		controlUpTextButton.addListener(inputListener);
		controlDownTextButton.addListener(inputListener);
		backTextButton.addListener(inputListener);
	}
	
	private class UIClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if(!listenToNextKey) {
				if(event.getListenerActor().equals(backTextButton)) {
					changeToMain = true;
				}
				else if(event.getListenerActor().equals(languageTextButton)) {
					int langId = 0;
					if(Main.getLanguageFile().getLangPrefix().equals("DE")) {
						langId = 1;
					}
					
					switch (langId) {
					case 0:
						Main.getLanguageFile().setLangPrefix("DE");
						break;
					case 1:
						Main.getLanguageFile().setLangPrefix("EN");
						break;
					default:
						break;
					}
					
					Main.getConfigFile().set("lang", Main.getLanguageFile().getLangPrefix());
					
					controlsLabel.setText(Main.getLanguageFile().get("SETTINGS_CONTROL"));
					languageTextButton.setText(Main.getLanguageFile().get("SETTINGS_LANG") + Main.getLanguageFile().getLangPrefix());
					mastervolumeTextButton.setText(Main.getLanguageFile().get("SETTINGS_MASTERVOLUME") + Main.getConfigFile().get("mastervolume"));
					controlLeftTextButton.setText(Main.getLanguageFile().get("SETTINGS_LEFT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveleft"))));
					controlRightTextButton.setText(Main.getLanguageFile().get("SETTINGS_RIGHT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveright"))));
					controlUpTextButton.setText(Main.getLanguageFile().get("SETTINGS_UP") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveup"))));
					controlDownTextButton.setText(Main.getLanguageFile().get("SETTINGS_DOWN") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_movedown"))));
					backTextButton.setText(Main.getLanguageFile().get("BACK"));
					
					buildTable();
				}
				else if(event.getListenerActor().equals(mastervolumeTextButton)) {
					float volume = Float.parseFloat(Main.getConfigFile().get("mastervolume"));
					
					if(volume >= 1) {
						volume = 0;
					}
					else {
						volume+=0.1f;
					}
					
					DecimalFormat format = new DecimalFormat("#.#");
					volume = Float.parseFloat(format.format(volume).replace(",", "."));
					
					Main.getConfigFile().set("mastervolume", volume);
					mastervolumeTextButton.setText(Main.getLanguageFile().get("SETTINGS_MASTERVOLUME") + Main.getConfigFile().get("mastervolume"));
				}
				else if(event.getListenerActor().equals(controlLeftTextButton)) {
					listenToNextKey = true;
					controlListeningTo = 0;
					controlLeftTextButton.setText(Main.getLanguageFile().get("SETTINGS_LEFT") + "> <");
				}
				else if(event.getListenerActor().equals(controlRightTextButton)) {
					listenToNextKey = true;
					controlListeningTo = 1;
					controlRightTextButton.setText(Main.getLanguageFile().get("SETTINGS_RIGHT") + "> <");
				}
				else if(event.getListenerActor().equals(controlUpTextButton)) {
					listenToNextKey = true;
					controlListeningTo = 2;
					controlUpTextButton.setText(Main.getLanguageFile().get("SETTINGS_UP") + "> <");
				}
				else if(event.getListenerActor().equals(controlDownTextButton)) {
					listenToNextKey = true;
					controlListeningTo = 3;
					controlDownTextButton.setText(Main.getLanguageFile().get("SETTINGS_DOWN") + "> <");
				}
			}
		}
	}
	
	private class UIInputListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			if(event.getListenerActor() instanceof TextButton) {
//				if(buttonPosX == 0) {
//					buttonPosX = new Float(languageTextButton.getX());
//				}
//				
//				event.getListenerActor().addAction(Actions.moveTo(
//						buttonPosX + 5,
//						event.getListenerActor().getY(),
//						0.125f));
//				Main.getAssetLoader().getSound("menu_hover").play(Float.parseFloat(Main.getConfigFile().get("mastervolume")) * 0.15f);
			}
		}
		
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			if(event.getListenerActor() instanceof TextButton) {
//				event.getListenerActor().addAction(Actions.moveTo(
//						buttonPosX,
//						event.getListenerActor().getY(),
//						0.125f));
			}
		}
	}
	
	
	
	
	
	

	
	@Override
	public boolean keyDown(int keycode) {
		if(listenToNextKey) {
			if(keycode == Keys.ESCAPE) {
				listenToNextKey = false;
			}
			else {
				if(controlListeningTo == 0) {
					Main.getConfigFile().set("key_moveleft", "" + keycode);
					listenToNextKey = false;
				}
				else if(controlListeningTo == 1) {
					Main.getConfigFile().set("key_moveright", "" + keycode);
					listenToNextKey = false;
				}
				else if(controlListeningTo == 2) {
					Main.getConfigFile().set("key_moveup", "" + keycode);
					listenToNextKey = false;
				}
				else if(controlListeningTo == 3) {
					Main.getConfigFile().set("key_movedown", "" + keycode);
					listenToNextKey = false;
				}
			}

			controlLeftTextButton.setText(Main.getLanguageFile().get("SETTINGS_LEFT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveleft"))));
			controlRightTextButton.setText(Main.getLanguageFile().get("SETTINGS_RIGHT") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveright"))));
			controlUpTextButton.setText(Main.getLanguageFile().get("SETTINGS_UP") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveup"))));
			controlDownTextButton.setText(Main.getLanguageFile().get("SETTINGS_DOWN") + Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_movedown"))));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	
	
	
	private void buildTable() {
		Table buttons = new Table();
		buttons.add(languageTextButton).padBottom(5).width(400).row();
		buttons.add(mastervolumeTextButton).padBottom(15).width(400).row();
		buttons.add(controlsLabel).padBottom(5).width(110).row();
		buttons.add(controlLeftTextButton).padBottom(5).width(400).row();
		buttons.add(controlRightTextButton).padBottom(5).width(400).row();
		buttons.add(controlUpTextButton).padBottom(5).width(400).row();
		buttons.add(controlDownTextButton).padBottom(15).width(400).row();
		buttons.add(backTextButton).width(400);
		
		table.clear();
		table.add(buttons).expand();
	}
}
