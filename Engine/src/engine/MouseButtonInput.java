package engine;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonInput extends GLFWMouseButtonCallback {
	static int button;
	static int action;

	@Override
	public void invoke(long window, int button, int action, int mods) {
		MouseButtonInput.button = button;
		MouseButtonInput.action = action;
	}

	public static int[] getMouseButton() {
		int[] i = { button, action };
		return i;
	}

}