package com.deldaryan.entity.component;

import com.badlogic.ashley.core.Component;
import com.deldaryan.entity.AIState;

public class AIComponent implements Component {

	public int currentAIState = AIState.WAITING;
	public boolean timerSet, canJump, shot;
	public int stateTimer;
	
}
