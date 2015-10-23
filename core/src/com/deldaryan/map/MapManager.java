package com.deldaryan.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.deldaryan.entity.EntityType;
import com.deldaryan.entity.entities.Player;
import com.deldaryan.main.Main;
import com.deldaryan.physic.CollisionFilter;
import com.deldaryan.physic.WorldManager;
import com.deldaryan.physic.userdata.GroundUserData;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class MapManager {

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private ShapeRenderer shapeRenderer;
	
	private int lastMap = 99999999;
	private int[] layersToRender = {0};

	public MapManager() {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
	}
	
	public void reset() {
		lastMap = 99999999;
	}
	
	public void load() {
		if(lastMap == 99999999) {
//			System.out.println(Main.getAssetLoader().getTiledMap("map_0"));
			tiledMap = Main.getAssetLoader().getTiledMap("map_0");
			lastMap = 0;
		}
		else {
			int randomNewMap = MathUtils.random(1, 2);
			while(randomNewMap == lastMap) {
				randomNewMap = MathUtils.random(1, 2);
			}
			tiledMap = Main.getAssetLoader().getTiledMap("map_" + randomNewMap);
			lastMap = randomNewMap;
		}
		
		if(renderer == null) {
			renderer = new OrthogonalTiledMapRenderer(tiledMap, 1f / WorldManager.PIXELS_PER_METER);
		}
		else {
			renderer.setMap(tiledMap);
		}

		
		Main.getWorldManager().removeAllBodiesAndLights();
		Main.getEntityManager().removeAll();
		loadMap();
	}


	public void render() {
		if(renderer != null) {
			float width = Main.getGraphicsManager().getGameCamera().viewportWidth + 1;
			float height = Main.getGraphicsManager().getGameCamera().viewportHeight + 0.5f;
			float x = Main.getGraphicsManager().getGameCamera().position.x - width / 2;
			float y = Main.getGraphicsManager().getGameCamera().position.y - height / 2;
			renderer.setView(Main.getGraphicsManager().getGameCamera().combined, x, y, width, height);
//			renderer.setView(Main.getGraphicsManager().getGameCamera());
			
			renderer.getBatch().begin();
			for (int i : layersToRender) {
				MapLayer layer = tiledMap.getLayers().get(i);
				
				if(layer instanceof TiledMapTileLayer) {
					renderer.renderTileLayer((TiledMapTileLayer) layer);
				}
				else {
					for(MapObject mapObject : layer.getObjects()) {
						if(mapObject instanceof TextureMapObject) {
							TextureMapObject texObj = (TextureMapObject) mapObject;

							if(Main.getGraphicsManager().getGameCamera().getCameraSpeed().x > 0.01f
									|| Main.getGraphicsManager().getGameCamera().getCameraSpeed().x < -0.01f) {
								float parallax_x = 0;
								if(mapObject.getProperties().containsKey("parallax_x")) {
									parallax_x = Float.parseFloat(mapObject.getProperties().get("parallax_x").toString());
								}
								texObj.setX(texObj.getX() - (Main.getGraphicsManager().getGameCamera().getCameraSpeed().x * parallax_x));
							}

							if(Main.getGraphicsManager().getGameCamera().getCameraSpeed().y > 0.01f
									|| Main.getGraphicsManager().getGameCamera().getCameraSpeed().y < -0.01f) {
								float parallax_y = 0;
								if(mapObject.getProperties().containsKey("parallax_y")) {
									parallax_y = Float.parseFloat(mapObject.getProperties().get("parallax_y").toString());
								}
								texObj.setY(texObj.getY() - (Main.getGraphicsManager().getGameCamera().getCameraSpeed().y * parallax_y));
							}
							
							Sprite sprite = new Sprite(texObj.getTextureRegion());
							sprite.setSize(
									texObj.getProperties().get("width", Float.class) / WorldManager.PIXELS_PER_METER,
									texObj.getProperties().get("height", Float.class) / WorldManager.PIXELS_PER_METER);
							sprite.setPosition(
									(texObj.getX() / WorldManager.PIXELS_PER_METER),
									(texObj.getY() / WorldManager.PIXELS_PER_METER));
							
							if(mapObject.getProperties().containsKey("tint")) {
								String[] splited = mapObject.getProperties().get("tint").toString().split(",");
								float red = Float.parseFloat(splited[0]);
								float green = Float.parseFloat(splited[1]);
								float blue = Float.parseFloat(splited[2]);
								float alpha = Float.parseFloat(splited[3]);
								
								sprite.setColor(red, green, blue, alpha);
							}
							
							sprite.draw(renderer.getBatch());
						}
					}
				}
			}
			renderer.getBatch().end();
		}
	}
	
	
	
	private void loadMap() {
		new Box2DMapObjectParser(renderer.getUnitScale()).load(Main.getWorldManager().getWorld(), tiledMap.getLayers().get("collision"));
		Array<Body> bodies = new Array<Body>();
		Main.getWorldManager().getWorld().getBodies(bodies);
		Filter filter = new Filter();
		filter.categoryBits = CollisionFilter.CATEGORY_MAP;
		
		for (Body body : bodies) {
			body.setUserData(new GroundUserData());
			
			for (Fixture fix : body.getFixtureList()) {
				fix.setFilterData(filter);
			}
		}
		
		String[] splited = tiledMap.getProperties().get("rgba").toString().split(",");
		
		Main.getWorldManager().getRayHandler().setAmbientLight(
				Float.parseFloat(splited[0]),
				Float.parseFloat(splited[1]),
				Float.parseFloat(splited[2]),
				Float.parseFloat(splited[3]));
		
		
		
		
		for (MapObject obj : tiledMap.getLayers().get("entities").getObjects()) {
			if(obj instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				Vector2 position = new Vector2((rect.x + rect.width / 2) / WorldManager.PIXELS_PER_METER, (rect.y + rect.height / 2) / WorldManager.PIXELS_PER_METER);
				int type = Integer.parseInt(obj.getProperties().get("type").toString());
				
				switch (type) {
				case EntityType.PLAYER:
					new Player(position, Main.getEntityManager().getEngine());
					break;
				default:
					break;
				}
			}
		}
	}
	
	
	public void setLayersToRender(int[] layersToRender) {
		this.layersToRender = layersToRender;
	}
	
	public void setShader(ShaderProgram shader) {
		renderer.getBatch().setShader(shader);
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	
	public void dispose() {
		shapeRenderer.dispose();
	}
}
