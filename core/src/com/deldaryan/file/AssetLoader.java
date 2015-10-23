package com.deldaryan.file;

import java.util.Collection;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deldaryan.main.Main;
import com.deldaryan.physic.WorldManager;

public class AssetLoader {

	private HashMap<String, TextureAtlas> atlas;
	private HashMap<String, ShaderProgram> shaders;
	private HashMap<String, Texture> textures;
	private HashMap<String, TiledMap> maps;
	private HashMap<String, ParticleEffect> particles;
	private HashMap<String, Music> music;
	private HashMap<String, Sound> sounds;
	
	private Skin skin;
	
	public AssetLoader() {
		skin = new Skin(Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "ui/skin.json"));
		atlas = new HashMap<String, TextureAtlas>();
		shaders = new HashMap<String, ShaderProgram>();
		textures = new HashMap<String, Texture>();
		maps = new HashMap<String, TiledMap>();
		particles = new HashMap<String, ParticleEffect>();
		music = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
	}
	
	
	public void load(String[] atlases, String[] animations, String[] textures, String[] shaders, String[] particleeffects, String[] musics, String[] sounds, String[] maps) {
		if(atlases.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "gfx/").list()) {
				if(file.extension().equals("pack") || file.extension().equals("atlas")) {
					for (String string : atlases) {
						if(!atlas.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								atlas.put(string, new TextureAtlas(file));
							}
						}
					}
				}
			}
		}
		
		if(animations.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "animations/").list()) {
				if(file.extension().equals("pack") || file.extension().equals("atlas")) {
					for (String string : animations) {
						if(!atlas.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								atlas.put(string, new TextureAtlas(file));
							}
						}
					}
				}
			}
		}
		
		if(shaders.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "shader/").list()) {
				if(file.extension().equals("vsh")) {
					FileHandle fragment = Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "shader/" + file.nameWithoutExtension() + ".fsh");
					if(fragment.exists()) {
						for (String string : shaders) {
							if(!this.shaders.containsKey(string)) {
								if(file.nameWithoutExtension().equals(string)) {
									ShaderProgram shader = new ShaderProgram(file, fragment);
									if(shader.isCompiled()) {
										this.shaders.put(string, shader);
									}
									else {
										System.out.println(shader.getLog());
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(textures.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "gfx/").list()) {
				if(file.extension().equals("png") || file.extension().equals("jpg")) {
					for (String string : textures) {
						if(!this.textures.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								this.textures.put(string, new Texture(file));
							}
						}
					}
				}
			}
		}

		if(maps.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "maps/").list()) {
				if(file.extension().equals("tmx")) {
					for (String string : maps) {
						if(file.nameWithoutExtension().equals(string)) {
							this.maps.put(string, new TmxMapLoader().load(file.path()));
						}
					}
				}
			}
		}
		
		if(particleeffects.length != 0) {
			FileHandle imageDir = Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "particles/imgs/");
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "particles/").list()) {
				if(file.extension().equals("p")) {
					for (String string : particleeffects) {
						if(!particles.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								ParticleEffect particleEffect = new ParticleEffect();
								particleEffect.load(file, imageDir);
								particleEffect.setDuration(0);
								particleEffect.scaleEffect(1f / (WorldManager.PIXELS_PER_METER * 10));
								particles.put(string, particleEffect);
							}
						}
					}
				}
			}
		}
		
		if(musics.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "audio/music/").list()) {
				if(file.extension().equals("ogg")) {
					for (String string : musics) {
						if(!music.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								music.put(string, Gdx.audio.newMusic(file));
							}
						}
					}
				}
			}
		}
		
		if(sounds.length != 0) {
			for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "audio/sounds/").list()) {
				if(file.extension().equals("ogg")) {
					for (String string : sounds) {
						if(!this.sounds.containsKey(string)) {
							if(file.nameWithoutExtension().equals(string)) {
								this.sounds.put(file.nameWithoutExtension(), Gdx.audio.newSound(file));
							}
						}
					}
				}
			}
		}
	}
	
	
	public void unloadAll(String checkLastScreen) {
		if(Main.getScreenManager().getLastScreen().equals(checkLastScreen)) {
			unloadAllAtlases();
			unloadAllTextures();
			unloadAllTiledMaps();
			unloadAllParticleEffects();
			unloadAllMusic();
			unloadAllSounds();
			unloadAllShaders();
		}
	}
	
	public void unloadAllAtlases() {
		for (String string : atlas.keySet()) {
			atlas.get(string).dispose();
		}
		atlas.clear();
	}
	
	public void unloadAllTextures() {
		for (String string : textures.keySet()) {
			textures.get(string).dispose();
		}
		textures.clear();
	}
	
	public void unloadAllTiledMaps() {
		for (String string : maps.keySet()) {
			maps.get(string).dispose();
		}
		maps.clear();
	}

	public void loadAllTiledMaps() {
		for (FileHandle file : Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "maps/").list()) {
			if(file.extension().equals("tmx")) {
				if(!maps.containsKey(file.nameWithoutExtension())) {
					maps.put(file.nameWithoutExtension(), new TmxMapLoader().load(file.path()));
				}
			}
		}
	}
	
	
	public void unloadAllParticleEffects() {
		for (String string : particles.keySet()) {
			particles.get(string).dispose();
		}
		particles.clear();
	}
	
	public void unloadAllMusic() {
		for (String string : music.keySet()) {
			music.get(string).dispose();
		}
		music.clear();
	}
	
	public void unloadAllSounds() {
		for (String string : sounds.keySet()) {
			sounds.get(string).dispose();
		}
		sounds.clear();
	}
	
	public void unloadAllShaders() {
		for (String string : shaders.keySet()) {
			shaders.get(string).dispose();
		}
		shaders.clear();
	}
	
	
	
	public ShaderProgram getShader(String key) {
		return shaders.get(key);
	}
	
	public boolean hasShader(String key) {
		return shaders.containsKey(key);
	}
	
	public Collection<ShaderProgram> getAllShaders() {
		return this.shaders.values();
	}
	
	
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public boolean hasTexture(String key) {
		return textures.containsKey(key);
	}
	
	public Collection<Texture> getAllTextures() {
		return this.textures.values();
	}
	
	
	
	public TiledMap getTiledMap(String key) {
		return maps.get(key);
	}
	
	public boolean hasTiledMap(String key) {
		return maps.containsKey(key);
	}
	
	
	
	public TextureAtlas getAtlas(String key) {
		return atlas.get(key);
	}
	
	public boolean hasAtlas(String key) {
		return atlas.containsKey(key);
	}
	
	public Collection<TextureAtlas> getAllTextureAtlases() {
		return this.atlas.values();
	}
	
	
	
	public ParticleEffect getParticleEffect(String key) {
		return particles.get(key);
	}
	
	public boolean hasParticleEffect(String key) {
		return particles.containsKey(key);
	}
	
	public Collection<ParticleEffect> getAllParticleEffects() {
		return this.particles.values();
	}
	
	
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public boolean hasMusic(String key) {
		return music.containsKey(key);
	}
	
	
	
	
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	
	public boolean hasSound(String key) {
		return sounds.containsKey(key);
	}
	
	
	
	
	
	public void unloadTexture(String key) {
		if(hasTexture(key)) {
			textures.get(key).dispose();
			textures.remove(key);
		}
	}
	
	public void unloadTiledMap(String key) {
		if(hasTiledMap(key)) {
			maps.get(key).dispose();
			maps.remove(key);
		}
	}
	
	public void unloadAtlas(String key) {
		if(hasAtlas(key)) {
			atlas.get(key).dispose();
			atlas.remove(key);
		}
	}
	
	public void unloadParticle(String key) {
		if(hasParticleEffect(key)) {
			particles.get(key).dispose();
			particles.remove(key);
		}
	}
	
	public void unloadMusic(String key) {
		if(hasMusic(key)) {
			music.get(key).dispose();
			music.remove(key);
		}
	}
	
	public void unloadSound(String key) {
		if(hasSound(key)) {
			sounds.get(key).dispose();
			sounds.remove(key);
		}
	}
	
	public void unloadShader(String key) {
		if(hasShader(key)) {
			shaders.get(key).dispose();
			shaders.remove(key);
		}
	}
	
	public Skin getSkin() {
		return skin;
	}
	

	
	
	public void showAll() {
		System.out.println("TextureAtlases/Animations: " + atlas);
		System.out.println("Textures: " + textures);
		System.out.println("Shaders: " + shaders);
		System.out.println("Tiled maps: " + maps);
		System.out.println("Particle effects: " + particles);
		System.out.println("Music: " + music);
		System.out.println("Sounds: " + sounds);
	}
	
	
	public void dispose() {
		for (Texture t : textures.values()) {
			t.dispose();
		}
		textures.clear();
		
		for (TiledMap m : maps.values()) {
			m.dispose();
		}
		maps.clear();
		
		for (TextureAtlas a : atlas.values()) {
			a.dispose();
		}
		atlas.clear();
		
		for (ParticleEffect p : particles.values()) {
			p.dispose();
		}
		particles.clear();
		
		for (Music m : music.values()) {
			m.dispose();
		}
		music.clear();
		
		for (Sound s : sounds.values()) {
			s.dispose();
		}
		sounds.clear();
		
		skin.dispose();
	}
	

}
