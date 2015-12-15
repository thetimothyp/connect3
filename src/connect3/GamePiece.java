package connect3;

public class GamePiece
{
	private String name;
	private int pointValue;
	private Coordinates coordinates;
	
	public GamePiece() {
		name = "";
		pointValue = 0;
		coordinates = new Coordinates();
	}
	
	public GamePiece(String name, int pointValue) {
		this.name = name;
		this.pointValue = pointValue;
		coordinates = new Coordinates();
	}
	
	public GamePiece(GamePiece g) {
		this.name = g.getName();
		this.pointValue = g.getPointValue();
		this.coordinates = g.getCoords();
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCoords(int x, int y) {
		coordinates.setX(x);
		coordinates.setY(y);
	}
	
	public Coordinates getCoords() {
		return coordinates;
	}
	
	public boolean equals(Object a) {
		GamePiece x = (GamePiece) a;
		return this.getName().equals(x.getName());
	}
	
	public String toString() {
		return this.getName();
	}
}
