package editor;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
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

import Engine.Camera2D;
import Engine.GLSLProgram;
import Engine.KeyboardInput;
import Engine.MainGame;
import Engine.SpriteBatch;
import otherpeoplesmath.Matrix4f;
import otherpeoplesmath.Vector2f;

public class Game extends MainGame {

	SpriteBatch batch;
	GLSLProgram shader;

	Camera2D camera;

	Level level;

	@Override
	protected void onInit() {
		batch = new SpriteBatch();
		batch.init();

		camera = new Camera2D(48, 27);
		camera.update();

		shader.use();
		int loc = shader.getUniformLocation("cameraMat");
		FloatBuffer mat = BufferUtils.createFloatBuffer(16);
		Matrix4f matrix = camera.getMatrix();
		mat.put(matrix.m00).put(matrix.m01).put(matrix.m02).put(matrix.m03);
		mat.put(matrix.m10).put(matrix.m11).put(matrix.m12).put(matrix.m13);
		mat.put(matrix.m20).put(matrix.m21).put(matrix.m22).put(matrix.m23);
		mat.put(matrix.m30).put(matrix.m31).put(matrix.m32).put(matrix.m33);
		mat.flip();
		glUniformMatrix4fv(loc, false, mat);
		camera.setScale(0.5f);

		level = new Level("C:\\Users\\Simon\\Code\\Java\\Game\\testlevel.csv");

		camera.translate(new Vector2f(0, 20));
	}

	@Override
	protected void initShaders() {
		shader = new GLSLProgram("C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.vert",
				"C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.frag");
	}

	@Override
	protected void update() {

		if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
			camera.translate(new Vector2f(-0.5f, 0));
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
			camera.translate(new Vector2f(0.5f, 0));
		} else {

		}

		if (camera.update()) {
			shader.use();
			int loc = shader.getUniformLocation("cameraMat");
			FloatBuffer mat = BufferUtils.createFloatBuffer(16);
			Matrix4f matrix = camera.getMatrix();
			mat.put(matrix.m00).put(matrix.m01).put(matrix.m02).put(matrix.m03);
			mat.put(matrix.m10).put(matrix.m11).put(matrix.m12).put(matrix.m13);
			mat.put(matrix.m20).put(matrix.m21).put(matrix.m22).put(matrix.m23);
			mat.put(matrix.m30).put(matrix.m31).put(matrix.m32).put(matrix.m33);
			mat.flip();
			glUniformMatrix4fv(loc, false, mat);
		}
	}

	@Override
	protected void draw() {

		glClearDepth(1.0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glActiveTexture(GL_TEXTURE0);

		int textureLocation = shader.getUniformLocation("sampler");
		glUniform1i(textureLocation, 0);

		shader.use();
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
