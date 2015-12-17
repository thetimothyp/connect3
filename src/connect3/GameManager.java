package connect3;

import java.util.*;

public class GameManager
{
	public GameManager() {
		GameBoard game = new GameBoard(6,6);
		GameLogic logic = new GameLogic(game, 10);
		game.generateBoard(logic);
		logic.displayTurns();
		
		
		Scanner in = new Scanner(System.in);
		while (logic.getTotalPoints() < 500 && logic.getTurns() > 0) {
			
			// Get input for coordinates for items to swap
			
			System.out.print("Enter y1, x1: ");
			int y1 = in.nextInt();
			int x1 = in.nextInt();
			System.out.print("Enter y2, x2: ");
			int y2 = in.nextInt();
			int x2 = in.nextInt();
			game.swap(y1, x1, y2, x2);
			logic.takeTurn();
			
			System.out.println("-----------\n");
			game.displayBoard();
			logic.displayTotalPoints();
			logic.displayTurns();
			logic.displayMatches();
			
			// Keep cascading the board whenever a new match appears
			
			while (logic.hasMatches()) {
				logic.cascade();
				game.displayBoard();
				logic.displayTotalPoints();
				logic.displayTurns();
				logic.displayMatches();
			}
		}
		in.close();
		if (logic.getTotalPoints() > 500) {
			System.out.println("YOU WON WITH " + logic.getTotalPoints() + 
				" POINTS AND " + logic.getTurns() + " TURNS LEFT");
		}
		else {
			System.out.println("YOU LOST");
		}
	}
}
