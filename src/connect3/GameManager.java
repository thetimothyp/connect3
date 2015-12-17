package connect3;

import java.util.*;

public class GameManager
{
	public GameManager(int pointLimit, int turnLimit) {
		GameBoard board = new GameBoard(6,6);
		GameLogic game = new GameLogic(board, turnLimit);
		board.generateBoard(game);
		game.displayTurns();
		
		Scanner in = new Scanner(System.in);
		while (game.getTotalPoints() < pointLimit && game.getTurns() > 0) {
			
			game.takeTurn(in);
			System.out.println("-----------\n");
			
			// Keep cascading the board whenever a new match appears
			
			do {
				game.cascadeAndDisplay();
			} while (game.hasMatches());
			
		}
		
		// Check for victory or defeat once turnLimit or pointLimit is reached
		
		if (game.getTotalPoints() >= pointLimit) {
			System.out.println("YOU WON WITH " + game.getTotalPoints() + 
				" POINTS AND " + game.getTurns() + " TURNS LEFT");
		} else {
			System.out.println("YOU LOST");
		}
		
		in.close();
	}
}
