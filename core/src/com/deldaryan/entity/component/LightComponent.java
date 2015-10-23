package com.deldaryan.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.deldaryan.main.Main;

import box2dLight.PointLight;

public class LightComponent implements Component {

	private PointLight light;

	public LightComponent(float distance, int rays, Color color) {
		light = new PointLight(Main.getWorldManager().getRayHandler(), rays, color, distance, 0, 0);
		light.setSoft(false);
		light.setXray(true);
	}
	
	public PointLight getLight() {
		return light;
	}
}
