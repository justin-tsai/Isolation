
public class AlphaBeta2 {

	private AlphaBeta2() {
	}
	
	public int AlphaBeta(Board board, Player player1, Player player2, int alpha, int beta) {
		if (board.getTurn() == 0) {
			if (player1.hasMoves() == false) {
				return score;
			} else {
				
			}
		} else {
			if (player2.hasMoves() == false) {
				return score;
			} else {
				
			} 
		}
		return 6;
	}
	
	private static int mini() {
		
	}
	
	private static int max() {
		
	}
	
	private static int heur(Board board, Player player1, Player player2) {
		if (board.getTurn() == 0) {
			player1.calculate(board);
			return player1.getNumMovesAvailable();
		} else {
			player2.calculate(board);
			return player2.getNumMovesAvailable();
		}
	}
}
