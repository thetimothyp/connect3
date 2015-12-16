package connect3;

import java.util.*;

/*
 * Implements the logic for the game
 * 
 * Player may swap one item with one other item at a time
 * 
 * A match is defined as 3 of the same item in a row (horizontally or vertically)
 * 
 * A match is represented by the coordinates of its leftmost member if horizontal and
 * its topmost member if vertical
 * 
 */

public class GameLogic
{
	private GameBoard board;
	private HashSet<Match> matches;
	private int points;

	public GameLogic(GameBoard board) {
		this.board = board;
		matches = new HashSet<Match>();
		points = 0;
	}
	
	public void displayMatches() {
		findMatches();
		System.out.print(matches.size() + " Matches: ");
		if (matches.size() > 0) {
			String toPrint = "";
			for (Match m : matches) {
				toPrint += m.getOrigin().getCoords();
				if (isVerticalMatch(m)) {
					toPrint += "(" + m.getSize() + "V),";
				}
				else 
					toPrint += "(" + m.getSize() + "H),";
			}
			System.out.println(toPrint.substring(0,toPrint.length()-1));
		}
		else
			System.out.println();
	}
	
	public void cascade() {
		int x, y;
		for (Match m : matches) {
			x = m.getOrigin().getCoords().getX();
			y = m.getOrigin().getCoords().getY();
			if (isHorizontalMatch(m)) {
				for (int i = 0; i < m.getSize(); ++i) {
					cascadePiece(x,y+i);
				}
			}
			if (isVerticalMatch(m)) {
				cascadeVertical(x,y);
			}
		}
	}
	
	public boolean hasMatches() {
		findMatches();
		return matches.size() > 0;
	}
	
	public HashSet<Match> getMatches() {
		return matches;
	}
	
	private void cascadePiece(int y, int x) {
		for (int i = y; i > 0; --i) {
			board.getBoard()[i][x] = board.getBoard()[i-1][x];
			board.getBoard()[i][x].setCoords(i, x);
		}
		board.getBoard()[0][x] = generatePiece();
		board.getBoard()[0][x].setCoords(0, x);
	}
	
	private void cascadeVertical(int y, int x) {
		switch (y) {
		case 3:
			board.getBoard()[y+2][x] = board.getBoard()[y-1][x];
			board.getBoard()[y+2][x].setCoords(y+2,x);
			board.getBoard()[y+1][x] = board.getBoard()[y-2][x];
			board.getBoard()[y+1][x].setCoords(y+1,x);
			board.getBoard()[y][x] = board.getBoard()[y-3][x];
			board.getBoard()[y][x].setCoords(y,x);
			break;
		case 2:
			board.getBoard()[y+2][x] = board.getBoard()[y-1][x];
			board.getBoard()[y+2][x].setCoords(y+2,x);
			board.getBoard()[y+1][x] = board.getBoard()[y-2][x];
			board.getBoard()[y+1][x].setCoords(y+1,x);
			break;
		case 1:
			board.getBoard()[y+2][x] = board.getBoard()[y-1][x];
			board.getBoard()[y+2][x].setCoords(y+2,x);
			break;
		}
		for (int i = 0; i < 3; ++i) {
			board.getBoard()[i][x] = generatePiece();
			board.getBoard()[i][x].setCoords(i, x);
		}
	}
	
	private GamePiece generatePiece() {
		Random generator = new Random();
		String options = "ABCDE";
		int n = generator.nextInt(5);
		return new GamePiece(String.valueOf(options.charAt(n)), 10);
	}
	
	private boolean isHorizontalMatch(Match m) {
		return m.getOrientation() == 0;
	}
	
	private boolean isVerticalMatch(Match m) {
		return m.getOrientation() == 1;
	}
	
	private void findMatches() {
		matches = new HashSet<Match>();
		for (int i = 0; i < board.getHeight(); ++i) {
			for (int j = 0; j < board.getWidth(); ++j) {
				if (i < board.getHeight()-2) {
					if (checkForVerticalMatch(i,j) > 0) {
						matches.add(new Match(board.getPiece(i,j), checkForVerticalMatch(i,j), 1, board));
					}
				}
				if (j < board.getWidth()-2) {
					if (isInMatch(board.getPiece(i, j))) {
						continue;
					}
					else if (checkForHorizontalMatch(i,j) > 0) {
						matches.add(new Match(board.getPiece(i, j), checkForHorizontalMatch(i,j), 0, board));
					}
				}
			}
		}
	}
	
	//Checks to see whether GamePiece p is already part of a Match
	private boolean isInMatch(GamePiece p) {
		for (Match m : matches) {
			if (m.containsPiece(p)) {
				return true;
			}
		}
		return false;
	}
	
	private int checkForVerticalMatch(int i, int j) {
		int size = 0;
		if ((board.getPiece(i+1,j).equals(board.getPiece(i, j)) && 
				board.getPiece(i+2, j).equals(board.getPiece(i, j)))) {
			size = 3;
		}
		return size;
	}
	
	private int checkForHorizontalMatch(int i, int j) {
		int size = 0;
		if ((board.getPiece(i,j+1).equals(board.getPiece(i, j)) && 
				board.getPiece(i, j+2).equals(board.getPiece(i, j)))) {
			size = 3;
			if (j < board.getWidth()-3 && board.getPiece(i, j+3).equals(board.getPiece(i, j))) {
				size = 4;
				if (j < board.getWidth()-4 && board.getPiece(i, j+4).equals(board.getPiece(i, j))) {
					size = 5;
				}
			}
		}
		return size;
	}
}
