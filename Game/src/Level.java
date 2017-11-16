import java.util.ArrayList;

import Engine.SpriteBatch;
import Engine.Texture;
import otherpeoplesmath.Vector4f;

public class Level {
	float scale = 5;

	int[][] levelData;

	ArrayList<Vector4f> destRects = new ArrayList<Vector4f>();
	Vector4f uvRect = new Vector4f(0,0,1,1);
	Vector4f color = new Vector4f(1,1,1,1);
	float depth = 0;
	
	Texture texture = new Texture("C:\\Users\\Simon\\Code\\Java\\Game\\Tracy\\FLOOR.bmp");
	public Level(String path) {
		levelData = LevelLoader.loadLevel(path);
		for (int x = 0; x < levelData.length; x++) {
			for (int y = 0; y < levelData[0].length; y++) {
				if (levelData[x][y] != 0) {
					destRects.add(new Vector4f(x * scale, 5-(y * scale), scale, scale));
				}
			}
		}
	}
	
	public void draw(SpriteBatch batch){
		destRects.stream().forEach(r -> batch.draw(r, uvRect, texture.id, depth, color));
	}

}
