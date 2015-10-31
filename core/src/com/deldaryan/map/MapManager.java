package com.deldaryan.map;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.deldaryan.entity.EntityType;
import com.deldaryan.entity.entities.Player;
import com.deldaryan.graphic.Layer;
import com.deldaryan.main.Main;
import com.deldaryan.physic.CollisionFilter;
import com.deldaryan.physic.WorldManager;
import com.deldaryan.physic.userdata.GroundUserData;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class MapManager {

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private ShapeRenderer shapeRenderer;
	
	public MapManager() {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
	}
	
	public void load(String mapname) {
		tiledMap = Main.getAssetLoader().getTiledMap(mapname);
		
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

	public void update() {
		float width = Main.getGraphicsManager().getGameCamera().viewportWidth + 1;
		float height = Main.getGraphicsManager().getGameCamera().viewportHeight + 0.5f;
		float x = Main.getGraphicsManager().getGameCamera().position.x - width / 2;
		float y = Main.getGraphicsManager().getGameCamera().position.y - height / 2;
		renderer.setView(Main.getGraphicsManager().getGameCamera().combined, x, y, width, height);
	}
	
	public void renderLayer(TiledMapTileLayer layer) {
		renderer.getBatch().begin();
			renderer.renderTileLayer(layer);
		renderer.getBatch().end();
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
		
		
		
		for (MapLayer layer : tiledMap.getLayers()) {
			if(layer.getProperties().containsKey("layer")) {
				String layerName = layer.getProperties().get("layer").toString();
				
				Layer renderLayer = null;
				if(Main.getGraphicsManager().getRenderManager().hasLayer(layerName)) {
					renderLayer = Main.getGraphicsManager().getRenderManager().getLayer(layerName); 
				}
				else if(layer.getProperties().containsKey("layer_priority")) {
					int layerPriority = Integer.parseInt(layer.getProperties().get("layer_priority").toString());
					renderLayer = Main.getGraphicsManager().getRenderManager().addLayer(layerName, layerPriority);
				}
				
				
				if(layer instanceof TiledMapTileLayer) {
					renderLayer.addRenderObject((TiledMapTileLayer) layer);
				}
				else {
					for (MapObject obj : layer.getObjects()) {
						if(obj instanceof TextureMapObject) {
							renderLayer.addRenderObject((TextureMapObject) obj);
						}
					}
				}
			}
		}
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
