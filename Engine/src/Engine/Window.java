package engine;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class Window {
	long id = 0;

	public Window(String name, int width, int height) {
		create(name, width, height);

	}

	public void create(String name, int width, int height) {
		System.out.println("start to make window");
		id = glfwCreateWindow(width, height, name, 0, 0);
		if (id == 0) {
			System.err.println("window creation failed");
		}

		glfwSetWindowPos(id, 3, 30); // good enough i guess
		glfwMakeContextCurrent(id);
		glfwShowWindow(id);

		glfwSetKeyCallback(id, new KeyboardInput());
		glfwSetMouseButtonCallback(id, new MouseButtonInput());
		glfwSetCursorPosCallback(id, new MousePosInput());

		System.out.println("made window");
	}

	public void swapBuffers() {
		glfwSwapBuffers(id);
	}
}
