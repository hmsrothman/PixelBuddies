package editor;

import java.util.ArrayList;

import engine.SpriteBatch;
import engine.Texture;
import otherpeoplesmath.Vector4f;

public class GUI {
	ArrayList<Tile> tiles = new ArrayList<Tile>();
	Vector4f uvRect = new Vector4f(0, 0, 1, 1);
	Vector4f color = new Vector4f(0, 0, 0, 0);
	Texture[] textures = new Texture[4];

	public GUI() {
		textures[0] = TileTextures.getTexture("Butts Tunnels Wall Texture.bmp");
		textures[1] = TileTextures.getTexture("Lower Floor Texture.bmp");
		textures[2] = TileTextures.getTexture("Floor Texture.bmp");
		textures[3] = TileTextures.getTexture("FLOOR.bmp");

		tiles.add(new Tile(textures[0].id, new Vector4f(-20, -10, 3, 3)));
		tiles.add(new Tile(textures[1].id, new Vector4f(-20, -5, 3, 3)));
		tiles.add(new Tile(textures[2].id, new Vector4f(-20, -0, 3, 3)));
		tiles.add(new Tile(textures[3].id, new Vector4f(-20, 5, 3, 3)));

	}

	public void drawGUI(SpriteBatch batch) {
		tiles.stream().forEach(r -> batch.draw(r.rect, uvRect, r.tex, 0, color));
	}
}
