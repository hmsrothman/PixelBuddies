package Engine;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	public int id, width, height;
	public int minX, maxX, minY, maxY;

	private static final int HITBOX_BUFFER = 0;

	public Texture(String path) {
		try {
			System.out.println(path);
			BufferedImage img = javax.imageio.ImageIO.read(new File(path));

			width = img.getWidth();
			height = img.getHeight();

			int[] pixelArray = new int[width * height * 4];

			img.getRaster().getPixels(0, 0, width, height, pixelArray);

			byte[] pixels = new byte[width * height * 4];

			int i = 0;

			minX = width / 2;
			maxX = width / 2;
			minY = height / 2;
			maxY = height / 2;

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					pixels[i] = (byte) pixelArray[i++];
					pixels[i] = (byte) pixelArray[i++];
					pixels[i] = (byte) pixelArray[i++];
					pixels[i] = (byte) pixelArray[i++];
					if (pixelArray[i - 1] != 0) {
						if (x < minX) {
							minX = x;
						}
						if (x > maxX) {
							maxX = x;
						}
						if (y < minY) {
							minY = y;
						}
						if (y > maxY) {
							maxY = y;
						}
					}
				}
			}

			int tmp = minY;
			minY = 44 - maxY;
			maxY = 44 - tmp;

			if (30 - maxX > minX) {
				maxX = 30 - minX;
			} else {
				minX = 30 - maxX;
			}

			minX -= HITBOX_BUFFER;
			maxX += HITBOX_BUFFER;
			maxY += HITBOX_BUFFER;

			System.out.println("X: " + minX + " , " + maxX);
			System.out.println("Y: " + minY + " , " + maxY);

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

	public Texture(BufferedImage img) {
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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, color, GL_UNSIGNED_BYTE, createByteBuffer(pixels));
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glBindBuffer(GL_TEXTURE_2D, 0);
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
