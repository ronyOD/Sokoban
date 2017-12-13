package heuristics;

import model.data.positions.Position;
/**
* <h1>Distance</h1>
* This interface defines the method for heuristics
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public interface Distance {
	public double getDistance(Position from, Position to);
}
