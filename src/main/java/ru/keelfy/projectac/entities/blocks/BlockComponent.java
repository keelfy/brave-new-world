package ru.keelfy.projectac.entities.blocks;

import ru.keelfy.projectac.entities.BaseComponent;

/**
 * @author keelfy
 * @created 11 авг. 2017 г.
 */
public class BlockComponent extends BaseComponent {

	public String stepSound;

	@Override
	public void tick(int i, int j, int k) {}

	public String getStepSound() {
		return stepSound;
	}

	@Override
	public void renderBefore(boolean editor, float interpolation) {}

	@Override
	public void onEntitySpawn() {}

	@Override
	public void renderAfter(boolean editor, float interpolation) {}

	@Override
	public void init() {}

	@Override
	public void apply() {}

}
