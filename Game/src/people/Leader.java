package people;

import game.Level;
import game.State;
import otherpeoplesmath.Vector2f;

public class Leader extends Person {

	Follower follower = null;
	Vector2f offset = new Vector2f(-5, 0);
	Vector2f followerPos = new Vector2f();

	public Leader(Vector2f loc, String name, Follower follower) {
		super(loc, name);
		this.follower = follower;
	}

	public Leader(Vector2f loc, String name) {
		super(loc, name);
	}

	@Override
	protected void move(Vector2f displacement, Level level) {
		// if (follower != null) {
		// follower.state = state;
		// }
		super.move(displacement, level);
	}

	@Override
	public void jump() {
		super.jump();
		if ((state & State.JUMPING) == State.JUMPING) {
			follower.issueCommand(new FollowCommand(new Vector2f(pos),true));
		} 
	}

	@Override
	public boolean update(Level level) {
		boolean r = super.update(level);
		if (follower.mode != FollowMode.JUMPING) {
			Vector2f.add(offset, pos, followerPos);
			follower.issueCommand(new FollowCommand(followerPos, false));
		}
		return r;
	}

}
