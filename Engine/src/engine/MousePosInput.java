package engine;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import otherpeoplesmath.Vector2f;

/**
 * @author TheKingInYellow
 */
public class MousePosInput extends GLFWCursorPosCallback {
	static double xpos;
	static double ypos;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		MousePosInput.xpos = xpos;
		MousePosInput.ypos = ypos;
	}

	public static Vector2f pos() {
		return new Vector2f((float) xpos, (float) ypos);
	}
}