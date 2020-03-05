package ru.keelfy.projectac.entities.living;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.remote.remote2d.engine.DisplayHandler;
import com.remote.remote2d.engine.art.Animation;
import com.remote.remote2d.engine.entity.Entity;
import com.remote.remote2d.engine.logic.Collider;
import com.remote.remote2d.engine.logic.ColliderBox;
import com.remote.remote2d.engine.logic.Collision;
import com.remote.remote2d.engine.logic.Vector2;

import ru.keelfy.projectac.entities.BaseComponent;
import ru.keelfy.projectac.entities.blocks.BlockComponent;
import ru.keelfy.projectac.world.Direction;

public class ActorComponent extends BaseComponent {

	// Public
	public Animation northAnimation;
	public Animation southAnimation;
	public Animation westAnimation;

	// Private
	private Direction direction = Direction.SOUTH;
	private PlayerState state = PlayerState.IDLE;
	private boolean[] walls = new boolean[] { false, false, false, false };
	private List<Entity> staticContacts = new ArrayList<Entity>();

	@Override
	public void tick(int i, int j, int k) {
		super.tick(i, j, k);

		this.staticContacts.clear();
		int l;

		for (l = 0; l < this.entity.getMap().getEntityList().size(); l++) {
			Entity ent = this.entity.getMap().getEntityList().get(l);

			if (ent.getComponentsOfType(BaseComponent.class).size() == 0 || ent.name.contains("Player")
					|| ent.name.contains("Camera")) {
				continue;
			}

			Collision col = Collider.getCollision(ent.getBorderedCollider(), entity.getBorderedCollider());

			if (col.collides) {
				staticContacts.add(ent);
			}
		}

		float speed = 3.0F;

		boolean north = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean south = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean west = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean east = Keyboard.isKeyDown(Keyboard.KEY_D);

		if (north && south && west && east) {
			north = south = west = east = false;
		}

		if (north && south) {
			north = south = false;
		}

		if (west && east) {
			west = east = false;
		}

		Vector2 velocity = new Vector2(0, 0);

		walls[0] = canMoveToDirection(Direction.NORTH);
		walls[1] = canMoveToDirection(Direction.SOUTH);
		walls[2] = canMoveToDirection(Direction.WEST);
		walls[3] = canMoveToDirection(Direction.EAST);

		if (north && !walls[0]) {
			if (direction != Direction.NORTH) {
				direction = Direction.NORTH;
			}

			velocity.y -= speed;
			if (state == PlayerState.IDLE) {
				state = PlayerState.WALK;
			}
		}

		if (south && !walls[1]) {
			if (direction != Direction.SOUTH) {
				direction = Direction.SOUTH;
			}

			velocity.y += speed;
			if (state == PlayerState.IDLE) {
				state = PlayerState.WALK;
			}
		}

		if (west && (!walls[2])) {
			if (direction != Direction.WEST) {
				direction = Direction.WEST;
			}

			velocity.x -= speed;
			if (state == PlayerState.IDLE) {
				state = PlayerState.WALK;
			}
		}

		if (east && !walls[3]) {
			if (direction != Direction.EAST) {
				direction = Direction.EAST;
			}

			velocity.x += speed;
			if (state == PlayerState.IDLE) {
				state = PlayerState.WALK;
			}
		}

		entity.pos = entity.pos.add(velocity);

		if (westAnimation != null && east && !west) {
			westAnimation.flippedX = true;
		} else if (westAnimation != null) {
			westAnimation.flippedX = false;
		}

		Vector2 globalPos = entity.getPosGlobal();
		ColliderBox renderarea = entity.getMap().camera.getMapRenderArea();
		int border = (int) (DisplayHandler.getDimensions().y / 6);

		float right = renderarea.pos.x + renderarea.dim.x;
		float left = renderarea.pos.x;

		if (globalPos.x + entity.getDim().x + border > right) {
			entity.getMap().camera.pos.x += (globalPos.x + entity.getDim().x + border) - right;

		}

		if (globalPos.x - border < left) {
			entity.getMap().camera.pos.x -= left - globalPos.x + border;
		}

		float bottom = renderarea.pos.y + renderarea.dim.y;
		float top = renderarea.pos.y;

		if (globalPos.y + entity.getDim().y + border > bottom) {
			entity.getMap().camera.pos.y += (globalPos.y + entity.getDim().y + border) - bottom;
		}

		if (globalPos.y - border < top) {
			entity.getMap().camera.pos.y -= top - globalPos.y + border;
		}

		Animation replaceAnimation = null;

		switch (direction) {
			case NORTH:
				replaceAnimation = northAnimation;
				break;
			case SOUTH:
				replaceAnimation = southAnimation;
				break;
			case WEST:
				replaceAnimation = westAnimation;
				break;
			case EAST:
				replaceAnimation = westAnimation;
				break;
		}

		if (replaceAnimation != null && !entity.material.getAnimation().equals(replaceAnimation)) {
			entity.material.setAnimation(replaceAnimation);
		}

		boolean keyDown = north || south || east || west;

		if (!keyDown) {
			state = PlayerState.IDLE;
		}

		if (entity.material.getAnimation() != null) {
			if (state == PlayerState.WALK && keyDown) {
				entity.material.getAnimation().animate = true;
			} else if (entity.material.getAnimation().animate) {
				entity.material.getAnimation().animate = false;
				entity.material.getAnimation().setCurrentFrame(0);
			}
		}
	}

	private Direction getSideOfStatic(Entity target) {
		Collision col = Collider.getCollision(target.getBorderedCollider(), entity.getBorderedCollider());

		Vector2 pPos = entity.getBorderedPos();
		Vector2 pDim = entity.getBorderedDim();

		Vector2 tPos = target.getBorderedPos();
		Vector2 tDim = target.getBorderedDim();

		float w = 0.5F * (pDim.x + tDim.x);
		float h = 0.5F * (pDim.y + tDim.y);
		float dx = (pPos.x + pDim.x / 2) - (tPos.x + tDim.x / 2);
		float dy = (pPos.y + pDim.y / 2) - (tPos.y + tDim.y / 2);

		if (col.isCollides()) {
			if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
				float wy = w * dy;
				float hx = h * dx;

				if (wy > hx) {
					if (wy > -hx)
						return Direction.NORTH;
					else
						return Direction.EAST;
				} else {
					if (wy > -hx)
						return Direction.WEST;
					else
						return Direction.SOUTH;
				}
			}
		}
		return null;
	}

	private boolean canMoveToDirection(Direction direction) {
		for (Entity ent : staticContacts) {
			Direction side = getSideOfStatic(ent);

			if (side == null) {
				continue;
			}

			Vector2 pPos = entity.getBorderedPos();
			Vector2 pDim = entity.getBorderedDim();

			Vector2 tPos = ent.getBorderedPos();
			Vector2 tDim = ent.getBorderedDim();

			switch (direction) {
				case NORTH:
					if (pPos.y <= tPos.y + tDim.y && side == Direction.NORTH)
						return true;
					break;
				case SOUTH:
					if (pPos.y + pDim.y >= tPos.y && side == Direction.SOUTH)
						return true;
					break;
				case WEST:
					if (pPos.x <= tPos.x + tDim.x && side == Direction.WEST)
						return true;
					break;
				case EAST:
					if (pPos.x + pDim.x >= tPos.x && side == Direction.EAST)
						return true;
					break;
			}
		}
		return false;

	}

	@Override
	public void renderBefore(boolean editor, float interpolation) {
		super.renderBefore(editor, interpolation);

		if (!editor) {

			for (int x = 0; x < entity.getMap().getEntityList().size(); x++) {

				Entity ent = this.entity.getMap().getEntityList().get(x);

				for (BlockComponent c : ent.getComponentsOfType(BlockComponent.class)) {

					// AudioHandler.playSound("res/sound/" + block.getStepSound(), true, false);
					c.onInteractWithEntity(this);
				}

				if (ent.getUUID().equals(this.entity.getUUID()) || ent.name.contains("Camera")) {
					continue;
				}

				float alpha = 1.0F;

				for (BaseComponent c : ent.getComponentsOfType(BaseComponent.class)) {
					if (c.canLookThrough) {
						alpha = 0.5F;
					}
				}

				Collision col = Collider.getCollision(ent.pos.getColliderWithDim(ent.dim),
						entity.pos.getColliderWithDim(entity.dim));
				if (col.isCollides()
						&& (col.getCorrectionX() >= entity.dim.x || col.getCorrectionY() >= entity.dim.y)) {
					ent.material.setCurrentAlpha(alpha);
				} else {
					ent.material.setCurrentAlpha(ent.material.getAlpha());
				}
			}
			// Vector2 interpPos = Interpolator.linearInterpolate(oldPos, entity.pos,
			// interpolation);
			// Fonts.get("Pixel_Arial").drawString(entity.name,
			// interpPos.x - Fonts.get("Pixel_Arial").getStringDim(entity.name, 20)[0] / 2,
			// interpPos.y - 28, 20,
			// ACColors.BLUE);
		}
	}

	enum PlayerState {
		WALK,
		IDLE;
	}

	@Override
	public void onEntitySpawn() {
		super.onEntitySpawn();
	}

	@Override
	public void renderAfter(boolean editor, float interpolation) {
		super.renderAfter(editor, interpolation);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void apply() {
		super.apply();
	}

}
