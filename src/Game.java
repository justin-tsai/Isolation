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
				calculate();
				if (!X.hasMoves()) {
					computerHasMoves = false;
					break;
				} else if (!O.hasMoves()){
					opponentHasMoves = false;
					break;
				}else {
					computerHasMoves = moveCOMPUTER();
				}
				/* If computer makes a move that blocks the opponent, game is over. */
				calculate();
				if (!O.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else if (!X.hasMoves()) {
					computerHasMoves = false;
					break;
				} else {
					opponentHasMoves = moveOPPONENT();
				}
				board.nextTurn();
			}
			/* Opponent goes first */
		} else if (first == 'O' || first == 'o') {
			/* If they both have moves, cycle between both. */
			while (computerHasMoves || opponentHasMoves) {
				calculate();
				if (!O.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else if (!X.hasMoves()) {
					computerHasMoves = false;
					break;
				} else {
					opponentHasMoves = moveOPPONENT();
				}
				/* If opponent makes a move that blocks the computer, game is over. */
				calculate();
				if (!X.hasMoves()) {
					computerHasMoves = false;
					break;
				} else if (!O.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else {
					computerHasMoves = moveCOMPUTER();
				}
				board.nextTurn();
			}
		} else {
			System.out.println("Error with determining who goes first");
		}
		/* If one team cannot make a move, the other team wins. */
		if (!computerHasMoves) {
			System.out.println("Opponent wins!");
		} else {
			System.out.println("Computer wins!");
		}
	}

	/* Opponent move function */
	public boolean moveOPPONENT() {
		/* Opponent has at least one move they can make */
		if (O.hasMoves()) {
			System.out.print("\nEnter opponent's move: ");
			String move = keyboard.nextLine();
			/* Valid input, successful move */
			if (board.move(O, move) == true) {
				System.out.println(board.toString());
				board.setTurn(0);
				return true;
				/* Invalid input and/or move */
			} else {
				System.out.println(board.toString());
				moveOPPONENT();
				return true;
			}
			/* Opponent can't make a move. */
		} else {
			return false;
		}
	}

	/* Test function to play against self */
	public boolean moveOPPONENT2() {
		if (X.hasMoves()) {
			System.out.print("\nEnter computer's move: ");
			String move = keyboard.nextLine();
			if (board.move(X, move) == true) {
				System.out.println(board.toString());
				return true;
			} else {
				System.out.println(board.toString());
				moveOPPONENT2();
				return true;
			}
		} else {
			return false;
		}
	}

	public void calculate() {
		X.calculate(board);
		O.calculate(board);
	}

	/* To do */
	public boolean moveCOMPUTER() {
		AlphaBeta.AlphaBeta(board, X, O, -999, 999, 0);
		System.out.println("Opponent moved: ");
		System.out.println(board.toString());
		board.setTurn(1);
		return true;
	}
}
