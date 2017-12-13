package model.data.directions;

public class LevelDirections {

	public static Direction choose(String direction) throws DirectionException {
		switch(direction) {
			case "up":
				return new Up();
			case "down":
				return new Down();
			case "left":
				return new Left();
			case "right":
				return new Right();
		}
		throw new DirectionException("No such direction: " + direction);
	}
}
