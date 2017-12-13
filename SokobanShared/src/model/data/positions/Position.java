package model.data.positions;

public class Position {
	int h, w;

	public int getH() {
		return h;
	}

	public int getW() {
		return w;
	}

	public Position(int h, int w) {
		super();
		this.h = h;
		this.w = w;
	}
	public Position(Position position){
		this.h = position.h;
		this.w = position.w;
	}

	@Override
	public String toString() {
		return "Position [h=" + h + ", w=" + w + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + h;
		result = prime * result + w;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (h != other.h)
			return false;
		if (w != other.w)
			return false;
		return true;
	}
}
