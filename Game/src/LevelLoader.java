import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
	public static int[][] loadLevel(String path) {
		String split = ",";
		String line = "";

		int y = 0;
		int x = 0;

		int[][] level = new int[41][15];
		for (x = 0; x < 41; x++) {
			for (y = 0; y < 8; y++) {
				level[x][y] = 0;
			}
		}

		x = 0;
		y = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split(split);
				for (String s : splitLine) {
					if (!s.contentEquals("")) {
						//System.out.println(x);
						//System.out.println(s);
						level[x][y] = Integer.parseInt(s);
					}
					x++;
				}
				x = 0;
				y++;

				System.out.println();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (y = 0; y < 8; y++) {
			for (x = 0; x < 10; x++) {
			//	System.out.print(level[x][y]);
			}
			//System.out.println();
		}
		
		return(level);

	}
	
}
