package people;

import game.Level;
import otherpeoplesmath.Vector2f;

public class Leader extends Person {
	float offsetDist = 5;

	Follower follower = null;
	Vector2f offset = new Vector2f(-offsetDist, 0);
	Vector2f followerPos = new Vector2f();

	public Leader(Vector2f loc, String name, Follower follower) {
		super(loc, name);
		this.follower = follower;
		follower.leaderPos = pos;
		follower.offset = offset;
	}

	public Leader(Vector2f loc, String name) {
		super(loc, name);
	}

	@Override
	protected void move(Vector2f displacement, Level level) {
		super.move(displacement, level);
	}

	@Override
	public void jump() {
		if ((state & State.JUMPING) != State.JUMPING) {
			follower.issueJumpCommand(new Vector2f(pos));
		}
		super.jump();
	}

	@Override
	public boolean update(Level level) {
		boolean r = super.update(level);
		return r;
	}

}
