import java.util.ArrayList;

import Engine.SpriteBatch;
import Engine.Texture;
import otherpeoplesmath.Vector2f;
import otherpeoplesmath.Vector4f;

public class Level {
	class Tile {
		int tex;
		Vector4f rect;

		public Tile(int texture, Vector4f rectangle) {
			tex = texture;
			rect = rectangle;
		}
	}

	float scale = 5;

	int[][] levelData;

	ArrayList<Tile> tiles = new ArrayList<Tile>();
	Texture[] textures = new Texture[3];
	
	Vector4f uvRect = new Vector4f(0, 0, 1, 1);
	Vector4f color = new Vector4f(1, 1, 1, 1);
	float depth = 0;

	public Level(String path) {
		textures[0] = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\FLOOR.bmp");
		textures[2] = new Texture(otherpeoplescode.FontUtil.stringToBufferedImage("fuck"));
		textures[1] = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\FLOOR.bmp");
		//textures[2] = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Assets\\FLOOR.bmp");

		levelData = LevelLoader.loadLevel(path);
		for (int x = 0; x < levelData.length; x++) {
			for (int y = 0; y < levelData[0].length; y++) {
				if (levelData[x][y] != 0) {
					tiles.add(new Tile(textures[levelData[x][y]-1].id,new Vector4f(x * scale, 5 - (y * scale), scale, scale)));
				}
			}
		}
	}

	public void draw(SpriteBatch batch) {
		tiles.stream().forEach(r -> batch.draw(r.rect, uvRect, r.tex, depth, color));
	}

	public boolean canWalk(int x, int y){
		y = 2-y;
		
		System.out.println(x);
		if(x<0||x>=levelData.length||y<0||y>=levelData[0].length){
			return false;
		}
		return levelData[x][y]==2;
	}
}

