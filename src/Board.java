public class Board {

	private char[][] board;
	private String match;
	private String[] opponentMoves;
	private String[] computerMoves;
	private char first;

	public Board(char first) {
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
		System.out.println("first: " + first);
	}

	public char[][] getBoardState() {
		return board;
	}

	public boolean move(Player player, String input) {
		try {
			char symbol = player.getSymbol();
			String move = input.toUpperCase();
			int x = Character.getNumericValue(move.charAt(1)) - 1;
			int y = ((int) move.charAt(0)) - 65;
			if (x < 0 || y < 0 || x > 7 || y > 7) {
				System.out.println("Invalid input.");
				return false;
			}
			String coordinate = Integer.toString(x) + Integer.toString(y);
			if (player.canMove(coordinate)) {
				board[x][y] = symbol;
				board[player.getX()][player.getY()] = '#';
				player.setX(x);
				player.setY(y);
				if (symbol == 'X') {
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
		} catch (Exception e) {
			System.out.println("Invalid input.");
			return false;
		}
	}

	@Override
	public String toString() {
		String str = "  1 2 3 4 5 6 7 8     " + match;
		int letter = 65;
		int turn = 0;
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
}
