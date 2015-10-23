package com.deldaryan.entity.component;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.deldaryan.entity.WeaponType;
import com.deldaryan.main.Main;

public class WeaponComponent implements Component {

	private HashMap<String, Weapon> weapons;	
	
	public WeaponComponent() {
		weapons = new HashMap<String, WeaponComponent.Weapon>();
	}
	
	public void addWeapon(String key, int weaponType, int ammoAmount, float firerate) {
		weapons.put(key, new Weapon(firerate, ammoAmount, weaponType));
	}
	
	public Weapon getWeapon(String key) {
		return weapons.get(key);
	}
	
	
	public void updateAll() {
		for (Weapon weapon : weapons.values()) {
			weapon.update();
		}
	}
	
	
	
	
	
	
	
	
	
	public class Weapon {
		
		private float firerate, firerateTimer;
		private int ammoAmount, weaponType;
		
		public Weapon(float firerate, int ammoAmount, int weaponType) {
			this.firerate = firerate;
			firerateTimer = firerate+1;
			this.ammoAmount = ammoAmount;
			this.weaponType = weaponType;
		}

		public void update() {
			if(firerateTimer < firerate) {
				firerateTimer += 0.1f;
			}
		}
		
		public void fire(Vector2 position, int lookRight, float speed) {
			if(hasAmmo()) {
				if(canShoot()) {
					if(weaponType == WeaponType.NORMAL) {
//						new NormalProjectile(position, lookRight, speed, Main.getEntityManager().getEngine());
						ammoAmount--;
						firerateTimer = 0;
					}
					else if(weaponType == WeaponType.SPECIAL) {
//						new SpecialProjectile(position, lookRight, Main.getEntityManager().getEngine());
						ammoAmount--;
						firerateTimer = 0;
					}
				}
			}
		}
		
		
		public void addAmmo(int amount) {
			ammoAmount+=amount;
		}
		
		public void setAmmoAmount(int ammoAmount) {
			this.ammoAmount = ammoAmount;
		}
		
		public boolean hasAmmo() {
			return ammoAmount > 0 ? true : false;
		}
		
		public int getAmmoAmount() {
			return ammoAmount;
		}
		
		public boolean canShoot() {
			return firerateTimer >= firerate ? true : false;
		}
	}
}
