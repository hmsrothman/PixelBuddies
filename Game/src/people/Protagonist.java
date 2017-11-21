package people;

import Engine.Camera2D;
import Engine.Texture;
import game.Level;
import otherpeoplesmath.Vector2f;

public class Protagonist extends Leader {

	Camera2D camera;

	Texture[] playingTextures;

	public Protagonist(Vector2f loc, String name, Camera2D camera) {
		super(loc, name);
		this.camera = camera;
		playingTextures = PeopleTextures.getViolin();
	}

	public Protagonist(Vector2f loc, String name, Follower follower, Camera2D camera) {
		super(loc, name, follower);
		playingTextures = PeopleTextures.getViolin();
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

	@Override
	public boolean update(Level level) {
		boolean r = super.update(level);
		handlePlaying();
		return r;
	}

	private void handlePlaying() {
		if ((state & State.PLAYING) == State.PLAYING) {
			uvRect.z = 1;
			if ((state & State.LEFT) == State.LEFT) {
				currentTexture = playingTextures[1];
			} else {
				currentTexture = playingTextures[0];
			}
		}
	}

	public void play() {
		if ((state & State.WALKING) != State.WALKING && (state & State.JUMPING) != State.JUMPING) {
			state |= State.PLAYING;
		} else {
			stopPlay();
		}
	}

	public void stopPlay() {
		state &= ~State.PLAYING;
	}
}
