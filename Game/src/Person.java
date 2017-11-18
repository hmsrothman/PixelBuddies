import Engine.SpriteBatch;
import Engine.Texture;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public class Person {
	Vector2f gravity = new Vector2f(0, -0.01f);
	Vector2f velocity = new Vector2f(0, 0);
	Vector2f speed = new Vector2f(0, 0);

	Vector2f pos;
	String name;
	byte state;

	int currentTexture;
	Texture[] walkCycle;
	Texture jumpSprite;

	Vector4f destRect;
	Vector4f uvRect;
	Vector4f color;
	float depth;

	float height = 45, width = 30;

	int frameCounter = 0;
	int walkIndex = 0;

	public Person(Vector2f loc, String name) {
		this.pos = loc;
		this.name = name;
		this.state = 0;

		depth = 0;
		destRect = new Vector4f(loc.x, loc.y, width / height * 10, 10);
		uvRect = new Vector4f(0, 0, 1, 1);
		color = new Vector4f(1, 1, 1, 1);

		walkCycle = PeopleTextures.getWalkCycle(name);
		jumpSprite = PeopleTextures.getJumpSprite(name);

		currentTexture = walkCycle[0].id;
	}

	public boolean update(Level level) {
		frameCounter++;
		handleFalling(level);
		handleWalking(level);
		return true;
	}

	public void jump() {
		if ((state & State.JUMPING) != State.JUMPING) {
			velocity = new Vector2f(0, 0.5f);
			state |= State.JUMPING;
		}
	}

	public void draw(SpriteBatch batch) {
		batch.draw(destRect, uvRect, currentTexture, depth, color);
	}

	private void move(Vector2f displace, Level level) {
		int x1 = (int) Math.floor((pos.x + displace.x) / 5f);
		int x2 = (int) Math.floor((pos.x + +displace.x + (width * 10 / height)) / 5f);
		int cx1 = (int) Math.floor((pos.x) / 5f);
		int cx2 = (int) Math.floor((pos.x + (width * 10 / height)) / 5f);

		int y1 = (int) Math.ceil((pos.y + displace.y) / 5f);
		int y2 = (int) Math.ceil((pos.y + +displace.y + 10) / 5f);
		int cy1 = (int) Math.ceil((pos.y) / 5f);
		int cy2 = (int) Math.ceil((pos.y + 10) / 5f);

		if (level.canWalk(cx1, y1) || level.canWalk(cx2, y1)) {
			System.out.println("dont fall");
			state &= ~State.JUMPING;
			velocity.y = 0;
			displace.y = 0;
		}
		if (level.canWalk(x1, cy1) || level.canWalk(x2, cy1) || level.canWalk(x1, cy2) || level.canWalk(x2, cy2)) {
			// state &= ~State.JUMPING;
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
				currentTexture = walkCycle[walkIndex].id;
			}
		} else {
			if (frameCounter >= 12) {
				walkIndex = 0;
				currentTexture = walkCycle[walkIndex].id;
			}
		}

		if ((state & State.JUMPING) == State.JUMPING) {
			currentTexture = jumpSprite.id;
		}

		currentTexture = 0;
	}
}
