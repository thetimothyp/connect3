package connect3;

import java.util.*;

public class Match
{
	//0 for horizontal, 1 for vertical
	private int orientation, size, x, y;
	private HashSet<GamePiece> members;
	private GamePiece origin;
	private GameBoard board;
	
	public Match(GamePiece origin, int size, int orientation, GameBoard board) {
		this.origin = origin;
		this.size = size;
		this.board = board;
		this.orientation = orientation;
		x = origin.getCoords().getX();
		y = origin.getCoords().getY();
		members = new HashSet<GamePiece>();
		//if it's a horizontal match
		if (orientation == 0) {
			//add [size] items to the match, including the origin piece
			for (int i = 0; i < size; ++i) {
				members.add(board.getPiece(x, y+i));
			}
		}
		else {
			for (int i = 0; i < size; ++i) {
				members.add(board.getPiece(x+i, y));
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public GamePiece getOrigin() {
		return origin;
	}
	
	public boolean containsPiece(GamePiece p) {
		return members.contains(p);
	}
	
	public int getOrientation() {
		return orientation;
	}
}
