package ru.keelfy.projectac.gui;

import org.lwjgl.input.Keyboard;

import com.remote.remote2d.engine.Remote2D;
import com.remote.remote2d.engine.art.Fonts;
import com.remote.remote2d.engine.gui.GuiMenu;

import ru.keelfy.projectac.utils.ColorUtils;
import ru.keelfy.projectac.world.BNWLocations;

/**
 * @author keelfy
 * @created 11 авг. 2017 г.
 */
public class GameUI extends GuiMenu {// implements MapHolder {

	// protected Location map;
	protected String mapPath;

	// public GameUI(String mapPath) {
	// BNWLocations.newLocation(mapPath);

	// Log.info("Loading Map...");
	// map = new Location();
	// map.loadR2DFile(ResourceLoader.getCollection(mapPath));
	// backgroundColor = map.backgroundColor;
	// map.spawn();
	// }

	// public GameUI(Location map) {
	// BNWLocations.newLocation(map);

	// this.map = map;
	// Log.info("Loading Map...");
	// backgroundColor = map.backgroundColor;
	// this.map.spawn();
	// }

	@Override
	public void initGui() {}

	@Override
	public void render(float interpolation) {
		// map.render(false, interpolation);

		Fonts.get("Arial").drawString("FPS: " + Remote2D.getFPS(), 10, 10, 20, ColorUtils.BLACK);
	}

	@Override
	public void tick(int i, int j, int k) {
		// map.tick(i, j, k, false);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			closed();
		}
	}

	@Override
	public void closed() {
		BNWLocations.destroyLocation();
		super.closed();
	}

	// @Override
	// public Map getMap() {
	// return map;
	// }
}
