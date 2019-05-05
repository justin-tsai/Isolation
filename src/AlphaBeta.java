import java.util.Iterator;

public class AlphaBeta {

	private int timeLimit;
	private int depthLimit = 5;
	private int MAX = 1000;
	private int MIN = -1000;
	private Board finalB;
	private int turns;
	public AlphaBeta(int timeLimit) {
		this.timeLimit = timeLimit;
		turns = 0;
	}

	public String alphaBetaSearch(Player X, Player O, Board board) {
		turns++;
		int best = max(X, O, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		//default:
		System.out.println("FINAL VALUE: " + best);
		for (String move : X.getMoves()) {
			System.out.println("movelist: " + move );
			if (X.getValue(board, move) == best) {
				System.out.println("final move: " + move);
				System.out.println(finalB.getComputerMove()[turns]);
				return move;
			}
		}
		System.out.println(finalB.getComputerMove()[turns]);
		System.out.println("default move: " + X.getMoves().get(0));
		return X.getMoves().get(0);
	}

	public int max(Player X, Player O, Board board, int alpha, int beta, int depth) {
		X.calculate(board);
		O.calculate(board);
		if (depth > depthLimit) {
			return utility(board, X);
		}
		int value = Integer.MIN_VALUE;
		Iterator<String> itr = X.getMoves().iterator();
		while(itr.hasNext()) {
			String move = itr.next();
			Player xTemp = X.getDeepCopy();
			Player oTemp = O.getDeepCopy();
			Board temp = board.getDeepCopy();
			temp.move2(xTemp, move);
			int min = min(xTemp, oTemp, temp, alpha, beta, depth + 1);
			if (min > value) {
				value = min;
			}
			if (value >= beta) {
				//System.out.println("v: " + value);
				return value;
			}

			if (value > alpha) {
				alpha = value;
			}
		}
		return value;

	}

	public int min(Player X, Player O, Board board, int alpha, int beta, int depth) {
		X.calculate(board);
		O.calculate(board);
		if (depth > depthLimit) {
			return utility(board, O);
		}
		int value = Integer.MAX_VALUE;
		Iterator<String> itr = O.getMoves().iterator();
		while(itr.hasNext()) {
			String move = itr.next();
			Board temp = board.getDeepCopy();
			Player oTemp = O.getDeepCopy();
			Player xTemp = X.getDeepCopy();
			temp.move2(oTemp, move);
			int max = max(xTemp, oTemp, temp, alpha, beta, depth + 1);
			if (max < value) {
				value = max;
			}
			if (value <= alpha) {
				//System.out.println("v: " + value);
				return value;
			}

			if (value < beta) {
				beta = value;
			}
		}
		return value;

	}

	public int utility(Board board, Player player) {
		int value;
		player.calculate(board);
		if (!player.hasMoves()) {
			value = (player.getSymbol() == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		} else {
			value = (player.getSymbol() == 'O') ? -player.getNumMovesAvailable() : player.getNumMovesAvailable();
			finalB = board;
		}
		//System.out.println("Utility Value: " + value);
		return value;
	}

}