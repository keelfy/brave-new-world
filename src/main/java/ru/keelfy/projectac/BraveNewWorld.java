package ru.keelfy.projectac;

import com.esotericsoftware.minlog.Log;
import com.remote.remote2d.engine.DisplayHandler;
import com.remote.remote2d.engine.Remote2D;
import com.remote.remote2d.engine.Remote2DGame;
import com.remote.remote2d.engine.entity.InsertableComponentList;

import ru.keelfy.projectac.entities.BaseComponent;
import ru.keelfy.projectac.entities.blocks.BlockComponent;
import ru.keelfy.projectac.entities.living.ActorComponent;
import ru.keelfy.projectac.glutils.FBO;
import ru.keelfy.projectac.gui.MainMenuGui;

/**
 * @author keelfy
 * @created 10 авг. 2017 г.
 */
public class BraveNewWorld extends Remote2DGame {

	private static FBO fbo;

	public static void main(String[] args) {
		Remote2D.startRemote2D(new BraveNewWorld());
	}

	@Override
	public void initGame() {
		Log.TRACE();

		fbo = new FBO(DisplayHandler.getDimensions(), FBO.DEPTH_RENDER_BUFFER);

		Remote2D.guiList.push(new MainMenuGui());

		// InsertableComponentList.addInsertableComponent("Player",
		// ComponentPlayer.class);
		InsertableComponentList.addInsertableComponent("Player", ActorComponent.class);
		// InsertableComponentList.addInsertableComponent("Entity Pointer Test",
		// ComponentEntityPointerTest.class);
		InsertableComponentList.addInsertableComponent("Altitudinal", BaseComponent.class);
		InsertableComponentList.addInsertableComponent("Basic Block", BlockComponent.class);
	}

	@Override
	public String[] getIconPath() {
		String[] paths = { "res/gui/icon_128.png", "res/gui/icon_32.png", "res/gui/icon_16.png" };
		return paths;
	}

	public static FBO getFBO() {
		return fbo;
	}

	@Override
	public void shutDown() {
		fbo.cleanUp();
	}

	public static String getVersion() {
		return "0.0.1a";
	}

}
