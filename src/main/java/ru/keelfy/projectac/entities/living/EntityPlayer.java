package ru.keelfy.projectac.entities.living;

import com.remote.remote2d.engine.entity.Entity;
import com.remote.remote2d.engine.world.Map;

/**
 * @author keelfy
 * @created 14 авг. 2017 г.
 */
public class EntityPlayer extends Entity {

	public EntityPlayer(Map map) {
		super(map);

		this.addComponent(new ActorComponent());
	}

}
