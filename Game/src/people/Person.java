package people;
import Engine.SpriteBatch;
import Engine.Texture;
import game.Level;
import game.State;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public abstract class Person {
	Vector2f gravity = new Vector2f(0, -0.035f);
	Vector2f velocity = new Vector2f(0, 0);
	Vector2f speed = new Vector2f(0, 0);

	Vector2f pos;
	String name;
	public byte state;

	Texture currentTexture;
	Texture[] walkCycle;
	Texture jumpSprite;

	Vector4f destRect;
	Vector4f uvRect;
	Vector4f color;
	float depth;

	float height = 10, width = 10 * 30 / 45;

	int frameCounter = 0;
	int walkIndex = 0;

	public Person(Vector2f loc, String name) {
		this.pos = loc;
		this.name = name;
		this.state = 0;

		depth = 0;
		destRect = new Vector4f(loc.x, loc.y, width, height);
		uvRect = new Vector4f(0, 0, 1, 1);
		color = new Vector4f(1, 1, 1, 1);

		walkCycle = PeopleTextures.getWalkCycle(name);
		jumpSprite = PeopleTextures.getJumpSprite(name);

		currentTexture = walkCycle[0];
	}

	public boolean update(Level level) {
		frameCounter++;
		handleFalling(level);
		handleWalking(level);
		return true;
	}

	public void jump() {
		if ((state & State.JUMPING) != State.JUMPING) {
			velocity = new Vector2f(0, 1f);
			state |= State.JUMPING;
		}
	}

	public void draw(SpriteBatch batch) {
		batch.draw(destRect, uvRect, currentTexture.id, depth, color);
	}

	protected void move(Vector2f displace, Level level) {
		float[] bounds = getTexBounds(walkCycle[0]);

		int x1 = (int) Math.floor((pos.x + displace.x + bounds[0]) / 5f);
		int x2 = (int) Math.floor((pos.x + +displace.x + bounds[1]) / 5f);
		int x3 = (int) Math.floor((pos.x + +displace.x + width / 2) / 5f);
		int cx1 = (int) Math.floor((pos.x + bounds[0]) / 5f);
		int cx2 = (int) Math.floor((pos.x + bounds[1]) / 5f);
		int cx3 = (int) Math.floor((pos.x + width / 2) / 5f);

		int y1 = (int) Math.ceil((pos.y + displace.y + bounds[2]) / 5f);
		int y2 = (int) Math.ceil((pos.y + +displace.y + bounds[3]) / 5f);
		int y3 = (int) Math.ceil((pos.y + +displace.y + height / 2) / 5f);
		int cy1 = (int) Math.ceil((pos.y + bounds[2]) / 5f);
		int cy2 = (int) Math.ceil((pos.y + bounds[3]) / 5f);
		int cy3 = (int) Math.ceil((pos.y + height / 2) / 5f);

		if (level.canWalk(cx1, y1) || level.canWalk(cx2, y1) || level.canWalk(cx3, y1) || level.canWalk(cx1, y2)
				|| level.canWalk(cx2, y2) || level.canWalk(cx3, y2) || level.canWalk(cx1, y3) || level.canWalk(cx2, y3)
				|| level.canWalk(cx3, y3)) {

			state &= ~State.JUMPING;
			velocity.y = 0;
			displace.y = 0;
			if (level.canWalk(cx1, y2) || level.canWalk(cx2, y2) || level.canWalk(cx3, y2)) {
				state |= State.JUMPING;
			}
		}
		if (level.canWalk(x1, cy1) || level.canWalk(x2, cy1) || level.canWalk(x3, cy1) || level.canWalk(x1, cy2)
				|| level.canWalk(x2, cy2) || level.canWalk(x3, cy2) || level.canWalk(x1, cy3) || level.canWalk(x2, cy3)
				|| level.canWalk(x3, cy3)) {
			velocity.x = 0;
			displace.x = 0;
		}
		Vector2f.add(displace, pos, pos);
	}

	private void handleFalling(Level level) {
		Vector2f.add(velocity, gravity, velocity);
		move(velocity, level);
		destRect.y = pos.y;
	}

	private void handleWalking(Level level) {
		if ((state & State.LEFT) == State.LEFT) { // going right
			uvRect.z = 1f;
			speed.x = -width / height * 5 / 12;
		} else {
			uvRect.z = -1f;
			speed.x = width / height * 5 / 12;
		}

		if ((state & State.WALKING) == State.WALKING) {
			move(speed, level);
			destRect.x = pos.x;
			if (frameCounter >= 12) {
				frameCounter = 0;
				walkIndex++;
				if (walkIndex >= 4) {
					walkIndex = 0;
				}
				currentTexture = walkCycle[walkIndex];
			}
		} else {
			if (frameCounter >= 12) {
				walkIndex = 0;
				currentTexture = walkCycle[walkIndex];
			}
		}

		if ((state & State.JUMPING) == State.JUMPING) {
			currentTexture = jumpSprite;
		}

		// currentTexture = 0;
	}

	private float[] getTexBounds(Texture texture) {
		float minX = ((float) texture.minX) / texture.width * width;
		float minY = ((float) texture.minY) / texture.height * height;
		float maxX = ((float) texture.maxX) / texture.width * width;
		float maxY = ((float) texture.maxY) / texture.height * height;

		if ((state & State.LEFT) == State.LEFT) {
			float tmp = minX;
			minX = width - maxX;
			maxX = width - tmp;
		}
		// System.out.println(minX);
		return (new float[] { minX, maxX, minY, maxY });
	}
}
