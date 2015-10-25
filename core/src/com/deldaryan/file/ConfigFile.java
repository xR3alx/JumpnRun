package com.deldaryan.file;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;

public class ConfigFile {

	private FileHandle file;
	private Properties props;
	
	public ConfigFile() {
		file = Gdx.files.local("config.yml");
		
		try {
			if(!file.exists()) {
				file.file().createNewFile();
			}
	
			props = new Properties();
			props.load(file.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		check();
	}

	public boolean has(String key) {
		return props.containsKey(key);
	}
	
	public String get(String key) {
		return props.getProperty(key);
	}
	
	public void set(String key, Object value) {
		props.setProperty(key, "" + value);
	}
	

	private void check() {
		if(!has("key_moveleft")) {
			set("key_moveleft", "" + Keys.A);
		}
		if(!has("key_moveright")) {
			set("key_moveright", "" + Keys.D);
		}
		if(!has("key_moveup")) {
			set("key_moveup", "" + Keys.W);
		}
		if(!has("key_jump")) {
			set("key_jump", "" + Keys.SPACE);
		}
		
		if(!has("window_width")) {
			set("window_width", "" + 1280);
		}
		if(!has("window_height")) {
			set("window_height", "" + 720);
		}
		if(!has("window_fullscreen")) {
			set("window_fullscreen", "" + false);
		}
		if(!has("window_hdpi_macosx")) {
			set("window_hdpi_macosx", "" + false);
		}
		if(!has("window_vsync")) {
			set("window_vsync", "" + false);
		}
		
		if(!has("lang")) {
			set("lang", "EN");
		}
		if(!has("mastervolume")) {
			set("mastervolume", "0.8");
		}
	}
	
	
	public void save() {
		try {
			props.store(file.write(false), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
