/**
 * Game represents the game that the players will be playing.
 * @author Justin
 *
 */

import java.util.Scanner;

public class Game {

	private Board board;
	private char first;
	private Player computer;
	private Player opponent;
	public static Scanner keyboard;
	private AlphaBeta ab;

	/**
	 * Creates a new game.
	 * @param first				The player that is going first.
	 * @param computerSymbol	The symbol that the AI will be playing as (Either X or O).
	 * @param timeLimit			The maximum amount of the time the AI is given to make a move.
	 */
	public Game(char first, char computerSymbol, int timeLimit) {
		this.first = first;
		board = new Board(first, computerSymbol);
		if (computerSymbol == 'X' || computerSymbol == 'x') {
			computer = new Player(0, 0, 'X');
			opponent = new Player(7, 7, 'O');
		} else {
			computer = new Player(7, 7, 'O');
			opponent = new Player(0, 0, 'X');
		}
		keyboard = new Scanner(System.in);
		ab = new AlphaBeta(timeLimit);
	}

	/**
	 * Starts the game. Loops between the computer and the opponent until the game has a winner.
	 */
	public void start() {
		System.out.println("Game start!\n\n" + board.toString());
		boolean opponentHasMoves = true;
		boolean computerHasMoves = true;
		/* Computer goes first */
		if (first == 'C' || first == 'c') {
			/* If they both have moves, cycle between both. */
			while (computerHasMoves || opponentHasMoves) {
				calculate();
				if (!computer.hasMoves()) {
					computerHasMoves = false;
					break;
				} else if (!opponent.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else {
					computerHasMoves = moveCOMPUTER();
				}
				/* If computer makes a move that blocks the opponent, game is over. */
				calculate();
				if (!opponent.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else if (!computer.hasMoves()) {
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
				if (!opponent.hasMoves()) {
					opponentHasMoves = false;
					break;
				} else if (!computer.hasMoves()) {
					computerHasMoves = false;
					break;
				} else {
					opponentHasMoves = moveOPPONENT();
				}
				/* If opponent makes a move that blocks the computer, game is over. */
				calculate();
				if (!computer.hasMoves()) {
					computerHasMoves = false;
					break;
				} else if (!opponent.hasMoves()) {
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

	/**
	 * Move function for the opponent.
	 * @return  Whether or not the opponent has a move that they can make.
	 */
	public boolean moveOPPONENT() {
		/* Opponent has at least one move they can make */
		if (opponent.hasMoves()) {
			System.out.print("\nEnter opponent's move: ");
			String move = keyboard.nextLine();
			/* Valid input, successful move */
			if (board.letterInputMove(opponent, move) == true) {
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

	/**
	 * Calculates the available moves for both the computer and the opponent.
	 */
	public void calculate() {
		computer.calculate(board);
		opponent.calculate(board);
	}

	/**
	 * Move function for the computer. Utilizes alpha beta search to decide where to move.
	 */
	public boolean moveCOMPUTER() {
		if (!board.letterInputMove(computer, ab.alphaBetaSearch(computer, opponent, board))) {
			/* Default move if alpha beta is unable to find a move. */
			board.numInputMove(computer, computer.getMoves().get(0));
		}
		System.out.println(board.toString());
		System.out.println("Computer moved to: " + board.getComputerMove()[computer.getNumMovesMade() - 1]);
		board.setTurn(1);
		return true;
	}
}
