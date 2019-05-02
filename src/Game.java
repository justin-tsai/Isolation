import java.util.Scanner;

public class Game {

	private Board board;
	private char first;
	private int timeLimit;
	private Player X;
	private Player O;
	public static Scanner keyboard;

	public Game(char first, int timeLimit) {
		this.first = first;
		this.timeLimit = timeLimit;
		board = new Board(first);
		X = new Player(0, 0, 'X');
		O = new Player(7, 7, 'O');
		keyboard = new Scanner(System.in);
	}

	public void start() {
		System.out.println("Game start!\n\n" + board.toString());
		boolean opponentHasMoves = true;
		boolean computerHasMoves = true;
		/* Computer goes first */
		if (first == 'C' || first == 'c') {
			/* If they both have moves, cycle between both. */
			while (computerHasMoves || opponentHasMoves) {
				computerHasMoves = moveOPPONENT2();
				/* If computer makes a move that blocks the opponent, game is over. */
				if(computerHasMoves) {
					break;
				}
				opponentHasMoves = moveOPPONENT();
			}
		/* Opponent goes first */
		} else if (first == 'O' || first == 'o') {
			/* If they both have moves, cycle between both. */
			while (computerHasMoves || opponentHasMoves) {
				opponentHasMoves = moveOPPONENT();
				/* If opponent makes a move that blocks the computer, game is over. */
				if(opponentHasMoves) {
					break;
				}
				computerHasMoves = moveOPPONENT2();
			}
		} else {
			System.out.println("Error with determining who goes first");
		}
		/* If one team cannot make a move, the other team wins. */
		if(!computerHasMoves) {
			System.out.println("Opponent wins!");
		}else {
			System.out.println("Computer wins!");
		}
	}

	
	public boolean moveOPPONENT() {
		O.calculate(board);
		if (O.hasMoves()) {
			System.out.print("\nEnter opponent's move: ");
			String move = keyboard.nextLine();
			if (board.move(O, move) == true) {
				System.out.println(board.toString());
				return false;
			} else {
				System.out.println(board.toString());
				moveOPPONENT();
				return false;
			}
		} else {
			return true;
		}
	}
	
	/*Test function to play against self */
	public boolean moveOPPONENT2() {
		X.calculate(board);
		if (X.hasMoves()) {
			System.out.print("\nEnter computer's move: ");
			String move = keyboard.nextLine();
			if (board.move(X, move) == true) {
				System.out.println(board.toString());
				return false;
			} else {
				System.out.println(board.toString());
				moveOPPONENT2();
				return false;
			}
		} else {
			return true;
		}
	}

	/* To do */
	public boolean moveCOMPUTER() {
		X.calculate(board);
		System.out.println(board.toString());
		return false;
	}
}
