import java.util.ArrayList;

public class AlphaBeta {

	private static int maxDepth;
	private static String BITCHINGMOVE;
	private static int maxTime;
	public AlphaBeta(int maxTime) {
		this.maxTime = maxTime;
	}

	public static int AlphaBeta(Board board, Player playerx, Player playero, int alpha, int beta, int depth) {
		playerx.calculate(board);
		if (depth > maxDepth || playerx.getNumMovesAvailable() == 0) {
			System.out.println(heur(board, playerx, depth));
			return heur(board, playerx, depth);
		}

		if (board.getTurn() == 0) {
			return max(board, playerx, playero, alpha, beta, depth);
		} else {
			return mini(board, playerx, playero, alpha, beta, depth);
		}




	}

	private static int mini(Board board, Player playerx, Player playero, int alpha, int beta, int depth) {
		int bestMove = -1;
		playerx.calculate(board);
		playero.calculate(board);
		ArrayList<String> moves = playero.getMoves();
		System.out.println("moves min can make: ");
		System.out.println(moves);
		for (int i = 0; i < playero.getNumMovesAvailable(); i++) {
			Board copy = board.getDeepCopy();
			Player playerxcopy = playerx.getDeepCopy();
			Player playerocopy = playero.getDeepCopy();
			System.out.println(moves.get(i));
			copy.move2(playerocopy, moves.get(i));
			System.out.println(copy.toString());

			int heur = AlphaBeta(copy, playerxcopy, playerocopy, alpha, beta, depth);

			if (heur < beta) {
				beta = heur;
				bestMove = i;

			}
			//alpha beta pruning
			if (alpha >= beta) {
				System.out.println("BREAK");
				break;
			}
		}

		if (bestMove != -1 && !moves.isEmpty()) {
			board.move2(playero, moves.get(bestMove));
			System.out.println(moves.get(bestMove));
		}

		return beta;
	}

	private static int max(Board board, Player playerx, Player playero, int alpha, int beta, int depth) {
		int bestMove = -1;
		playerx.calculate(board);
		playero.calculate(board);
		ArrayList<String> moves = playerx.getMoves();
		System.out.println("moves max can make: ");
		System.out.println(moves);
		for (int i = 0; i < playerx.getNumMovesAvailable(); i++) {
			Board copy = board.getDeepCopy();
			Player playerxcopy = playerx.getDeepCopy();
			Player playerocopy = playero.getDeepCopy();
			System.out.println("max" + moves.get(i));
			copy.move2(playerxcopy, moves.get(i));
			System.out.println(copy.toString());
			int heur = AlphaBeta(copy, playerxcopy, playerocopy, alpha, beta, depth);

			if (heur > alpha) {
				alpha = heur;
				bestMove = i;
				BITCHINGMOVE = moves.get(i);
				System.out.println(BITCHINGMOVE);
			}
			//alpha beta pruning
			if (alpha >= beta) {
				System.out.println("BREAK");
				break;
			}
		}

		if (bestMove != -1 && !moves.isEmpty()) {
			board.move2(playerx, moves.get(bestMove));
			System.out.println(moves.get(bestMove));
		}
		return beta;
	}

	private static int heur(Board board, Player player, int depth) {
		player.calculate(board);
		if (board.getTurn() == 0 && player.hasMoves() == false) {
			return -100 + depth;
		} else if (board.getTurn() == 0 && player.hasMoves() == true) {
			return player.getNumMovesAvailable();
		}

		if (board.getTurn() == 1 && player.hasMoves() == false) {
			return 100 - depth;
		} else if (board.getTurn() == 1 && player.hasMoves() == true) {
			return -player.getNumMovesAvailable();
		}

		return 0;
	}
	
}
