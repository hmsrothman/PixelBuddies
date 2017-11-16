import Engine.SpriteBatch;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public class Person {
	Vector2f loc;
	Name name;
	State state;

	int currentTexture;
	int[] walkCycle;

	Vector4f destRect;
	Vector4f uvRect;
	Vector4f color;
	float depth;

	float height = 39, width = 27;

	int frameCounter = 0;
	int walkIndex = 0;

	public Person(Vector2f loc, Name name) {
		PeopleTextures.loadTextures();
		this.loc = loc;
		this.name = name;
		this.state = State.StandingRight;

		depth = 0;
		destRect = new Vector4f(loc.x, loc.y, width / height * 10, 10);
		uvRect = new Vector4f(0, 0, 1, 1);
		color = new Vector4f(0, 0, 0, 0);

		walkCycle = new int[4];
		
		switch(name){
		case Simon:
			walkCycle[0] = PeopleTextures.textures.get(PeopleTextures.SIMON_WALK_2).id;
			walkCycle[1] = PeopleTextures.textures.get(PeopleTextures.SIMON_WALK_1).id;
			walkCycle[2] = PeopleTextures.textures.get(PeopleTextures.SIMON_WALK_2).id;
			walkCycle[3] = PeopleTextures.textures.get(PeopleTextures.SIMON_WALK_3).id;
			break;
		case Tracy:
			walkCycle[0] = PeopleTextures.textures.get(PeopleTextures.TRACY_WALK_1).id;
			walkCycle[1] = PeopleTextures.textures.get(PeopleTextures.TRACY_WALK_2).id;
			walkCycle[2] = PeopleTextures.textures.get(PeopleTextures.TRACY_WALK_1).id;
			walkCycle[3] = PeopleTextures.textures.get(PeopleTextures.TRACY_WALK_3).id;
			break;
		default:
			break;
		
		}
		

		currentTexture = walkCycle[0];

	}

	public boolean update() {
		frameCounter++;
		if (frameCounter >= 12) {
			switch (state) {
			case StandingLeft:
				walkIndex = 0;
				currentTexture = walkCycle[0];
				return false;
			case StandingRight:
				walkIndex = 0;
				currentTexture = walkCycle[0];
				return false;
			case WalkingLeft:
				walkIndex++;
				frameCounter = 0;
				if (walkIndex == 4) {
					walkIndex = 0;
				}
				currentTexture = walkCycle[walkIndex];
				Vector2f.add(loc, new Vector2f(width * 5 / height, 0), loc);
				destRect = new Vector4f(loc.x, loc.y, width / height * 10, 10);
				frameCounter = 0;
				return true;
			case WalkingRight:
				walkIndex++;
				frameCounter = 0;
				if (walkIndex == 4) {
					walkIndex = 0;
				}
				currentTexture = walkCycle[walkIndex];
				Vector2f.sub(loc, new Vector2f(width * 5 / height, 0), loc);
				destRect = new Vector4f(loc.x + width / height * 10, loc.y, -width / height * 10, 10);
				frameCounter = 0;
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(destRect, uvRect, currentTexture, depth, color);
	}

	public void setState(State newState) {
		state = newState;
	}

	static enum Name {
		Tracy, Simon
	}

	static enum State {
		WalkingLeft, WalkingRight, StandingLeft, StandingRight
	}
}
