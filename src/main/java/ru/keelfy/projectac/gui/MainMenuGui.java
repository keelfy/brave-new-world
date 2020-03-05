package ru.keelfy.projectac.gui;

import com.remote.remote2d.editor.GuiEditor;
import com.remote.remote2d.engine.Remote2D;
import com.remote.remote2d.engine.art.Fonts;
import com.remote.remote2d.engine.art.ResourceLoader;
import com.remote.remote2d.engine.gui.GuiButton;
import com.remote.remote2d.engine.gui.GuiMenu;
import com.remote.remote2d.engine.logic.Vector2;

import ru.keelfy.projectac.utils.ColorUtils;
import ru.keelfy.projectac.utils.GameConstants;
import ru.keelfy.projectac.world.BNWLocations;
import ru.keelfy.projectac.world.Location;

public class MainMenuGui extends GuiMenu {

	@Override
	public void initGui() {

		buttonList.clear();

		buttonList.add(new GuiButton(0, new Vector2(screenWidth() / 2 - 125, 150), new Vector2(250, 40), "Run Game"));
		buttonList.add(new GuiButton(1, new Vector2(screenWidth() / 2 - 125, 200), new Vector2(250, 40), "Settings"));
		buttonList.add(new GuiButton(2, new Vector2(screenWidth() / 2 - 125, 250), new Vector2(250, 40), "Exit"));

		if (GameConstants.DEVELOPMENT) {
			buttonList.add(new GuiButton(3, new Vector2(screenWidth() / 2 - 125, 300), new Vector2(250, 40), "Editor"));
		}
	}

	@Override
	public void render(float interpolation) {
		super.render(interpolation);

		int[] remoteDim = Fonts.get("Logo").getStringDim("PROJECT", 100);
		int[] otherDim = Fonts.get("Logo").getStringDim("AC", 50);
		float remotePos = screenWidth() / 2 - (remoteDim[0] + otherDim[0]) / 2;
		float otherPos = remotePos + remoteDim[0];

		Fonts.get("Logo").drawString("PROJECT", remotePos, 0, 100, ColorUtils.RED);
		Fonts.get("Logo").drawString("AC", otherPos, 50, 50, ColorUtils.RED);

		Fonts.get("Arial").drawCenteredString("based on Remote2D", 90, 40, ColorUtils.BLACK);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			Location newMap = new Location();
			newMap.loadR2DFile(ResourceLoader.getCollection("res/maps/map_collider.r2d.xml"));
			BNWLocations.newLocation(newMap);
			Remote2D.guiList.push(new GameUI());
		} else if (button.id == 1) {
			Remote2D.guiList.add(new OptionsGui());
		} else if (button.id == 2) {
			Remote2D.running = false;
		} else if (button.id == 3) {
			Remote2D.guiList.add(new GuiEditor());
		}
	}

}
