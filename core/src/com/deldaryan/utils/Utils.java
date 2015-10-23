package com.deldaryan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.deldaryan.main.Main;

public class Utils {

	public static Properties openFile(File file) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public static void saveFile(File file, Properties props) {
		try {
			props.store(new FileOutputStream(file), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String fillAliases(String string) {
		return string.replace("{KEY_LEFT}", Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveleft"))))
				.replace("{KEY_RIGHT}", Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_moveright"))))
				.replace("{KEY_JUMP}", Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_jump"))))
				.replace("{KEY_SHOOTNORMAL}", Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_shootnormal"))))
				.replace("{KEY_SHOOTSPECIAL}", Keys.toString(Integer.parseInt(Main.getConfigFile().get("key_shootspecial"))));
	}

	public static void playSound(String key, float volume) {
		Main.getAssetLoader().getSound(key).play(Utils.getVolumeWithMasterVolume(volume));
	}
	
	public static float getVolumeWithMasterVolume(float volume) {
		return Float.parseFloat(Main.getConfigFile().get("mastervolume")) * volume;
	}
	
	
	public static double lookAtMouse(Body body, Camera camera){
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);
		
		double degrees = (float) ((Math.atan2 (mousePos.x - body.getPosition().x, -(mousePos.y - body.getPosition().y))*180.0d/Math.PI)+270.0f);
		
		return degrees * MathUtils.degreesToRadians;
	}
}
