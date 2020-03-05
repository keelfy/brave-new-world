package ru.keelfy.projectac.glutils;

import java.nio.ByteBuffer;

import com.remote.remote2d.engine.DisplayHandler;
import com.remote.remote2d.engine.logic.ColliderBox;
import com.remote.remote2d.engine.logic.Vector2;
import com.remote.remote2d.engine.logic.Vector3;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

import ru.keelfy.projectac.utils.ColorUtils;

public class FBO {

	public static final int NONE = 0;
	public static final int DEPTH_TEXTURE = 1;
	public static final int DEPTH_RENDER_BUFFER = 2;

	private final int width;
	private final int height;

	private int frameBuffer;

	private int colorTexture;
	private int depthTexture;

	private int depthBuffer;
	private int colourBuffer;

	/**
	 * Creates an FBO of a specified width and height, with the desired type of
	 * depth buffer attachment.
	 *
	 * @param width
	 *            - the width of the FBO.
	 * @param height
	 *            - the height of the FBO.
	 * @param depthBufferType
	 *            - an int indicating the type of depth buffer attachment that this
	 *            FBO should use.
	 */
	public FBO(Vector2 dim, int depthBufferType) {
		this.width = (int) dim.x;
		this.height = (int) dim.y;
		initialiseFrameBuffer(depthBufferType);
	}

	/**
	 * Deletes the frame buffer and its attachments when the game closes.
	 */
	public void cleanUp() {
		glDeleteFramebuffers(frameBuffer);
		glDeleteTextures(colorTexture);
		glDeleteTextures(depthTexture);
		glDeleteRenderbuffers(depthBuffer);
		glDeleteRenderbuffers(colourBuffer);
	}

	/**
	 * Binds the frame buffer, setting it as the current render target. Anything
	 * rendered after this will be rendered to this FBO, and not to the screen.
	 */
	public void bindFrameBuffer() {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBuffer);
		glViewport(0, 0, width, height);
	}

	/**
	 * Unbinds the frame buffer, setting the default frame buffer as the current
	 * render target. Anything rendered after this will be rendered to the screen,
	 * and not this FBO.
	 */
	public void unbindFrameBuffer() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		ColliderBox renderArea = DisplayHandler.getScreenRenderArea();
		glViewport((int) renderArea.pos.x, (int) renderArea.pos.y, (int) renderArea.dim.x, (int) renderArea.dim.y);
	}

	/**
	 * Binds the current FBO to be read from (not used in tutorial 43).
	 */
	public void bindToRead() {
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, frameBuffer);
		glReadBuffer(GL_COLOR_ATTACHMENT0);
	}

	/**
	 * @return The ID of the texture containing the colour buffer of the FBO.
	 */
	public int getColorTexture() {
		return colorTexture;
	}

	public void drawColorTexture(Vector2 pos, Vector2 dim, int color, float alpha) {
		Vector3 rgb = ColorUtils.hexToRGB(color);

		glBindTexture(GL_TEXTURE_2D, colorTexture);
		glColor4f(rgb.x, rgb.y, rgb.z, alpha);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);
		glVertex2f(pos.x, pos.y);
		glTexCoord2f(1, 1);
		glVertex2f(pos.x + dim.x, pos.y);
		glTexCoord2f(1, 0);
		glVertex2f(pos.x + dim.x, pos.y + dim.y);
		glTexCoord2f(0, 0);
		glVertex2f(pos.x, pos.y + dim.y);
		glEnd();
		glColor3f(1, 1, 1);
	}

	/**
	 * @return The texture containing the FBOs depth buffer.
	 */
	public int getDepthTexture() {
		return depthTexture;
	}

	/**
	 * Creates the FBO along with a colour buffer texture attachment, and possibly a
	 * depth buffer.
	 *
	 * @param type
	 *            - the type of depth buffer attachment to be attached to the FBO.
	 */
	private void initialiseFrameBuffer(int type) {
		createFrameBuffer();
		createTextureAttachment();
		if (type == DEPTH_RENDER_BUFFER) {
			createDepthBufferAttachment();
		} else if (type == DEPTH_TEXTURE) {
			createDepthTextureAttachment();
		}
		unbindFrameBuffer();
	}

	/**
	 * Creates a new frame buffer object and sets the buffer to which drawing will
	 * occur - colour attachment 0. This is the attachment where the colour buffer
	 * texture is.
	 *
	 */
	private void createFrameBuffer() {
		frameBuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
	}

	/**
	 * Creates a texture and sets it as the colour buffer attachment for this FBO.
	 */
	private void createTextureAttachment() {
		colorTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, colorTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTexture, 0);
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a texture, which can later be
	 * sampled.
	 */
	private void createDepthTextureAttachment() {
		depthTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, depthTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT,
				(ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexture, 0);
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a render buffer. This can't be
	 * used for sampling in the shaders.
	 */
	private void createDepthBufferAttachment() {
		depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
	}

}