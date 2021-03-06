package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
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
import engine.SpriteBatch;
import otherpeoplesmath.Matrix4f;
import otherpeoplesmath.Vector2f;
import people.Follower;
import people.Protagonist;
import people.State;

public class Game extends MainGame {

	Protagonist tracy;
	Follower simon;
	// Person kat;
	// Person katherine;

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

		simon = new Follower(new Vector2f(0, 1), "Katherine");
		tracy = new Protagonist(new Vector2f(10, 1), "Tracy", simon, camera);

		// kat = new Leader(new Vector2f(20, 0), "Kat");
		// katherine = new Leader(new Vector2f(30, 0), "Katherine");

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
		tracy.update(level);
			// camera.setPosition(new Vector2f(tracy.pos.x, camera.pos.y));
		
		simon.update(level);
		// kat.update(level);
		// katherine.update(level);

		if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
			tracy.state |= State.LEFT;
			tracy.state |= State.WALKING;

			// simon.state |= State.LEFT;
			// simon.state |= State.WALKING;

			// kat.state |= State.LEFT;
			// kat.state |= State.WALKING;

			// katherine.state |= State.LEFT;
			// katherine.state |= State.WALKING;
		} else if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
			tracy.state &= ~State.LEFT;
			tracy.state |= State.WALKING;

			/*
			 * simon.state &= ~State.LEFT; simon.state |= State.WALKING;
			 * 
			 * kat.state &= ~State.LEFT; kat.state |= State.WALKING;
			 * 
			 * katherine.state &= ~State.LEFT; katherine.state |= State.WALKING;
			 */
		} else {
			tracy.state &= ~State.WALKING;

			/*
			 * simon.state &= ~State.WALKING;
			 * 
			 * kat.state &= ~State.WALKING;
			 * 
			 * katherine.state &= ~State.WALKING;
			 */
		}

		if (KeyboardInput.isKeyDown(GLFW_KEY_SPACE)) {
			tracy.jump();

			// simon.jump();

			// kat.jump();

			// katherine.jump();
		}

		if (KeyboardInput.isKeyDown(GLFW_KEY_E)) {
			tracy.play();
		} else {
			tracy.stopPlay();
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

		tracy.draw(batch);
		simon.draw(batch);

		// katherine.draw(batch);
		// kat.draw(batch);
		level.drawLevel(batch);

		batch.end();
		batch.renderBatch();

		batch.begin();

		level.drawBackground(batch);

		batch.end();
		batch.renderBatch();
		shader.unuse();
	}
}
