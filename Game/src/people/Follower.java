package people;

import java.util.LinkedList;

import game.Level;
import game.State;
import otherpeoplesmath.Vector2f;

enum FollowMode {
	NORMAL, JUMPING, RETURNING, WALKING_TO_JUMP
}

public class Follower extends Person {
	Vector2f leaderPos;
	Vector2f offset;
	private Vector2f goalDir = new Vector2f();
	private Vector2f goalPos = new Vector2f();

	LinkedList<Vector2f> jumpCommands = new LinkedList<Vector2f>();

	FollowMode mode;

	public Follower(Vector2f pos, String name) {
		super(pos, name);
		mode = FollowMode.NORMAL;
	}

	void issueJumpCommand(Vector2f pos) {
		jumpCommands.addLast(pos);
	}

	@Override
	public boolean update(Level level) {
		if ((mode == FollowMode.NORMAL || mode == FollowMode.RETURNING) && jumpCommands.isEmpty()) {
			Vector2f.add(leaderPos, offset, goalPos);
			Vector2f.sub(goalPos, pos, goalDir);
			if (goalDir.x > 0) {
				state &= ~State.LEFT;
				state |= State.WALKING;
			} else if (goalDir.x < 0) {
				state |= State.LEFT;
				state |= State.WALKING;
			} else {
				state &= ~State.WALKING;
			}
		} else if (!jumpCommands.isEmpty() && (mode == FollowMode.NORMAL || mode == FollowMode.RETURNING)) {
			mode = FollowMode.WALKING_TO_JUMP;
			goalPos = jumpCommands.poll();
		} else if (mode == FollowMode.WALKING_TO_JUMP) {
			Vector2f.sub(goalPos, pos, goalDir);
			if (goalDir.x > 0) {
				state &= ~State.LEFT;
				state |= State.WALKING;
			} else if (goalDir.x < 0) {
				state |= State.LEFT;
				state |= State.WALKING;
			} else {
				state &= ~State.WALKING;
				mode = FollowMode.JUMPING;
			}
		}

		if (mode == FollowMode.JUMPING) {
			goalPos = leaderPos;
			Vector2f.sub(goalPos, pos, goalDir);
			if (goalDir.x > 0) {
				state &= ~State.LEFT;
				state |= State.WALKING;
			} else if (goalDir.x < 0) {
				state |= State.LEFT;
				state |= State.WALKING;
			} else {
				state &= ~State.WALKING;
				mode = FollowMode.JUMPING;
			}
			jump();
		}

		boolean r = super.update(level);

		if ((state & State.JUMPING) != State.JUMPING && mode == FollowMode.JUMPING) {
			mode = FollowMode.RETURNING;
			goalPos = new Vector2f();
		}

		return r;
	}

	@Override
	public void jump() {
		super.jump();
	}
}
