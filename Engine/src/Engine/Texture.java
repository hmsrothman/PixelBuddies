package Engine;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_BGR;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	public int id, width, height;

	public Texture(String path) {
		try {
			System.out.println(path);
			BufferedImage img = javax.imageio.ImageIO.read(new File(path));

			width = img.getWidth();
			height = img.getHeight();

			int[] pixelArray = new int[width * height * 4];

			System.out.println(pixelArray[0]);

			img.getRaster().getPixels(0, 0, width, height, pixelArray);

			System.out.println(pixelArray[0]);

			byte[] pixels = new byte[width * height * 4];

			for (int i = 0; i < pixels.length; i++) {
				if (pixelArray[i] > 255) {
					System.err.println(pixelArray[i]);
				}
				pixels[i] = (byte) pixelArray[i];
			}

			int color = 0;

			if (img.getColorModel().getNumComponents() == 3) {
				color = GL_RGB;
			} else {
				color = GL_RGBA;
			}

			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, color, GL_UNSIGNED_BYTE,
					createByteBuffer(pixels));
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glBindBuffer(GL_TEXTURE_2D, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
