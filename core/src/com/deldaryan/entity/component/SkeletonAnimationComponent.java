package com.deldaryan.entity.component;

import com.badlogic.ashley.core.Component;
import com.deldaryan.advanced.AdvancedSpriterPlayer;
import com.deldaryan.main.Main;

public class SkeletonAnimationComponent implements Component {

	private AdvancedSpriterPlayer player;
	
	public SkeletonAnimationComponent(String key) {
		player = Main.getAssetLoader().getSpritePlayer(key);
	}
	
	public AdvancedSpriterPlayer getPlayer() {
		return player;
	}
}
