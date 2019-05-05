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
		Node best = max(X, O, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		//default:
		Board bestBoard = best.getBoard();
		System.out.println("best board: \n" + bestBoard);
		String bestMove =  bestBoard.getComputerMove()[turns];
		turns++;
		System.out.println("move to" + bestMove);
		return bestMove;
	}

	public Node max(Player X, Player O, Board board, int alpha, int beta, int depth) {
		if (depth > depthLimit) {
			return utility(board, X, depth);
		}
		int value = Integer.MIN_VALUE;
		Node node = new Node(board, value);
		Iterator<String> itr = X.getMoves().iterator();
		while(itr.hasNext()) {
			String move = itr.next();
			Player xTemp = X.getDeepCopy();
			Player oTemp = O.getDeepCopy();
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
				//System.out.println("v: " + value);
				return node;
			}

			if (node.getN() > alpha) {
				alpha = node.getN();
			}
		}
		return node;

	}

	public Node min(Player X, Player O, Board board, int alpha, int beta, int depth) {
		if (depth > depthLimit) {
			return utility(board, O, depth);
		}
		int value = Integer.MAX_VALUE;
		Node node = new Node(board, value);
		Iterator<String> itr = O.getMoves().iterator();
		while(itr.hasNext()) {
			String move = itr.next();
			Board temp = board.getDeepCopy();
			Player oTemp = O.getDeepCopy();
			Player xTemp = X.getDeepCopy();
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
				//System.out.println("v: " + value);
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
			value = (player.getSymbol() == 'O') ? -player.getNumMovesAvailable() + depth : player.getNumMovesAvailable() - depth;
			finalB = board;
		}
		//System.out.println("Utility Value: " + value);
		Node node = new Node(board, value);
		return node;
	}

}