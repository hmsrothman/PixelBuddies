package editor;

import java.util.HashMap;
import java.util.Map;

import engine.Texture;

public class TileTextures {
	static final String BASE_STRING = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\";

	private static Map<String, Texture> textures = new HashMap<String, Texture>();

	public static Texture getTexture(String name) {
		if (textures.containsKey(name)) {
			return textures.get(name);
		} else {
			textures.put(name, new Texture(BASE_STRING + name));
			return textures.get(name);
		}
	}

}
