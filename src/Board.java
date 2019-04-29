
public class Board {

	private char[][] board;
	private String[] movesAI;
	private String[] movesPLAYER;
	private int N = 8;
	
	public Board() {
		board = new char[N][N];
		movesAI = new String[N*N];
		movesPLAYER = new String[N*N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				board[i][j] = '-';
			}
		}
		board[0][N-1] = 'X';
		board[N-1][0] = 'O';
		board[0][0] = '#';
	}  
	
	public void move(boolean player, String move) {
		char symbol = (player == true) ? 'O' : 'X';
		int x = ((int) move.charAt(0)) - 65;
		int y = Character.getNumericValue(move.charAt(1)) - 1;
		System.out.println("x: " + x + ", y: " + y);
		board[y][x] = symbol;
		
		
	}
	
	@Override
	public String toString() {
		String str = "  1 2 3 4 5 6 7 8     Computer vs. Opponent\n"; 	
		int letter = 65;
		for (int j = 0; j < N; j++, letter++) {
			str += (char) letter + " ";
			for (int i = 0; i < N; i++) {
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
			str += "\n";
		}
		return str;
	}
}
