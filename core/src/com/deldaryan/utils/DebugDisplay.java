package com.deldaryan.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.main.Main;
import com.deldaryan.physic.WorldManager;

public class DebugDisplay {

	private SpriteBatch debugBatch;
	private ShapeRenderer shapeRenderer;
	private BitmapFont debugFont;
	
	private ArrayList<DebugLine> debugLines;

	public DebugDisplay() {
		debugBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		
		debugFont = new BitmapFont(Gdx.files.internal(Main.DESKTOP_PATH_MODIFIER + "ui/fonts/debug.fnt"));
		debugFont.setColor(Color.RED);
		
		debugLines = new ArrayList<DebugDisplay.DebugLine>();
	}

	public void render() {

		shapeRenderer.setProjectionMatrix(Main.getGraphicsManager().getGameCamera().combined);
		shapeRenderer.begin();
			// draw pixel grid
			if(Main.DRAW_PIXELGRID) {
				shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.75f);
				for (float i = 1; i < 129; i++) {
					shapeRenderer.line(
							new Vector2(0, i / WorldManager.PIXELS_PER_METER),
							new Vector2(128 / WorldManager.PIXELS_PER_METER, i / WorldManager.PIXELS_PER_METER));
					shapeRenderer.line(
							new Vector2(i / WorldManager.PIXELS_PER_METER, 0),
							new Vector2(i / WorldManager.PIXELS_PER_METER, 128 / WorldManager.PIXELS_PER_METER));
				}
				shapeRenderer.setColor(0.6f, 0.4f, 0.4f, 1f);
				shapeRenderer.line(
						new Vector2(0, 0),
						new Vector2(0, 128 / WorldManager.PIXELS_PER_METER));
				shapeRenderer.line(
						new Vector2(0, 0),
						new Vector2(128 / WorldManager.PIXELS_PER_METER, 0));
				shapeRenderer.line(
						new Vector2(0, 128 / WorldManager.PIXELS_PER_METER),
						new Vector2(128 / WorldManager.PIXELS_PER_METER, 128 / WorldManager.PIXELS_PER_METER));
				shapeRenderer.line(
						new Vector2(128 / WorldManager.PIXELS_PER_METER, 0),
						new Vector2(128 / WorldManager.PIXELS_PER_METER, 128 / WorldManager.PIXELS_PER_METER));
			}
			
			// draw camera border
			if(Main.getGraphicsManager().getGameCamera().getCameraBorderBox() != null) {
				shapeRenderer.setColor(0.4f, 0.4f, 0.4f, 1f);
				shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y),
						new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().height));
				shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y),
						new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().width, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y));
				shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().getWidth(), Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y),
						new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().getWidth(), Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().height));
				shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().getHeight()),
						new Vector2(Main.getGraphicsManager().getGameCamera().getCameraBorderBox().x + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().width, Main.getGraphicsManager().getGameCamera().getCameraBorderBox().y + Main.getGraphicsManager().getGameCamera().getCameraBorderBox().getHeight()));
			}
			
			// draw debug lines
			shapeRenderer.setColor(0.6f, 0.6f, 0.6f, 1f);
			for (DebugLine debugLine : debugLines) {
				shapeRenderer.line(debugLine.point1, debugLine.point2);
			}
			debugLines.clear();
			
			// draw camera center cross
			shapeRenderer.setColor(0.75f, 0f, 0f, 1f);
			shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().position.x - 0.25f, Main.getGraphicsManager().getGameCamera().position.y),
					new Vector2(Main.getGraphicsManager().getGameCamera().position.x + 0.25f, Main.getGraphicsManager().getGameCamera().position.y));
			shapeRenderer.line(new Vector2(Main.getGraphicsManager().getGameCamera().position.x, Main.getGraphicsManager().getGameCamera().position.y - 0.25f),
					new Vector2(Main.getGraphicsManager().getGameCamera().position.x, Main.getGraphicsManager().getGameCamera().position.y + 0.25f));
		shapeRenderer.end();
		
		
		float y = Gdx.graphics.getHeight() - 5;
		String[] debugMessages = {
				"FPS: " + Gdx.graphics.getFramesPerSecond(),
				"FrameId: " + Gdx.graphics.getFrameId(),
				"JavaHeap: " + Gdx.app.getJavaHeap() / (1024*1024f),
				"NativeHeap: " + Gdx.app.getNativeHeap() / (1024*1024f),
				"DeltaTime: " + Gdx.graphics.getDeltaTime(),
				"",
				"CurrentEventTime: " + Gdx.input.getCurrentEventTime(),
				"BodyCount: " + Main.getWorldManager().getBodyCount(),
				"EntityCount: " + Main.getEntityManager().getEntityCount()
		};

		debugBatch.begin();
			for (String string : debugMessages) {
				debugFont.draw(debugBatch, string, 5, y);
				y -= 18;
			}
		debugBatch.end();
	}
	
	public void addDebugLine(DebugLine line) {
		debugLines.add(line);
	}
	
	
	public void dispose() {
		debugBatch.dispose();
		debugFont.dispose();
		shapeRenderer.dispose();
	}
	
	
	
	public static class DebugLine {
		public Vector2 point1, point2;

		public DebugLine(Vector2 point1, Vector2 point2) {
			this.point1 = point1;
			this.point2 = point2;
		}
	}
}
