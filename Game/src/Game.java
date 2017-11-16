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
import Engine.MainGame;
import Engine.SpriteBatch;
import Engine.Texture;
import otherpeoplesmath.Matrix4f;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public class Game extends MainGame {
	Texture tracy1;
	Texture tracy2;
	Texture tracy3;
	Texture tracy4;

	SpriteBatch batch;
	GLSLProgram shader;

	Camera2D camera;

	int i = 0;
	int j = 0;

	@Override
	protected void onInit() {
		tracy1 = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 1 transparent.bmp");
		tracy2 = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 2 transparent.bmp");
		tracy3 = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 3 transparent.bmp");
		tracy4 = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 1 transparent.bmp");

		batch = new SpriteBatch();
		batch.init();

		camera = new Camera2D(48,27);
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
		// camera.setScale(0.01f);
	}

	@Override
	protected void initShaders() {
		shader = new GLSLProgram("C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.vert",
				"C:\\Users\\Simon\\Code\\Java\\Game\\src\\shaders\\simple.frag");
	}

	@Override
	protected void update() {
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


		Vector4f destRect = new Vector4f(0, 0, 10*tracy1.width/tracy1.height, 10);
		Vector4f uvRect = new Vector4f(0, 0, 1, 1);
		int texture = 0;
		if (i % 2 == 0) {
			texture = tracy1.id;
		} else if (i % 4 == 1) {
			texture = tracy2.id;
		} else if (i % 4 == 3) {
			texture = tracy3.id;
		}
		float depth = 0;
		Vector4f color = new Vector4f(1, 1, 1, 1);
		batch.draw(destRect, uvRect, texture, depth, color);

		batch.end();
		batch.renderBatch();
		shader.unuse();
		if (j++ == 12) {
			i++;
			camera.translate(new Vector2f(tracy1.width*5/tracy1.height,0));
			j = 0;
		}
		if (i == 4) {
			i = 0;
		}
		//camera.translate(new Vector2f(0.08f,0));
	}
}
