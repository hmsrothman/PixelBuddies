package editor;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import engine.Camera2D;
import engine.GLSLProgram;
import engine.KeyboardInput;
import engine.MainGame;
import engine.MouseButtonInput;
import engine.MousePosInput;
import engine.SpriteBatch;
import otherpeoplesmath.Matrix4f;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public class Game extends MainGame {

	SpriteBatch batch;
	GLSLProgram shader;

	Camera2D levelCamera;
	Camera2D guiCamera;

	Level level;
	GUI gui;

	int current = -1;

	@Override
	protected void onInit() {
		batch = new SpriteBatch();
		batch.init();

		levelCamera = new Camera2D(48, 27);
		guiCamera = new Camera2D(48, 27);
		levelCamera.update();
		guiCamera.update();

		shader.use();
		int loc = shader.getUniformLocation("cameraMat");
		FloatBuffer mat = BufferUtils.createFloatBuffer(16);
		Matrix4f matrix = levelCamera.getMatrix();
		mat.put(matrix.m00).put(matrix.m01).put(matrix.m02).put(matrix.m03);
		mat.put(matrix.m10).put(matrix.m11).put(matrix.m12).put(matrix.m13);
		mat.put(matrix.m20).put(matrix.m21).put(matrix.m22).put(matrix.m23);
		mat.put(matrix.m30).put(matrix.m31).put(matrix.m32).put(matrix.m33);
		mat.flip();
		glUniformMatrix4fv(loc, false, mat);
		levelCamera.setScale(0.5f);
		guiCamera.setScale(0.5f);

		level = new Level("C:\\Users\\Simon\\Code\\Java\\Game\\testlevel.csv");
		gui = new GUI();
		levelCamera.translate(new Vector2f(0, 20));
	}

	@Override
	protected void initShaders() {
		shader = new GLSLProgram("C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.vert",
				"C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.frag");
	}

	@Override
	protected void update() {

		if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
			levelCamera.translate(new Vector2f(-0.5f, 0));
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
			levelCamera.translate(new Vector2f(0.5f, 0));
		}

		if (KeyboardInput.isKeyDown(GLFW_KEY_1)) {
			current = 1;
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_2)) {
			current = 2;
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_3)) {
			current = 3;
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_4)) {
			current = 4;
		}

		if (MouseButtonInput.getMouseButton()[1] == GLFW_PRESS) {
			System.out.println("click");
			Vector2f pos = levelCamera.toWorldCoords(MousePosInput.pos());
			System.out.println(pos);
			gui.tiles.add(new Tile(0, new Vector4f(pos.x, -pos.y - 20, 4, 4)));
			System.out.println(gui.tiles.size());
		}

		levelCamera.update();
	}

	@Override
	protected void draw() {

		glClearDepth(1.0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glActiveTexture(GL_TEXTURE0);

		int textureLocation = shader.getUniformLocation("sampler");
		glUniform1i(textureLocation, 0);

		shader.setMatrix("cameraMat", guiCamera.getMatrix());
		batch.begin();
		gui.drawGUI(batch);
		batch.end();
		batch.renderBatch();

		shader.use();
		shader.setMatrix("cameraMat", levelCamera.getMatrix());
		batch.begin();

		level.drawLevel(batch);

		batch.end();
		batch.renderBatch();

		batch.begin();

		level.drawBackground(batch);
		level.drawGrid(batch);
		batch.end();
		batch.renderBatch();

		shader.unuse();
	}
}
