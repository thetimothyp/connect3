package connect3;

import java.util.*;

public class GameLogic
{
	private GameBoard board;
	private HashSet<GamePiece> verticalMatches;
	private HashSet<GamePiece> horizontalMatches;
	private HashSet<GamePiece> matches;
	private int points;

	public GameLogic(GameBoard board) {
		this.board = board;
		verticalMatches = new HashSet<GamePiece>();
		horizontalMatches = new HashSet<GamePiece>();
		matches = new HashSet<GamePiece>();
		points = 0;
	}
	
	public void displayMatches() {
		findMatches();
		System.out.print(matches.size() + " Matches: ");
		if (matches.size() > 0) {
			String toPrint = "";
			for (GamePiece p : matches) {
				toPrint += p.getCoords();
				if (isVerticalMatch(p)) {
					toPrint += "(V),";
				}
				else 
					toPrint += "(H),";
			}
			System.out.println(toPrint.substring(0,toPrint.length()-1));
		}
		else
			System.out.println();
	}
	
	public void cascadePiece(int y, int x) {
		for (int i = y; i > 0; --i) {
			board.getBoard()[i][x] = board.getBoard()[i-1][x];
			board.getBoard()[i][x].setCoords(i, x);
		}
		board.getBoard()[0][x] = generatePiece();
	}
	
	public void cascade() {
		int x, y;
		for (GamePiece p : matches) {
			x = p.getCoords().getX();
			y = p.getCoords().getY();
			if (isHorizontalMatch(p)) {
				cascadePiece(x,y);
				cascadePiece(x,y-1);
				cascadePiece(x,y+1);
			}
			if (isVerticalMatch(p)) {
				cascadePiece(x,y);
				cascadePiece(x-1,y);
				cascadePiece(x+1,y);
			}
		}
	}
	
	public boolean hasMatches() {
		findMatches();
		return matches.size() > 0;
	}
	
	public HashSet<GamePiece> getMatches() {
		return matches;
	}
	
	private GamePiece generatePiece() {
		Random generator = new Random();
		String options = "ABCDE";
		int n = generator.nextInt(5);
		return new GamePiece(String.valueOf(options.charAt(n)), 10);
	}
	
	private boolean isHorizontalMatch(GamePiece p) {
		return horizontalMatches.contains(p);
	}
	
	private boolean isVerticalMatch(GamePiece p) {
		return verticalMatches.contains(p);
	}
	
	private void findMatches() {
		verticalMatches = new HashSet<GamePiece>();
		horizontalMatches = new HashSet<GamePiece>();
		matches = new HashSet<GamePiece>();
		for (int i = 0; i < board.getHeight(); ++i) {
			for (int j = 0; j < board.getWidth(); ++j) {
				if (i > 0 && i < board.getHeight()-1) {
					if (checkForVerticalMatch(i,j)) {
						matches.add(board.getPiece(i,j));
						verticalMatches.add(board.getPiece(i, j));
					}
				}
				if (j > 0 && j < board.getWidth()-1) {
					if (checkForHorizontalMatch(i,j)) {
						matches.add(board.getPiece(i, j));
						horizontalMatches.add(board.getPiece(i, j));
					}
				}
			}
		}
	}
	
	private boolean checkForVerticalMatch(int i, int j) {
		if ((board.getPiece(i-1,j).equals(board.getPiece(i, j)) && 
				board.getPiece(i+1, j).equals(board.getPiece(i, j)))) {
			return true;
		}
		return false;
	}
	
	private boolean checkForHorizontalMatch(int i, int j) {
		if ((board.getPiece(i,j-1).equals(board.getPiece(i, j)) && 
				board.getPiece(i, j+1).equals(board.getPiece(i, j)))) {
			return true;
		}
		return false;
	}
}
