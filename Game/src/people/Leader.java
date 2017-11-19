package people;

import game.Level;
import otherpeoplesmath.Vector2f;

public class Leader extends Person {

	Person follower = null;

	public Leader(Vector2f loc, String name, Person follower) {
		super(loc, name);
		this.follower = follower;
	}

	public Leader(Vector2f loc, String name) {
		super(loc, name);
	}

	@Override
	protected void move(Vector2f displacement, Level level) {
		if (follower != null) {
			follower.state = state;
		}
		super.move(displacement, level);
	}

	@Override
	public void jump() {
		super.jump();
		if (follower != null) {
			follower.jump();
		}
	}

}
