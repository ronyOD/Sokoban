package model.data.loaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.cells.Wall;
import model.data.levels.Level;
import model.data.levels.LevelException;
import model.data.positions.Position;

public class MyTextLevelLoader implements LevelLoader {
	@Override
	public Level load(InputStream inputStream) throws LevelLoaderException {

		ArrayList<String> lines = new ArrayList<String>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}

		int height = lines.size();
		Cell[][] cells = new Cell[height + 1][];
		Position playerPosition = null;
		ArrayList<Position> targets = new ArrayList<Position>();

		int h = height;
		for (String line : lines) {
			// System.out.println(line);
			int width = line.length();
			int w = 1;
			cells[h] = new Cell[width + 1];
			for (int index = 0; index < width; index++) {
				char c = line.charAt(index);
				// System.out.println("Index: " + index + " Char: '" + c + "'");
				if (isDecodePlayer(c)) {
					playerPosition = new Position(h, w);
					cells[h][w] = new Floor();
				} else {
					cells[h][w] = decode(c);
					if(cells[h][w] instanceof Target){
						targets.add(new Position(h,w));
					}
				}
				
				w++;
			}
			h--;
		}

		try {
			return new Level(cells, playerPosition, targets);
		} catch (LevelException e) {
			throw new LevelLoaderException(e);
		}
	}

	private boolean isDecodePlayer(char c) {
		return (c == 'A');
	}

	@Override
	public void save(OutputStream outputStream, Level level) throws LevelLoaderException {

		if (level == null)
			return; // no level to save

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			Position position;
			Cell cell;
			for (int h = level.height(); h > 0; h--) {
				for (int w = 1;; w++) {
					position = new Position(h, w);
					if ((cell = level.get(position)) == null)
						break;
					bufferedWriter.write(level.getPlayerPosition().equals(position) ? encodePlayer() : encode(cell));
				}
				bufferedWriter.write('\n');
			}
			bufferedWriter.flush();
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}
	}

	private char encodePlayer() {
		return 'A';
	}

	private char encode(Cell cell) throws LevelLoaderException {
		if (cell instanceof Floor) {
			return ' ';
		} else if (cell instanceof Wall) {
			return '#';
		} else if (cell instanceof Box) {
			return '@';
		} else if (cell instanceof Target) {
			return 'o';
		}
		throw new LevelLoaderException("unknown cell type: " + cell);
	}

	private Cell decode(char c) throws LevelLoaderException {
		if (c == ' ') {
			return new Floor();
		} else if (c == '#') {
			return new Wall();
		} else if (c == '@') {
			return new Box();
		} else if (c == 'o') {
			return new Target();
		}
		throw new LevelLoaderException("unknown cell char: " + c);
	}

}
