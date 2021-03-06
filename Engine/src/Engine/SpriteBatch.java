package engine;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.BufferUtils;

import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

class Vertex {
	Vector2f coord = new Vector2f(0, 0);
	Vector4f color = new Vector4f(0, 0, 0, 0);
	Vector2f uv = new Vector2f(0, 0);

	public Vertex() {

	}
}

class Glyph {
	int texture = 0;
	float depth = 0;

	Vertex topLeft = new Vertex();
	Vertex topRight = new Vertex();
	Vertex bottomLeft = new Vertex();
	Vertex bottomRight = new Vertex();

	public Glyph(Vector4f destRect, Vector4f uvRect, int Texture, float Depth, Vector4f color) {

		texture = Texture;
		depth = Depth;

		topLeft.color = color;
		topLeft.coord = new Vector2f(destRect.x, destRect.y + destRect.w);
		topLeft.uv = new Vector2f(uvRect.x, uvRect.y + uvRect.w);

		topRight.color = color;
		topRight.coord = new Vector2f(destRect.x + destRect.z, destRect.y + destRect.w);
		topRight.uv = new Vector2f(uvRect.x + uvRect.z, uvRect.y + uvRect.w);

		bottomLeft.color = color;
		bottomLeft.coord = new Vector2f(destRect.x, destRect.y);
		bottomLeft.uv = new Vector2f(uvRect.x, uvRect.y);

		bottomRight.color = color;
		bottomRight.coord = new Vector2f(destRect.x + destRect.z, destRect.y);
		bottomRight.uv = new Vector2f(uvRect.x + uvRect.z, uvRect.y);
	}
}

class RenderBatch {
	int offset;
	int numVertices;
	int texture;

	public RenderBatch(int Offset, int NumVertices, int Texture) {
		offset = Offset;
		numVertices = NumVertices;
		texture = Texture;
	}
}

public class SpriteBatch {
	private int vboID = 0;
	private int vaoID = 0;

	ArrayList<Glyph> glyphs = new ArrayList<Glyph>();
	ArrayList<RenderBatch> renderBatches = new ArrayList<RenderBatch>();

	public void init() {
		createVertexArray();
	}

	public void begin() {
		glyphs.clear();
		renderBatches.clear();
	}

	public void end() {
		sortGlyphs();
		createRenderBatches();

	}

	public void draw(Vector4f destRect, Vector4f uvRect, int texture, float depth, Vector4f color) {
		glyphs.add(new Glyph(destRect, uvRect, texture, depth, color));
	}

	public void createRenderBatches() {
		if (glyphs.isEmpty()) {
			return;
		}

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		vertices.ensureCapacity(glyphs.size() * 6);

		int offset = 0;
		renderBatches.add(new RenderBatch(offset, 6, glyphs.get(0).texture));
		vertices.add(glyphs.get(0).topLeft);
		vertices.add(glyphs.get(0).bottomLeft);
		vertices.add(glyphs.get(0).bottomRight);
		vertices.add(glyphs.get(0).bottomRight);
		vertices.add(glyphs.get(0).topRight);
		vertices.add(glyphs.get(0).topLeft);
		offset += 6;

		for (int currentGlyph = 1; currentGlyph < glyphs.size(); currentGlyph++) {
			if (glyphs.get(currentGlyph).texture != glyphs.get(currentGlyph - 1).texture) {
				renderBatches.add(new RenderBatch(offset, 6, glyphs.get(currentGlyph).texture));
			} else {
				renderBatches.get(renderBatches.size() - 1).numVertices += 6;
			}
			vertices.add(glyphs.get(currentGlyph).topLeft);
			vertices.add(glyphs.get(currentGlyph).bottomLeft);
			vertices.add(glyphs.get(currentGlyph).bottomRight);
			vertices.add(glyphs.get(currentGlyph).bottomRight);
			vertices.add(glyphs.get(currentGlyph).topRight);
			vertices.add(glyphs.get(currentGlyph).topLeft);
			offset += 6;
		}

		float[] motherfucker = new float[vertices.size() * 8];
		int i = 0;
		for (Vertex v : vertices) {
			motherfucker[i++] = v.coord.x;
			motherfucker[i++] = v.coord.y;

			motherfucker[i++] = v.uv.x;
			motherfucker[i++] = v.uv.y;
		}

		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, 4 * 8 * vertices.size(), // ORPHAN BUFFER
				GL_DYNAMIC_DRAW);
		glBufferSubData(GL_ARRAY_BUFFER, 0, toFloatBuffer(motherfucker));
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void renderBatch() {
		glBindVertexArray(vaoID);
		for (int i = 0; i < renderBatches.size(); i++) {
			glBindTexture(GL_TEXTURE_2D, renderBatches.get(i).texture);
			glDrawArrays(GL_TRIANGLES, renderBatches.get(i).offset, renderBatches.get(i).numVertices);
		}
		glBindVertexArray(0);
	}

	public void sortGlyphs() {
		glyphs.sort(new Comparator<Glyph>() {

			@Override
			public int compare(Glyph o1, Glyph o2) {
				return o1.texture - o2.texture;
			}

		});
	}

	public void createVertexArray() {
		if (vaoID == 0) {
			vaoID = glGenVertexArrays();
		}
		glBindVertexArray(vaoID);

		if (vboID == 0) {
			vboID = glGenBuffers();
		}
		glBindBuffer(GL_ARRAY_BUFFER, vboID);

		// Enable Vertex Attribs
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		// Setup Vertex Attrib Pointers
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);

		glBindVertexArray(0);
	}

	public static FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
