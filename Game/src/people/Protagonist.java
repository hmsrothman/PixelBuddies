package people;

import Engine.Camera2D;
import game.Level;
import otherpeoplesmath.Vector2f;

public class Protagonist extends Leader {

	Camera2D camera;

	public Protagonist(Vector2f loc, String name, Camera2D camera) {
		super(loc, name);
		this.camera = camera;
	}

	public Protagonist(Vector2f loc, String name, Follower follower, Camera2D camera) {
		super(loc, name, follower);
		this.camera = camera;
	}

	@Override
	protected void move(Vector2f displacement, Level level) {
		super.move(displacement, level);
		if (displacement.x == 0 && displacement.y == 0) {

		} else {
			camera.setPosition(new Vector2f(pos.x, camera.pos.y));
		}
	}
}
