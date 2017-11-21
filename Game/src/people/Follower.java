package people;

import java.util.LinkedList;

import game.Level;
import otherpeoplesmath.Vector2f;

public class Follower extends Person {
	enum FollowMode {
		FORCE_FOLLOW, JUMPING, NORMAL, WALKING_TO_JUMP
	}

	Vector2f leaderPos;
	Vector2f offset;
	private Vector2f goalDir = new Vector2f();
	private Vector2f goalPos = new Vector2f();

	LinkedList<Vector2f> jumpCommands = new LinkedList<Vector2f>();

	FollowMode mode;

	public Follower(Vector2f pos, String name) {
		super(pos, name);
		mode = FollowMode.FORCE_FOLLOW;
	}

	void issueJumpCommand(Vector2f pos) {
		jumpCommands.addLast(pos);
	}

	@Override
	public boolean update(Level level) {
		if ((mode == FollowMode.FORCE_FOLLOW || mode == FollowMode.NORMAL) && jumpCommands.isEmpty()) {
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
				mode = FollowMode.FORCE_FOLLOW;
			}

			if (Math.abs(pos.x - leaderPos.x) + Math.abs(pos.y - leaderPos.y) > -offset.x) {
				mode = FollowMode.FORCE_FOLLOW;
			} else {
				mode = FollowMode.NORMAL;
			}
		} else if (!jumpCommands.isEmpty() && (mode == FollowMode.FORCE_FOLLOW || mode == FollowMode.NORMAL)) {
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
			mode = FollowMode.NORMAL;
			goalPos = new Vector2f();
		}

		System.out.println(mode);
		return r;
	}

	@Override
	public void jump() {
		super.jump();
	}

	@Override
	protected void move(Vector2f displace, Level level) {
		if (mode == FollowMode.NORMAL) {
			float[] bounds = getTexBounds(walkCycle[0]);
			int x = (int) Math.floor((pos.x + +displace.x + bounds[1]) / level.scale);
			int y = (int) Math.ceil((pos.y + displace.y + bounds[2] - 1) / level.scale);

			if (!level.canWalk(x, y)) {
				displace.x = 0;
				state &= ~State.WALKING;
			}
		}
		super.move(displace, level);
	}

}
