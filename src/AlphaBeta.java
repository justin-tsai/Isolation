/**
 * Alpha Beta search with iterative deepening and time limit.
 * @author Justin
 *
 */

import java.util.Iterator;

public class AlphaBeta {

	private int timeLimit;
	private long startTime;
	private long currTime;
	private int depthLimit = 1;
	private int turns;

	/**
	 * Constructor for AlphaBeta
	 * @param timeLimit		The maximum amount of seconds that AlphaBeta has to find a best move.
	 */
	public AlphaBeta(int timeLimit) {
		this.timeLimit = timeLimit;
		turns = 0;
	}

	/**
	 * Searches for the best move the computer can make using Alpha Beta and iterative deepening.
	 * @param computer		The player that the computer is representing.
	 * @param opponent		The player that the opponent is representing.
	 * @param board			The current board state.
	 * @return				The best move that the computer can make.
	 */
	public String alphaBetaSearch(Player computer, Player opponent, Board board) {
		startTime = System.nanoTime();
		Node best = new Node();
		best = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		while (currTime < (timeLimit * 1000)) {
			depthLimit++;
			best = max(computer, opponent, board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		}
		Board bestBoard = best.getBoard();
		String bestMove = bestBoard.getComputerMove()[turns];
		turns++;
		return bestMove;
	}

	/**
	 * The function to find the best MAX for Alpha Beta using its utility value. 
	 * @param computer		The player that the computer is representing.
	 * @param opponent		The player that the opponent is representing.
	 * @param board			The current state of the board.
	 * @param alpha			The current best alpha.
	 * @param beta			The current best beta.
	 * @param depth			The current depth the search is at.
	 * @return				The best MAX node with its utility value.
	 */
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
			Player computerCopy = computer.getDeepCopy();
			Board boardCopy = board.getDeepCopy();
			computerCopy.calculate(boardCopy);
			boardCopy.numInputMove(computerCopy, move);
			Node min = min(computerCopy, opponent, boardCopy, alpha, beta, depth + 1);
			if (min.getValue() > node.getValue()) {
				node = min;
			}
			if (node.getValue() >= beta) {
				return node;
			}

			if (node.getValue() > alpha) {
				alpha = node.getValue();
			}
		}
		return node;

	}

	/**
	 * The function to find the best MIN for Alpha Beta using its utility value. 
	 * @param computer		The player that the computer is representing.
	 * @param opponent		The player that the opponent is representing.
	 * @param board			The current state of the board.
	 * @param alpha			The current best alpha.
	 * @param beta			The current best beta.
	 * @param depth			The current depth the search is at.
	 * @return				The best MIN node with its utility value.
	 */
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
			Board boardCopy = board.getDeepCopy();
			Player opponentCopy = opponent.getDeepCopy();
			opponentCopy.calculate(boardCopy);
			boardCopy.numInputMove(opponentCopy, move);
			opponentCopy.calculate(boardCopy);
			Node max = max(computer, opponentCopy, boardCopy, alpha, beta, depth + 1);
			if (max.getValue() < node.getValue()) {
				node = max;
			}
			if (node.getValue() <= alpha) {
				return node;
			}

			if (node.getValue() < beta) {
				beta = node.getValue();
			}
		}
		return node;

	}

	/**
	 * The utility function to evaluate the current board for the player.
	 * @param board		The current state of the board.
	 * @param player	The player being evaluated.
	 * @param depth		The current depth the utility function is being called from.
	 * @return			A node with the current board and the utility value.
	 */
	public Node utility(Board board, Player player, int depth) {
		int value;
		player.calculate(board);
		if (!player.hasMoves()) {
			value = (player.getSymbol() == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		} else {
			value = (player.getSymbol() == 'O') ? -player.getNumMovesAvailable() + depth : player.getNumMovesAvailable() - depth;
		}
		Node node = new Node(board, value);
		return node;
	}
}