package engine;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import otherpeoplesmath.Matrix4f;

public class GLSLProgram {
	private int programID, vertexShaderID, fragmentShaderID;

	public GLSLProgram(String vertexPath, String fragmentPath) {
		makeProgram(vertexPath, fragmentPath);
	}

	public void makeProgram(String vertexPath, String fragmentPath) {
		compileShaders(vertexPath, fragmentPath);
		linkProgram();
	}

	public void compileShaders(String vertexPath, String fragmentPath) {
		if ((vertexShaderID = glCreateShader(GL_VERTEX_SHADER)) == 0) {
			System.err.println("Vertex Shader Allocation Failure");
		}
		;

		if ((fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER)) == 0) {
			System.err.println("Fragment Shader Allocation Failure");
		}
		;

		compileShader(vertexPath, vertexShaderID);
		compileShader(fragmentPath, fragmentShaderID);
	}

	private void compileShader(String path, int id) {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer);
				result.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		glShaderSource(id, result.toString());
		glCompileShader(id);

		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(id, 500));
			System.err.println("Could not compile shader");
		}
	}

	private void linkProgram() {
		programID = glCreateProgram();

		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);

		glLinkProgram(programID);

		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println('1' + glGetProgramInfoLog(programID, 500) + '1');
			System.err.println("Could not link shader");
		}

		glValidateProgram(programID);

		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);

		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
	}

	public int getUniformLocation(String name) {
		int location = glGetUniformLocation(programID, name);
		if (location == -1) {
			System.err.println("Couldn't find uniform: " + name);
		}
		return location;
	}

	public void use() {
		glUseProgram(programID);
	}

	public void unuse() {
		glUseProgram(0);
	}

	public void destroy() {
		glDeleteProgram(programID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
	}

	public void setMatrix(String name, Matrix4f matrix) {
		use();
		int loc = getUniformLocation("cameraMat");
		FloatBuffer mat = BufferUtils.createFloatBuffer(16);
		mat.put(matrix.m00).put(matrix.m01).put(matrix.m02).put(matrix.m03);
		mat.put(matrix.m10).put(matrix.m11).put(matrix.m12).put(matrix.m13);
		mat.put(matrix.m20).put(matrix.m21).put(matrix.m22).put(matrix.m23);
		mat.put(matrix.m30).put(matrix.m31).put(matrix.m32).put(matrix.m33);
		mat.flip();
		glUniformMatrix4fv(loc, false, mat);
	}
}
