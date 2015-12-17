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
	private int points, turnsLeft;

	public GameLogic(GameBoard board, int turnLimit) {
		this.board = board;
		matches = new HashSet<Match>();
		points = 0;
		turnsLeft = turnLimit;
	}
	
	//Updates GameBoard and displays info for the turn
	
	public void cascadeAndDisplay() {
		cascade();
		board.displayBoard();
		displayTotalPoints();
		displayTurns();
		displayMatches();
	}
	
	// Displays all Matches with the following syntax: (y,x)([size][orientation])
	// i.e (3,0)(4H)
	
	private void displayMatches() {
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
	
	// Iterates through all current Matches and uses the appropriate
	// cascade method for vertical and horizontal Matches
	
	private void cascade() {
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
				cascadeVertical(x,y, m.getSize());
			}
			points += m.getPoints();
		}
	}
	
	public boolean hasMatches() {
		findMatches();
		return matches.size() > 0;
	}
	
	private void displayTotalPoints() {
		System.out.println("Points: " + points);
	}
	
	public int getTotalPoints() {
		return points;
	}
	
	public void displayTurns() {
		System.out.println("Turns left: " + turnsLeft);
	}
	
	public void takeTurn(Scanner in) {
		System.out.print("Enter y1, x1: ");
		int y1 = in.nextInt();
		int x1 = in.nextInt();
		System.out.print("Enter y2, x2: ");
		int y2 = in.nextInt();
		int x2 = in.nextInt();
		board.swap(y1, x1, y2, x2);
		--turnsLeft;
	}
	
	public int getTurns() {
		return turnsLeft;
	}
	
	// Remove the piece at (y, x) and cascade all the pieces above it down by 1 space
	private void cascadePiece(int y, int x) {
		for (int i = y; i > 0; --i) {
			board.getBoard()[i][x] = board.getBoard()[i-1][x];
			board.getBoard()[i][x].setCoords(i, x);
		}
		board.getBoard()[0][x] = generatePiece();
		board.getBoard()[0][x].setCoords(0, x);
	}
	
	private void cascadeVertical(int y, int x, int size) {
		// Replace each tile in the match with the tile [size] spaces above it
		// i.e (4,5) in a match of size 4 becomes the tile at (0,5)
		for (int i = size - y; i < size; ++i) {
			board.getBoard()[y+i][x] = board.getBoard()[y+i-size][x];
			board.getBoard()[y+i][x].setCoords(y+i, x);
		}

		// Fill in top [size] spaces with new tiles
		for (int i = 0; i < size; ++i) {
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
	
	// Finds all the matches in the current board and adds them to the set of Matches
	private void findMatches() {
		matches = new HashSet<Match>();
		for (int i = 0; i < board.getHeight(); ++i) {
			for (int j = 0; j < board.getWidth(); ++j) {
				
				// Matches are at least 3, so any i within 2 spaces of the height needn't be
				// checked for a vertical match
				// Horizontal matches still need to be checked for all i
				
				if (i < board.getHeight()-2) {
					
					// If a piece is already part of a Match, we don't check if it's an origin for
					// a Match
					
					if (isInMatch(board.getPiece(i, j))) {
						continue;
					}
					else if (checkForVerticalMatch(i,j) > 0) {
						matches.add(new Match(board.getPiece(i,j), checkForVerticalMatch(i,j), 1, board));
					}
				}
				
				// Any j within 2 spaces of the width needn't be checked for
				// a horizontal match
				// Vertical matches still need to be checked for all j
				
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
	
	// Checks if the piece at (i,j) is the origin for a vertical match
	// Returns the size of the match if there is one, otherwise returns 0
	private int checkForVerticalMatch(int i, int j) {
		int size = 0;
		if ((board.getPiece(i+1,j).equals(board.getPiece(i, j)) && 
				board.getPiece(i+2, j).equals(board.getPiece(i, j)))) {
			size = 3;
			
			// If there are more spaces on the board to check, then check them to see if they
			// should be included in the match
			
			if (i < board.getHeight()-3 && board.getPiece(i+3, j).equals(board.getPiece(i, j))) {
				size = 4;
				if (i < board.getHeight()-4 && board.getPiece(i+4, j).equals(board.getPiece(i, j))) {
					size = 5;
				}
			}
		}
		return size;
	}
	
	// Checks if the piece at (i,j) is the origin for a horizontal match
	// Returns the size of the match if there is one, otherwise returns 0
	private int checkForHorizontalMatch(int i, int j) {
		int size = 0;
		if ((board.getPiece(i,j+1).equals(board.getPiece(i, j)) && 
				board.getPiece(i, j+2).equals(board.getPiece(i, j)))) {
			size = 3;
			
			// If there are more spaces on the board to check, then check them to see if they
			// should be included in the match
			
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
