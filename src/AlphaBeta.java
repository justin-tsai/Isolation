import java.util.Iterator;

public class AlphaBeta {

	private int timeLimit;
	private long startTime;
	private long currTime;
	private int depthLimit = 1;
	private int MAX = 1000;
	private int MIN = -1000;
	private Board finalB;
	private int turns;

	public AlphaBeta(int timeLimit) {
		this.timeLimit = timeLimit;
		turns = 0;
	}

	public String alphaBetaSearch(Player computer, Player opponent, Board board) {
		startTime = System.nanoTime();
		Node best = new Node();
		// Created this conditional to switch X and O players
		best = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		while (currTime < (timeLimit * 1000)) {
			depthLimit++;
			best = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		}

		/**
		 * Node best = max(X, O, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0); while
		 * (currTime < (timeLimit * 1000)) { depthLimit++; best = max(X, O, board,
		 * Integer.MIN_VALUE, Integer.MAX_VALUE, 0); }
		 */

		// default:

		Board bestBoard = best.getBoard();
		String bestMove = bestBoard.getComputerMove()[turns];
		turns++;
		return bestMove;
	}

	public Node max(Player computer, Player opponent, Board board, int alpha, int beta, int depth) {
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (depth > depthLimit || currTime > (timeLimit * 1000) || computer.hasMoves() == false) {
			return utility(board, computer, depth);
		}
		if(computer.getNumMovesAvailable() < 3) {
			return utility(board, computer, depth);
		}
		int value = Integer.MIN_VALUE;
		Node node = new Node(board, value);
		Iterator<String> itr = computer.getMoves().iterator();
		while (itr.hasNext()) {
			String move = itr.next();
			Player xTemp = computer.getDeepCopy();
			Player oTemp = opponent.getDeepCopy();
			Board temp = board.getDeepCopy();
			xTemp.calculate(temp);
			oTemp.calculate(temp);
			temp.move2(xTemp, move);
			xTemp.calculate(temp);
			oTemp.calculate(temp);
			Node min = min(xTemp, oTemp, temp, alpha, beta, depth + 1);
			if (min.getN() > node.getN()) {
				node = min;
			}
			if (node.getN() >= beta) {
				// System.out.println("v: " + value);
				return node;
			}

			if (node.getN() > alpha) {
				alpha = node.getN();
			}
		}
		return node;

	}

	public Node min(Player computer, Player opponent, Board board, int alpha, int beta, int depth) {
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (depth > depthLimit || currTime > (timeLimit * 1000) || opponent.hasMoves() == false) {
			return utility(board, opponent, depth);
		}
		if(opponent.getNumMovesAvailable() < 3) {
			return utility(board, opponent, depth);
		}
		int value = Integer.MAX_VALUE;
		Node node = new Node(board, value);
		Iterator<String> itr = opponent.getMoves().iterator();
		while (itr.hasNext()) {
			String move = itr.next();
			Board temp = board.getDeepCopy();
			Player oTemp = opponent.getDeepCopy();
			Player xTemp = computer.getDeepCopy();
			xTemp.calculate(temp);
			oTemp.calculate(temp);
			temp.move2(oTemp, move);
			xTemp.calculate(temp);
			oTemp.calculate(temp);
			Node max = max(xTemp, oTemp, temp, alpha, beta, depth + 1);
			if (max.getN() < node.getN()) {
				node = max;
			}
			if (node.getN() <= alpha) {
				// System.out.println("v: " + value);
				return node;
			}

			if (node.getN() < beta) {
				beta = node.getN();
			}
		}
		return node;

	}

	public Node utility(Board board, Player player, int depth) {
		int value;
		player.calculate(board);
		if (!player.hasMoves()) {
			value = (player.getSymbol() == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		} else {
			value = (player.getSymbol() == 'O') ? -player.getNumMovesAvailable() + depth
					: player.getNumMovesAvailable() - depth;
			finalB = board;
		}
		// System.out.println("Utility Value: " + value);
		Node node = new Node(board, value);
		return node;
	}
}