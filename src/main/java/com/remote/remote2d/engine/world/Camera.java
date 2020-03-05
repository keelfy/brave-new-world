package com.remote.remote2d.engine.world;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.remote.remote2d.engine.DisplayHandler;
import com.remote.remote2d.engine.art.Renderer;
import com.remote.remote2d.engine.gui.Gui;
import com.remote.remote2d.engine.logic.ColliderBox;
import com.remote.remote2d.engine.logic.Interpolator;
import com.remote.remote2d.engine.logic.Vector2;

public class Camera {
	
	public Vector2 pos;
	private Vector2 oldPos;
	public float scale = 1.0f;
	
	public Camera(Vector2 pos)
	{
		this.pos = pos.copy();
		oldPos = pos.copy();
	}
	
	private Camera(Vector2 pos, Vector2 oldPos)
	{
		this.pos = pos.copy();
		this.oldPos = oldPos.copy();
	}
	
	public Camera()
	{
		this(new Vector2(0,0));
	}
	
	public void tick(int i, int j, int k)
	{
		this.oldPos = pos.copy();
	}
	
	public void renderBefore(float interpolation)
	{
		Vector2 pos = Interpolator.linearInterpolate(oldPos, this.pos, interpolation).subtract(DisplayHandler.getDimensions());		
		Vector2 dim = getDimensions();
		Renderer.pushMatrix();
		Renderer.scale(scale);
		Renderer.translate(new Vector2(-pos.x-dim.x/2, -pos.y-dim.y/2));		
	}
	
	public void renderAfter(float interpolation)
	{
		Renderer.popMatrix();
	}
	
	public Vector2 getTruePos(float interpolation)
	{
		return Interpolator.linearInterpolate(oldPos, this.pos, interpolation);
	}
	
	public ColliderBox getMapRenderArea()
	{
		return getMapRenderArea(1);
	}
	
	public ColliderBox getMapRenderArea(float interpolation)
	{
		return getTruePos(interpolation).subtract(getDimensions().divide(new Vector2(2))).getColliderWithDim(getDimensions());
	}
	
	public Vector2 getDimensions()
	{
		return DisplayHandler.getDimensions();
	}

	public Camera copy() {
		return new Camera(pos,oldPos);
	}
	
	public Matrix4f getMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		
		Vector3f scaleV = new Vector3f(scale, scale, 0);
		Vector3f translate2 = new Vector3f(-pos.x+Gui.screenWidth()/2, -pos.y+Gui.screenHeight()/2, 0);
		
		matrix.scale(scaleV);
		matrix.translate(translate2);
		
		return matrix;
	}
	
	public Matrix4f getInverseMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		
		Vector3f scaleV = new Vector3f(1/scale, 1/scale, 0);
		Vector3f translate2 = new Vector3f(pos.x-Gui.screenWidth()/2, pos.y-Gui.screenHeight()/2, 0);
		
		matrix.translate(translate2);
		matrix.scale(scaleV);
		
		return matrix;
	}
	
	public void updatePos()
	{
		this.oldPos = pos.copy();
	}
}
