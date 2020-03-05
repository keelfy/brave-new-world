package com.remote.remote2d.engine;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;

import javax.swing.JFrame;

import com.remote.remote2d.engine.gui.GuiInGame;
import com.remote.remote2d.engine.gui.GuiMenu;
import com.remote.remote2d.engine.logic.Vector2;
import com.remote.remote2d.engine.world.Map;

/**
 * A catch-all class that each and every game MUST use. This class handles
 * information such as what method of platform independence must be used, how to
 * initialize the game what components should be registered with the engine,
 * etc.
 *
 * @author Flafla2
 */
public abstract class Remote2DGame {

	/**
	 * This is called when the Remote2D instance starts up. Put any Remote2D-based
	 * initialization code here.
	 */
	public abstract void initGame();

	/**
	 * This is called when game is shutting down
	 */
	public abstract void shutDown();

	/**
	 * Supplies the local path to any icons. These are shown on the window and in
	 * the taskbar in windows/linux, and in the dock on mac.
	 *
	 * For full support, you need at least:
	 * <ul>
	 * <li>One 16x16 and one 32x32 icon for full windows support</li>
	 * <li>One 32x32 icon for full Linux (and similar platforms) support</li>
	 * <li>One 128x128 icon for full Mac support</li>
	 * </ul>
	 *
	 * Finally, the icon list should be <b>in order from largest icon to the
	 * smallest</b>. In other words, the largest icon should be the first one in the
	 * list. Not doing this may lead to a small icon being the dock icon on Mac.
	 * 
	 * @return A string array with icons to use, or null for the default LWJGL icon.
	 */
	public String[] getIconPath() {
		return null;
	}

	/**
	 * This is called at the beginning of each render. If it is different than the
	 * current stretch type, and no GuiMenus are overriding it, than the stretch
	 * type will change to this.
	 *
	 * @return The default Stretch Type - {@link StretchType#MULTIPLES} by default.
	 * @see StretchType
	 */
	public StretchType getDefaultStretchType() {
		return StretchType.MULTIPLES;
	}

	public Vector2 getDefaultResolution() {
		return new Vector2(1024, 576);
	}

	public Vector2 getDefaultScreenResolution() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Dimension screenDimension = env.getMaximumWindowBounds().getSize();

		JFrame frame = new JFrame("Top lel");
		frame.pack();
		Insets inset = frame.getInsets();
		frame.dispose();
		return new Vector2(screenDimension.width - inset.left - inset.right,
				screenDimension.height - inset.top - inset.bottom);
	}

	/**
	 * This is called by the editor when you click "Run Map", and is recommended to
	 * be called by your game when you want to push an in-game GUI to the stack.
	 * This can be used to override the default, simple {@link GuiInGame} with a
	 * more complex GUI.
	 * 
	 * @param map
	 *            The map that should be used in the created GUI
	 */
	public GuiMenu getNewInGameGui(Map map) {
		return new GuiInGame(map);
	}

}
