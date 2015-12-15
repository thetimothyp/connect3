package connect3;

import java.util.Random;

public class GameBoard
{
	private int width;
	private int height;
	private GamePiece[][] board;
	
	public GameBoard() {
		width = 0;
		height = 0;
	}
	
	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
		board = new GamePiece[height][width];
	}
	
	public void generateBoard(GameLogic logic) {
		populateBoard(logic);
		displayBoard();
	}
	
	public void swap(int x1, int y1, int x2, int y2) {
		GamePiece temp = new GamePiece(board[x1][y1]);
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;
	}
	
	public GamePiece[][] getBoard() {
		return board;
	}
	
	public GamePiece getPiece(int i, int j) {
		return board[i][j];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void displayBoard() {
		System.out.print("    ");
		for (int i = 0; i < width; ++i) {
			System.out.print(i + "   ");
		}
		System.out.println("\n");
		for (int i = 0; i < height; ++i) {
			System.out.print(i + "   ");
			for (int j = 0; j < width; ++j) {
				System.out.print(board[i][j] + "   ");
			}
			System.out.println("\n");
		}
	}
	
	private void populateBoard(GameLogic logic) {
		do {
			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					board[i][j] = generatePiece();
					board[i][j].setCoords(i, j);
				}
			}
		} while (logic.hasMatches());
	}
	
	private GamePiece generatePiece() {
		Random generator = new Random();
		String options = "ABCDE";
		int n = generator.nextInt(5);
		return new GamePiece(String.valueOf(options.charAt(n)), 10);
	}
}
