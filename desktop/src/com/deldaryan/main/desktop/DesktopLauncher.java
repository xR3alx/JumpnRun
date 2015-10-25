package com.deldaryan.main.desktop;

import java.io.File;
import java.util.Properties;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deldaryan.main.Main;
import com.deldaryan.utils.Utils;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Jump'n'Run";
		
		File file = new File("config.yml");
		if(file.exists()) {
			Properties props = Utils.openFile(file);
			
			config.width = Integer.parseInt(props.getProperty("window_width"));
			config.height = Integer.parseInt(props.getProperty("window_height"));
			config.fullscreen = Boolean.parseBoolean(props.getProperty("window_fullscreen"));
			config.useHDPI = Boolean.parseBoolean(props.getProperty("window_hdpi_macosx"));
			config.vSyncEnabled = Boolean.parseBoolean(props.getProperty("window_vsync"));
		}
		else {
			config.width = 1080;
			config.height = 720;
			config.fullscreen = false;
			config.useHDPI = false;
			config.vSyncEnabled = false;
		}
		config.resizable = false;
		
		new LwjglApplication(new Main("./bin/"), config);
	}
}
