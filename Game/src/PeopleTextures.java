
import java.util.HashMap;
import java.util.Map;

import Engine.Texture;

public class PeopleTextures {
	static final String TRACY_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 1 transparent.bmp";
	static final String TRACY_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 2 transparent.bmp";
	static final String TRACY_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\TEC Walk Cycle\\Walk 3 transparent.bmp";

	static final String SIMON_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\SSR Walk Cycle\\Walk 1 transparent.bmp";
	static final String SIMON_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\SSR Walk Cycle\\Walk 2 transparent.bmp";
	static final String SIMON_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\SSR Walk Cycle\\Walk 3 transparent.bmp";

	
	public static Map<String, Texture> textures = new HashMap<String, Texture>();

	private static boolean initialized = false;

	public static void loadTextures() {
		if (!initialized) {
			textures.put(TRACY_WALK_1, new Texture(TRACY_WALK_1));
			textures.put(TRACY_WALK_2, new Texture(TRACY_WALK_2));
			textures.put(TRACY_WALK_3, new Texture(TRACY_WALK_3));
			
			textures.put(SIMON_WALK_1, new Texture(SIMON_WALK_1));
			textures.put(SIMON_WALK_2, new Texture(SIMON_WALK_2));
			textures.put(SIMON_WALK_3, new Texture(SIMON_WALK_3));
			initialized = true;
		}
	}
}
