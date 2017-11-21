package people;

public abstract class State {
	public static final byte WALKING = 0b00000001;
	public static final byte LEFT = 0b00000010;
	public static final byte JUMPING = 0b00000100;
	public static final byte PLAYING = 0b00001000;
}