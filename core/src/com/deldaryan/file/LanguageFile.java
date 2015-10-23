package com.deldaryan.file;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.deldaryan.main.Main;

public class LanguageFile {

	private FileHandle file;
	private Properties props;
	
	private String langPrefix;
	
	public LanguageFile() {
		file = Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "ui/lang.yml");
		
		try {
			props = new Properties();
			props.load(file.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean has(String key) {
		return props.containsKey(key);
	}
	
	public String get(String key) {
		return props.getProperty(langPrefix + "_" + key);
	}
	
	
	public String getLangPrefix() {
		return langPrefix;
	}
	
	public void setLangPrefix(String langPrefix) {
		this.langPrefix = langPrefix;
	}
}
