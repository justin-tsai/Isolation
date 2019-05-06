/**
 * Board represents the 8x8 board the game will be played on.
 * @author Justin
 *
 */
public class Board {

	private char[][] board;
	private String match;
	private String[] opponentMoves;
	private String[] computerMoves;
	private char first;
	public int turns;
	private int turn;	
	private char computerSymbol;
		
	/**
	 * Constructor for the board object that the game will be played on.
	 * @param first				The symbol that represents who will go first (Either opponent or computer).
	 * @param computerSymbol	The symbol that the computer will represent (Either X or O).
	 */
	public Board(char first, char computerSymbol) {
		board = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = '-';
			}
		}
		board[0][0] = 'X';
		board[7][7] = 'O';
		match = (first == 'C' || first == 'c') ? "Computer vs. Opponent\n" : "Opponent vs. Computer\n";
		opponentMoves = new String[32];
		computerMoves = new String[32];
		for (int i = 0; i < 32; i++) {
			opponentMoves[i] = "";
			computerMoves[i] = "";
		}
		this.first = first;
		turns = 0;
		this.computerSymbol = computerSymbol;
	}

	/**
	 * Empty constructor for board, for purpose of deep copy function.
	 */
	public Board() {

	}

	/**
	 * Getter function for the board state.
	 * @return	The character array containing all the moves on the board/
	 */
	public char[][] getBoardState() {
		return board;
	}

	/**
	 * Setter function for the turn number.
	 * @param i	The turn number the board is being set to.
	 */
	public void setTurn(int i) {
		turn = i;
	}

	/**
	 * Getter function for the turn number.
	 * @return The turn number.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Increments the turn count.
	 */
	public void nextTurn() {
		turns++;
	}

	/**
	 * Getter function for the move history of the computer.
	 * @return The history of moves that the computer has made.
	 */
	public String[] getComputerMove() {
		return computerMoves;		
	}
	
	/**
	 * Getter function for the move history of the opponent.
	 * @return The history of moves that the opponent has made.
	 */
	public String[] getOpponentMove() {
		return opponentMoves;
	}
	
	/**
	 * Function that allows a player to make a move on the board using board values.
	 * Will check if the player is allowed to move there first before making it.
	 * @param player	The player that is moving on the board.
	 * @param input		The move that the player is making (A1, etc).
	 * @return			Whether or not the move was successful.
	 */
	public boolean letterInputMove(Player player, String input) {
		try {
			char symbol = player.getSymbol();
			/* Converting letter input to coordinates to indexes for board array */
			int[] coordinates = letterToCoordinate(input);
			int x = coordinates[0];
			int y = coordinates[1];
			/* Checking if the converted indexes are within the board boundaries (Good input or bad input)*/
			if (x < 0 || y < 0 || x > 7 || y > 7) {
				System.out.println("Invalid input.");
				return false;
			}
			String coordinate = Integer.toString(x) + Integer.toString(y);
			/* Checking if the player is allowed to move to the coordinate*/
			if (player.canMove(coordinate)) {
				/* Moves player to the coordinate, update values for board and player*/
				board[x][y] = symbol;
				board[player.getX()][player.getY()] = '#';
				player.setPosition(x, y);
				if (symbol == computerSymbol) {
					computerMoves[player.getNumMovesMade()] = input;
				} else {
					opponentMoves[player.getNumMovesMade()] = input;
				}
				player.moveMade();
				return true;
			/* Player isn't allowed to move to the coordinate inputed. */
			} else {
				System.out.println("Cannot move there!");
				return false;
			}
		/* User unexpected input */
		} catch (Exception e) {
			System.out.println("Invalid input.");
			return false;
		}
	}

	/**
	 * Function that allows a player to move to make a move on the board using coordinate values.
	 * @param player	The player that is making the move.
	 * @param numInput	The move that the player is making (00, 01, etc).
	 * @return			Whether or not the move was successful.
	 */
	public boolean numInputMove(Player player, String numInput) {
		player.calculate(this);
		char symbol = player.getSymbol();
		int[] coordinates = numberToCoordinate(numInput);
		int x = coordinates[0];
		int y = coordinates[1];
		String move = coordinateToLetter(x, y);
		if (player.canMove(numInput)) {
			board[x][y] = symbol;
			board[player.getX()][player.getY()] = '#';
			player.setPosition(x, y);
			if (symbol == computerSymbol) {
				computerMoves[player.getNumMovesMade()] = move;
			} else {
				opponentMoves[player.getNumMovesMade()] = move;
			}
			player.moveMade();
			return true;
		} else {
			System.out.println("Cannot move there!");
			return false;
		}
	}
	
	/**
	 * Creates a deep copy of the board with same values.
	 * @return	Copy of the board with duplicated values.
	 */
	public Board getDeepCopy() {
		Board copy = new Board();
		copy.board = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy.board[i][j] = board[i][j];
			}
		}
		copy.match = this.match;
		copy.opponentMoves = new String[32];
		copy.computerMoves = new String[32];
		for (int i = 0; i < 32; i++) {
			copy.opponentMoves[i] = opponentMoves[i];
			copy.computerMoves[i] = computerMoves[i];
		}
		copy.first = this.first;
		copy.turns = this.turns;
		copy.turn = this.turn;
		copy.computerSymbol = this.computerSymbol;
		return copy;
	}

	@Override
	public String toString() {
		String str = "  1 2 3 4 5 6 7 8     " + match;
		int letter = 65;
		int turn = 0;
		if (turns > 7) {
			turn = turns - 7;
		}
		for (int j = 0; j < 8; j++, letter++, turn++) {
			str += (char) letter + " ";
			for (int i = 0; i < 8; i++) {
				if (board[i][j] == '#') {
					str += "# ";
				} else if (board[i][j] == 'X') {
					str += "X ";
				} else if (board[i][j] == 'O') {
					str += "O ";
				} else {
					str += "- ";
				}
			}

			str += "     " + (turn + 1) + ": ";
			str += (first == 'C' || first == 'c') ? computerMoves[turn] + "   " + opponentMoves[turn]
					: opponentMoves[turn] + "   " + computerMoves[turn];
			str += "\n";
		}
		return str;
	}
	
	/**
	 * Converts letter input to coordinates.
	 * @param letterInput	Letter input (A2, etc).
	 * @return				Coordinates for the input (A1 -> 00).
	 */
	public int[] letterToCoordinate(String letterInput) {
		String move = letterInput.toUpperCase();
		int[] coordinates = new int[2];
		coordinates[0] = Character.getNumericValue(move.charAt(1)) - 1;
		coordinates[1] = ((int) move.charAt(0)) - 65;
		return coordinates;
	}
	
	/**
	 * Converts coordinates to letter values.
	 * @param x		X-coordinate for the board array.
	 * @param y		Y-coordinate for the board array.
	 * @return		Letter move for the coordinates(00 -> A1).
	 */
	public String coordinateToLetter(int x, int y) {
		char letter = numToLetter(y);
		 return letter + Integer.toString(x+1);
	}
	
	/**
	 * Converts string coordinates to int coordinates.
	 * @param numInput	Coordinates as a string.
	 * @return			Coordinates as a int.
	 */
	public int[] numberToCoordinate(String numInput) {
		int[] coordinates = new int[2];
		coordinates[0] = Character.getNumericValue(numInput.charAt(0));
		coordinates[1] = Character.getNumericValue(numInput.charAt(1));
		return coordinates;
	}
	
	/**
	 * Converts an integer to its corresponding letter value.
	 * @param num	The integer being converted.
	 * @return		The ASCII letter value of the integer (65 -> A)
	 */
	public char numToLetter(int num) {
		return (char) (num + 65);
	}
	
}
