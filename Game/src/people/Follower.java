package people;

import java.util.LinkedList;

import game.Level;
import game.State;
import otherpeoplesmath.Vector2f;

enum FollowMode {
	NORMAL, JUMPING, RETURNING
}

class FollowCommand {
	Vector2f location;
	boolean jumpOnArrival;

	FollowCommand(Vector2f loc, boolean jump) {
		location = loc;
		jumpOnArrival = jump;
	}
}

public class Follower extends Person {
	LinkedList<FollowCommand> commands = new LinkedList<FollowCommand>();
	FollowCommand currentCommand = new FollowCommand(new Vector2f(), false);

	FollowMode mode = FollowMode.NORMAL;

	public Follower(Vector2f loc, String name) {
		super(loc, name);
	}

	public void issueCommand(FollowCommand command) {
		commands.addLast(command);
	}

	@Override
	public boolean update(Level level) {
		System.out.println(commands.size());
		if (mode != FollowMode.JUMPING) {
			while (!currentCommand.jumpOnArrival && !commands.isEmpty()) {
				currentCommand = commands.pollFirst();
				if (currentCommand.jumpOnArrival) {
					mode = FollowMode.JUMPING;
					System.out.println("jump set");
				}
			}
		}
		Vector2f goalDir = new Vector2f();
		Vector2f.sub(currentCommand.location, pos, goalDir);
		if (goalDir.x > 0) {
			state &= ~State.LEFT;
			state |= State.WALKING;
		} else if (goalDir.x < 0) {
			state |= State.LEFT;
			state |= State.WALKING;
		} else {
			state &= ~State.WALKING;
			if (currentCommand.jumpOnArrival) {
				jump();
				currentCommand.jumpOnArrival = false;
			}
			if (landed) {
				System.out.println("killed jumpstate");
				mode = FollowMode.NORMAL;
				landed = false;
			}
		}
		return super.update(level);
	}

	@Override
	public void jump() {
		super.jump();
	}
}
