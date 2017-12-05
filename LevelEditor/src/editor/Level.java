package editor;

import java.util.ArrayList;

import engine.SpriteBatch;
import engine.Texture;
import otherpeoplesmath.Vector4f;

class Tile {
	int tex;
	Vector4f rect;

	public Tile(int texture, Vector4f rectangle) {
		tex = texture;
		rect = rectangle;
	}
}

public class Level {
	public float scale = 5 * 30 / 45f;
	public int yoffset = 0;

	int[][] levelData;

	ArrayList<Tile> tiles = new ArrayList<Tile>();
	ArrayList<Tile> background = new ArrayList<Tile>();
	ArrayList<Tile> grid = new ArrayList<Tile>();

	Texture[] textures = new Texture[40];

	Vector4f uvRect = new Vector4f(0, 0, 1, 1);
	Vector4f color = new Vector4f(1, 1, 1, 1);
	float depth = 0;

	public Level() {
		setupLevel();
		yoffset = 7;
		fillTileArray();
	}

	public Level(String path) {
		setupLevel();

		levelData = LevelLoader.loadLevel(path);
		for (int y = 0; y < levelData[0].length; y++) {
			if (levelData[0][y] == 5) {
				levelData[0][y] = 2;
				yoffset = y;
			}
		}
		fillTileArray();
	}

	private void setupLevel() {
		textures[0] = TileTextures.getTexture("Butts Tunnels Wall Texture.bmp");
		textures[1] = TileTextures.getTexture("Lower Floor Texture.bmp");
		textures[2] = TileTextures.getTexture("Floor Texture.bmp");
		textures[3] = TileTextures.getTexture("FLOOR.bmp");

		for (int x = 0; x < 200; x += scale * 6) {
			for (float y = -scale * 4; y < 50; y += scale * 6) {
				background.add(new Tile(textures[0].id, new Vector4f(x, y, scale * 6, scale * 6)));
			}
		}

		for (float x = 0; x < 200; x += scale) {
			grid.add(new Tile(0, new Vector4f(x - 0.1f, -20, 0.2f, 100)));
		}

		for (float y = -scale * 4; y < 50; y += scale) {
			grid.add(new Tile(0, new Vector4f(0, y - 0.1f, 200, 0.2f)));
		}
	}

	private void fillTileArray() {

		for (int x = 0; x < levelData.length; x++) {
			for (int y = 0; y < levelData[0].length; y++) {
				if (levelData[x][y] != 0) {
					if (levelData[x][y] <= 3) {
						tiles.add(new Tile(textures[levelData[x][y]].id,
								new Vector4f(x * scale, (yoffset - 1) * scale - (y * scale), scale, scale)));
					}
				}
			}
		}
	}

	public void drawLevel(SpriteBatch batch) {
		tiles.stream().forEach(r -> batch.draw(r.rect, uvRect, r.tex, depth, color));
	}

	public void drawBackground(SpriteBatch batch) {
		background.stream().forEach(r -> batch.draw(r.rect, uvRect, r.tex, depth, color));
	}

	public void drawGrid(SpriteBatch batch) {
		grid.stream().forEach(r -> batch.draw(r.rect, uvRect, r.tex, depth, color));
	}

	public boolean canWalk(int x, int y) {
		y = yoffset - y;

		if (x < 0 || x >= levelData.length || y < 0 || y >= levelData[0].length) {
			return false;
		}
		return levelData[x][y] == 2;
	}
}
