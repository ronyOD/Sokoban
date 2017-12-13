package heuristics;

import model.data.positions.Position;

public interface Distance {
	public double getDistance(Position from, Position to);
}
