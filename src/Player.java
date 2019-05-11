/**
 * Player represents a player of the Game.
 * @author Justin
 *
 */

import java.util.ArrayList;

public class Player {

	private int numMovesMade;
	private int x;
	private int y;
	private char symbol;
	private ArrayList<String> moves = new ArrayList<String>();
	private boolean computer;

	/**
	 * Empty constructor.
	 */
	public Player() {

	}

	/**
	 * Constructor for a Player object.
	 * @param x			The x position of the player.
	 * @param y			The y position of the player.
	 * @param symbol	The symbol the Player is respresenting (O or X)
	 * @param computer	Whether or not the player is a computer.
	 */
	public Player(int x, int y, char symbol, boolean computer) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		numMovesMade = 0;
		this.computer = computer;
	}

	/**
	 * Returns a deep copy of the player.
	 * @return	Duplicate Player object with the same values.
	 */
	public Player getDeepCopy() {
		Player copy = new Player();
		copy.numMovesMade = this.numMovesMade;
		copy.x = this.x;
		copy.y = this.y;
		copy.symbol = this.symbol;
		for (String str : moves) {
			copy.moves.add(str);
		}
		copy.computer = this.computer;
		return copy;
	}

	/**
	 * Set the current position of the player.
	 * @param x		Current x position.
	 * @param y		Current y position.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return Returns the current X position of the player.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return Returns the current Y position of the player.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return Returns the symbol of the player.
	 */
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Increments the number of moves made by the player.
	 */
	public void moveMade() {
		numMovesMade++;
	}

	/**
	 * @return Returns whether or not the player is a computer.
	 */
	public boolean isComputer() {
		return this.computer;
	}
	
	/**
	 * @return Returns the number of moves made by the player.
	 */
	public int getNumMovesMade() {
		return numMovesMade;
	}

	/**
	 * @param move	The move input being tested.
	 * @return 		Returns whether or not the player can move to the move input
	 * 
	 */
	public boolean canMove(String move) {
		if (moves.contains(move)) {
			return true;
		}
		return false;
	}

	/**
	 * @return	 Whether or not the player has moves they can make.	
	 */
	public boolean hasMoves() {
		if (moves.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Calculate the possible moves the player can make and stores into ArrayList.
	 * @param board		The current board state.
	 */
	public void calculate(Board board) {
		moves.clear();
		char[][] boardState = board.getBoardState();
		// NE
		int tempX = x + 1;
		int tempY = y - 1;
		while (tempY >= 0 && tempX < 8) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
				tempY--;
			} else {
				break;
			}
		}
		// N
		tempX = x;
		tempY = y - 1;
		while (tempY >= 0) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempY--;
			} else {
				break;
			}
		}
		
		
		// SW
		tempX = x - 1;
		tempY = y + 1;
		while (tempX >= 0 && tempY < 8) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
				tempY++;
			} else {
				break;
			}
		}

		// NW
		tempX = x - 1;
		tempY = y - 1;
		while (tempX >= 0 && tempY >= 0) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
				tempY--;
			} else {
				break;
			}
		}

		// SE
		tempX = x + 1;
		tempY = y + 1;
		while (tempX < 8 && tempY < 8) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
				tempY++;
			} else {
				break;
			}
		}

		// S
		tempX = x;
		tempY = y + 1;
		while (tempY < 8) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempY++;
			} else {
				break;
			}
		}

		// E
		tempX = x + 1;
		tempY = y;
		while (tempX < 8) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
			} else {
				break;
			}
		}

		// W
		tempX = x - 1;
		tempY = y;
		while (tempX >= 0) {
			if (boardState[tempX][tempY] == '-') {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
			} else {
				break;
			}
		}
	}


	/**
	 * @return The number of moves available that the player can make.
	 */
	public int getNumMovesAvailable() {
		return moves.size();
	}

	/**
	 * @return The current moveset that the player can move to.
	 */
	public ArrayList<String> getMoves() {
		return moves;
	}

}
