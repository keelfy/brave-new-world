package ru.keelfy.projectac.utils;

import com.remote.remote2d.engine.logic.Vector3;

/**
 * @author keelfy
 * @created 10 авг. 2017 г.
 */
public class ColorUtils {

	public static final int WHITE = 0xFFFFFF;
	public static final int BLACK = 0x000000;
	public static final int RED = 0xFF0000;
	public static final int GREEN = 0x00FF00;
	public static final int BLUE = 0x0000FF;

	public static Vector3 hexToRGB(int color) {
		float r = ((color >> 16) & 0xff) / 255f;
		float g = ((color >> 8) & 0xff) / 255f;
		float b = (color & 0xff) / 255f;
		return new Vector3(r, g, b);
	}

}
