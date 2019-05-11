import java.util.Iterator;

public class AlphaBeta {

	private int timeLimit;
	private int depthLimit = 1;
	private int turns;
	private long startTime = System.nanoTime();
	private long currTime = System.nanoTime();

	public AlphaBeta(int timeLimit) {
		this.timeLimit = timeLimit;
		turns = 0;
	}

	public String alphaBetaSearch(Player computer, Player opponent, Board board) {
		startTime = System.nanoTime();
		currTime = 0;
		Node best = new Node();
		Node currBest = new Node();
		startTime = System.nanoTime();
		depthLimit = 1;

		while (currTime < (timeLimit * 1000)) {
			currBest = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
			if (currBest.successfulRun()) {
				best = currBest;
			}
			depthLimit = depthLimit + 2;
		}
		Board bestBoard = best.getBoard();
		String bestMove = bestBoard.getComputerMove()[turns];
		System.out.println(bestBoard.toString());
		turns++;
		return bestMove;
	}

	public Node max(Player computer, Player opponent, Board board, double alpha, double beta, int depth) {
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (currTime > (timeLimit * 1000)) {
			return utility(board, computer, opponent, depth, true);
		} else if (depth > depthLimit || computer.hasMoves() == false) {
			return utility(board, computer, opponent, depth, false);
		}
		opponent.calculate(board);
		computer.calculate(board);
		int value = Integer.MIN_VALUE;
		Node node = new Node(board, value, true);
		Iterator<String> itr = computer.getMoves().iterator();
		while (itr.hasNext()) {
			String move = itr.next();
			Player xTemp = computer.getDeepCopy();
			Board temp = board.getDeepCopy();
			xTemp.calculate(temp);
			temp.move2(xTemp, move);
			xTemp.calculate(temp);
			Node min = min(xTemp, opponent, temp, alpha, beta, depth + 1);
			if (min.getValue() > node.getValue()) {
				node = min;
			}
			if (node.getValue() >= beta) {
				// System.out.println("v: " + value);
				if (currTime > (timeLimit * 1000)) {
					node.setSuccess(false);
				}
				return node;
			}

			if (node.getValue() > alpha) {
				alpha = node.getValue();
			}
		}
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (currTime > (timeLimit * 1000)) {
			node.setSuccess(false);
		}
		return node;

	}

	public Node min(Player computer, Player opponent, Board board, double alpha, double beta, int depth) {
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (currTime > (timeLimit * 1000)) {
			return utility(board, opponent, computer, depth, true);
		} else if (depth > depthLimit || opponent.hasMoves() == false) {
			return utility(board, opponent, computer, depth, false);
		}
		opponent.calculate(board);
		computer.calculate(board);
		int value = Integer.MAX_VALUE;
		Node node = new Node(board, value, true);
		Iterator<String> itr = opponent.getMoves().iterator();
		while (itr.hasNext()) {
			String move = itr.next();
			Board temp = board.getDeepCopy();
			Player oTemp = opponent.getDeepCopy();
			oTemp.calculate(temp);
			temp.move2(oTemp, move);
			oTemp.calculate(temp);
			Node max = max(computer, oTemp, temp, alpha, beta, depth + 1);
			if (max.getValue() < node.getValue()) {
				node = max;
			}
			if (node.getValue() <= alpha) {
				if (currTime > (timeLimit * 1000)) {
					node.setSuccess(false);
				}
				return node;
			}

			if (node.getValue() < beta) {
				beta = node.getValue();
			}
		}
		currTime = (System.nanoTime() - startTime) / 1000000;
		if (currTime > (timeLimit * 1000)) {
			node.setSuccess(false);
		}
		return node;

	}

	public Node utility(Board board, Player player, Player player2, int depth, boolean timeout) {
		double value;
		boolean success;
		player.calculate(board);
		player2.calculate(board);
		if (timeout) {
			value = (player.isComputer()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			success = false;
		} else if (!player.hasMoves()) {
			value = (player.isComputer()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			success = true;
		} else if (!player2.hasMoves()) {
			value = (player.isComputer()) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			success = true;
		} else {
			value = (player.isComputer()) ?
					30 - player2.getNumMovesAvailable() : player2.getNumMovesAvailable() - 30;
			success = true;
		}
		Node node = new Node(board, value, success);
		return node;
	}
}