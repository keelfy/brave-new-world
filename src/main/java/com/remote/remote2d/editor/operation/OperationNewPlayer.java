package com.remote.remote2d.editor.operation;

import com.remote.remote2d.editor.GuiEditor;
import com.remote.remote2d.engine.entity.Entity;

public class OperationNewPlayer extends Operation {

	Entity entity;

	public OperationNewPlayer(GuiEditor editor) {
		super(editor);
	}

	@Override
	public void execute() {
		editor.insertPlayer();
	}

	@Override
	public void undo() {
		editor.getMap().getEntityList().removeEntityFromList(entity);
	}

	@Override
	public String name() {
		return "Insert Player";
	}

	@Override
	public boolean canBeUndone() {
		return true;
	}

}
