package people;

import java.util.HashMap;
import java.util.Map;

import engine.Texture;

public class PeopleTextures {
	private static class Tex {
		String name;
		int walk;

		public Tex(String Name, int Walk) {
			name = Name;
			walk = Walk;
		}
	}

	static final String BASE_STRING = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND ";

	private static Map<Tex, Texture> textures = new HashMap<Tex, Texture>();

	public static Texture[] getWalkCycle(String name) {
		Texture[] textures = new Texture[4];
		Tex tex = new Tex(name, 2);
		textures[0] = getTexture(tex);
		tex = new Tex(name, 1);
		textures[1] = getTexture(tex);
		tex = new Tex(name, 2);
		textures[2] = getTexture(tex);
		tex = new Tex(name, 3);
		textures[3] = getTexture(tex);
		return textures;
	}

	public static Texture getJumpSprite(String name) {
		Tex tex = new Tex(name, 5);
		if (textures.containsKey(tex)) {
			return textures.get(tex);
		} else {
			System.out.println(getJumpPath(name));
			textures.put(tex, new Texture(getJumpPath(name)));
			return textures.get(tex);
		}
	}

	private static String getWalkPath(Tex tex) {
		return (BASE_STRING + tex.name + " " + tex.walk + ".bmp");
	}

	private static String getJumpPath(String name) {
		return (BASE_STRING + "Jump! " + name + ".bmp");
	}

	private static Texture getTexture(Tex tex) {
		if (textures.containsKey(tex)) {
			return textures.get(tex);
		} else {
			textures.put(tex, new Texture(getWalkPath(tex)));
			return textures.get(tex);
		}
	}

	public static Texture[] getViolin() {
		Texture[] t = new Texture[2];
		t[0] = new Texture(BASE_STRING + "Violin Right.bmp");
		t[1] = new Texture(BASE_STRING + "Violin Left.bmp");
		return t;
	}
}
