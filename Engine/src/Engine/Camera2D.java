package engine;

import otherpeoplesmath.MathUtil;
import otherpeoplesmath.Matrix4f;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector3f;
import otherpeoplesmath.Vector4f;

public class Camera2D {
	private int screenWidth, screenHeight;
	private boolean needsUpdate;
	private float scale;
	public Vector2f pos;
	private Matrix4f cameraMat;
	private Matrix4f orthoMat;
	private Matrix4f inverted;

	public Camera2D(int screenWidth, int screenHeight) {
		pos = new Vector2f(0, 0);
		scale = 1f;
		cameraMat = new Matrix4f();
		needsUpdate = true;
		orthoMat = new Matrix4f();
		init(screenWidth, screenHeight);
	}

	private void init(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		orthoMat = MathUtil.toOrtho2D(orthoMat, 0.0f, 0.0f, screenWidth / scale, -screenHeight / scale);
		cameraMat = orthoMat.translate(new Vector3f(-pos.x, -pos.y, 0));
		inverted = new Matrix4f();
		Matrix4f.invert(cameraMat, inverted);
		needsUpdate = false;
	}

	public boolean update() {
		if (needsUpdate) {
			Vector3f translate = new Vector3f(-pos.x, -pos.y, 0);

			orthoMat = MathUtil.toOrtho2D(orthoMat, 0.0f, 0.0f, screenWidth / scale, -screenHeight / scale);

			cameraMat = Matrix4f.translate(translate, orthoMat, cameraMat); // TRANSLATE

			Matrix4f.invert(cameraMat, inverted);

			needsUpdate = false;
			return true;
		}
		return false;
	}

	public void translate(Vector2f dist) {
		Vector2f.add(pos, dist, pos);
		needsUpdate = true;
	}

	public void setPosition(Vector2f position) {
		pos = position;
		needsUpdate = true;
	}

	public void setScale(float scale) {
		this.scale = scale;
		needsUpdate = true;
	}

	Vector2f getPosition() {
		return pos;
	}

	public float getScale() {
		return scale;
	}

	public Matrix4f getMatrix() {
		return cameraMat;
	}

	public Vector2f toWorldCoords(Vector2f screenCoords) {
		Vector4f pos = new Vector4f(screenCoords.x, screenCoords.y, 0, 1);
		Matrix4f.transform(inverted, pos, pos);
		return new Vector2f(pos.x/pos.w, pos.y/pos.w);
	}
}
