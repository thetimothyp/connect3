package connect3;

import java.util.*;

public class GameLogic
{
	private GameBoard board;
	private HashSet<GamePiece> matches;
	
	public GameLogic(GameBoard board) {
		this.board = board;
		matches = new HashSet<GamePiece>();
	}
	
	public void displayMatches() {
		getMatches();
		System.out.print(matches.size() + " Matches: ");
		if (matches.size() > 0) {
			String toPrint = "";
			for (GamePiece p : matches) {
				toPrint += p.getCoords() + ",";
			}
			System.out.println(toPrint.substring(0,toPrint.length()-1));
		}
		else
			System.out.println();
	}
	
	public boolean hasMatches() {
		getMatches();
		return matches.size() > 0;
	}
	
	private void getMatches() {
		matches = new HashSet<GamePiece>();
		for (int i = 0; i < board.getHeight(); ++i) {
			for (int j = 0; j < board.getWidth(); ++j) {
				if (i > 0 && i < board.getHeight()-1) {
					if (checkForVerticalMatch(i,j)) {
						matches.add(board.getPiece(i,j));
					}
				}
				if (j > 0 && j < board.getWidth()-1) {
					if (checkForHorizontalMatch(i,j)) {
						matches.add(board.getPiece(i, j));
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
