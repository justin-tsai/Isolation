import java.util.Scanner;

public class Game {

	private Board board;
	private char first;
	private int timeLimit;
	private Player p1;
	private Player p2;
	public static Scanner keyboard;

	public Game(char first, int timeLimit) {
		this.first = first;
		this.timeLimit = timeLimit;
		board = new Board(first);
		p1 = new Player(0, 0, 'X');
		p2 = new Player(7, 7, 'O');
		keyboard = new Scanner(System.in);

		// System.out.println("first: " + first);
		// System.out.println("time limit per move: " + timeLimit + " seconds");
		start();
	}

	public void start() {
		System.out.println("Game start!\n\n" + board.toString());
		boolean playing = true;
		if (first == 'C' || first == 'c') {
			while (playing) {
				playing = moveCOMPUTER();
				playing = moveOPPONENT();
			}
		} else if (first == 'O' || first == 'o') {
			while (playing) {
				playing = moveOPPONENT();
				playing = moveCOMPUTER();
			}
		} else {
			System.out.println("Error with determining who goes first");
		}
	}

	public boolean moveOPPONENT() {
		System.out.print("\nEnter opponent's move: ");
		String move = keyboard.nextLine();
		if (board.move(p2, move) == true) {
			System.out.println(board.toString());
			return false;
		} else {
			System.out.println("Move is not valid.");
			moveOPPONENT();
			return false;
		}
	}

	public boolean moveCOMPUTER() {
		board.move(p1, "F5");
		System.out.println(board.toString());
		return false;
	}
}
