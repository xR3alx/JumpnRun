package com.deldaryan.file;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.deldaryan.utils.DataObject;

public class SaveFile {

	private FileHandle file;
	private Json json;
	private DataObject data;
	
	public SaveFile() {
		file = Gdx.files.local("save.yml");
		
		try {
			if(!file.exists()) {
				file.file().createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		check();
	}

	public DataObject getData() {
		return data;
	}
	

	private void check() {
//		if(!data.ints.containsKey("key_moveleft")) {
//			data.ints.put("key_moveleft", Keys.A);
//		}
	}
	
	
	public void load() {
		data = json.fromJson(DataObject.class, file);
	}
	
	public void save() {
		json.toJson(data, file);
	}
}
