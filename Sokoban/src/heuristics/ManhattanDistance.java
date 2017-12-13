package heuristics;

import model.data.positions.Position;

public class ManhattanDistance implements Distance {

	@Override
	public double getDistance(Position from, Position to) {
		
	    return Math.abs(to.getW() - from.getW()) + 
	      Math.abs(to.getH() - from.getH());
	}

}
