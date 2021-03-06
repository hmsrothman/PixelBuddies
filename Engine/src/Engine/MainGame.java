package engine;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public abstract class MainGame {
	private Window window = null;
	
	public MainGame(){
		init();
		gameLoop();
	}
	
	private void init(){
		Engine.initGLFW();
		window = new Window("Game", 1920,1080);
		Engine.initGL();
		initShaders();
		onInit();
	}
		
	private void gameLoop(){
		while(!glfwWindowShouldClose(window.id)){
			processInput();
			update();
			draw();
			window.swapBuffers();
		}
	}
	
	private void processInput(){
		glfwPollEvents();
	}
	
	protected abstract void onInit();
	
	protected abstract void initShaders();
	
	protected abstract void update();
	
	protected abstract void draw();
}
