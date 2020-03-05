package ru.keelfy.projectac.entities;

import com.remote.remote2d.engine.entity.component.Component;
import com.remote.remote2d.engine.logic.Collider;

import ru.keelfy.projectac.utils.ColorUtils;

/**
 * @author keelfy
 * @created 11 авг. 2017 г.
 */
public class BaseComponent extends Component {

	/**
	 * Is alpha value decreases when player behind the component
	 */
	public boolean canLookThrough;
	/**
	 * Impassable borders offset
	 */
	public float borderNorth;
	public float borderSouth;
	public float borderWest;
	public float borderEast;

	@Override
	public void tick(int i, int j, int k) {}

	/**
	 * Fires when entity interact with block (step, use and etc.)
	 */
	public void onInteractWithEntity(Component component) {}

	public float[] getBorders() {
		return new float[] { borderNorth, borderSouth, borderWest, borderEast };
	}

	@Override
	public void renderBefore(boolean editor, float interpolation) {}

	@Override
	public void renderAfter(boolean editor, float interpolation) {
		entity.getBorderedCollider(interpolation).drawCollider(ColorUtils.RED);
	}

	@Override
	public Collider[] getColliders() {
		return new Collider[] { entity.getBorderedCollider() };
	}

	@Override
	public void apply() {}

	@Override
	public void init() {}

	@Override
	public void onEntitySpawn() {}
}