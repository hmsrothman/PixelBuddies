
import java.util.HashMap;
import java.util.Map;

import Engine.Texture;

public class PeopleTextures {
	static final String TRACY_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Tracy 1.bmp";
	static final String TRACY_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Tracy 2.bmp";
	static final String TRACY_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Tracy 3.bmp";

	static final String SIMON_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Simon 1.bmp";
	static final String SIMON_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Simon 2.bmp";
	static final String SIMON_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Simon 3.bmp";

	static final String KAT_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Kat 1.bmp";
	static final String KAT_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Kat 2.bmp";
	static final String KAT_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Kat 3.bmp";
	
	static final String KATHERINE_WALK_1 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Treecko 1.bmp";
	static final String KATHERINE_WALK_2 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Treecko 2.bmp";
	static final String KATHERINE_WALK_3 = "C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\Sweet, Sweet BMPs\\STND Treecko 3.bmp";

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
			
			textures.put(KAT_WALK_1, new Texture(KAT_WALK_1));
			textures.put(KAT_WALK_2, new Texture(KAT_WALK_2));
			textures.put(KAT_WALK_3, new Texture(KAT_WALK_3));
			
			textures.put(KATHERINE_WALK_1, new Texture(KATHERINE_WALK_1));
			textures.put(KATHERINE_WALK_2, new Texture(KATHERINE_WALK_2));
			textures.put(KATHERINE_WALK_3, new Texture(KATHERINE_WALK_3));
			initialized = true;
		}
	}
}
