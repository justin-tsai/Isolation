import java.util.Iterator;

public class AlphaBeta {

	private int timeLimit;
	private long startTime;
	private long currTime;
	private int depthLimit = 10;
	private int turns;

	public AlphaBeta(int timeLimit) {
		this.timeLimit = timeLimit;
		turns = 0;
	}

	public String alphaBetaSearch(Player computer, Player opponent, Board board) {
		Node best = new Node();
		startTime = System.nanoTime();
		best = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		Board bestBoard = best.getBoard();
		String bestMove = bestBoard.getComputerMove()[turns];
		System.out.println("Best MAX: " + bestMove);
		System.out.println("Best MAX board: " + bestBoard.toString());
		System.out.println("Best MAX value: " + best.getValue());
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
		computer.calculate(board);
		int value = Integer.MIN_VALUE;
		Node node = new Node(board, value);
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
				return node;
			}

			if (node.getValue() > alpha) {
				alpha = node.getValue();
			}
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
		int value = Integer.MAX_VALUE;
		Node node = new Node(board, value);
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
				// System.out.println("v: " + value);
				return node;
			}

			if (node.getValue() < beta) {
				beta = node.getValue();
			}
		}
		return node;

	}

	public Node utility(Board board, Player player, Player player2, int depth, boolean timeout) {
		double value;
		player.calculate(board);
		player2.calculate(board);
		if (!player.hasMoves() || timeout) {
			value = (player.getSymbol() == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		} else if (!player2.hasMoves()) {
			value = (player.getSymbol() == 'X') ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		} else {
			value = (player.getSymbol() == 'O') ? -player.getNumMovesAvailable() + (10 / player2.getNumMovesAvailable())
					: player.getNumMovesAvailable() - (10 / player2.getNumMovesAvailable());
		}
		Node node = new Node(board, value);
		return node;
	}
}