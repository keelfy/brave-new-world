package ru.keelfy.projectac.world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.remote.remote2d.engine.art.ResourceLoader;
import com.remote.remote2d.engine.io.R2DFileManager;
import com.remote.remote2d.engine.world.Map;

/**
 * @author keelfy
 * @created 15 авг. 2017 г.
 */
public final class BNWLocations {

	public static List<Map> loadedLocations = new ArrayList<Map>();
	private static Map currentLocation = null;

	public static Map getLocation() {
		return currentLocation;
	}

	public static void destroyLocation() {
		if (currentLocation == null)
			return;

		// try {
		// new R2DFileManager(currentLocation.path, currentLocation).write();
		currentLocation = null;
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static void newLocation(String path) {
		Map loc = new Map();
		loc.loadR2DFile(ResourceLoader.getCollection(path));
		newLocation(loc);
	}

	public static void newLocation(Map loc) {
		if (currentLocation != null) {
			destroyLocation();
		}

		if (loc == null)
			return;

		currentLocation = loc;
		currentLocation.spawn();
		loadLocation(loc);
	}

	public static void loadLocation(Map loc) {
		loadedLocations.add(loc);
	}

	public static void unloadLocation(Map loc) {
		loadedLocations.remove(loc);
	}

	public static void saveLoadedLocations() {
		for (Map loaded : loadedLocations) {
			try {
				new R2DFileManager(loaded.path, loaded).write();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
