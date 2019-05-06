/**
 * Player represents a player of the game. Each board will have two.
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
	
	/**
	 * Empty player constructor for purpose of deep copy.
	 */
	public Player() {
		
	}
	
	/**
	 * Player constructor.
	 * @param x			X-coordinate of the Player.
	 * @param y			Y-coordinate of the Player.
	 * @param symbol	Symbol that the Player is representing (Either X or O).
	 */
	public Player(int x, int y, char symbol) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		numMovesMade = 0;
	}
	
	/**
	 * Returns a deep copy of the Player.
	 * @return		Duplicate player object with the same values.
	 */
	public Player getDeepCopy() {
		Player copy = new Player();
		copy.numMovesMade = this.numMovesMade;
		copy.x = this.x;
		copy.y = this.y;
		copy.symbol = this.symbol;
		for(String str : moves) {
			copy.moves.add(str);
		}
		return copy;
	}
	
	/**
	 * Sets the position of the Player.
	 * @param x		The X-coordinate the Player is at.
	 * @param y		The Y-coordinate the Player is at.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter method the X-coordinate the Player is at.
	 * @return		The X-coordinate the Player is at.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter method for the Y-coordinate the Player is at.
	 * @return		The Y-coordinate the Player is at.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Getter method for the symbol the Player is representing.
	 * @return		The symbol that the Player is representing (Either X or O).
	 */
	public char getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Increment the number of moves made by the Player.
	 */
	public void moveMade() {
		numMovesMade++;
	}
	
	/**
	 * Getter method for the number of moves made by the Player.
	 * @return		The number of moves made by the Player.
	 */
	public int getNumMovesMade() {
		return numMovesMade;
	}
	
	/**
	 * Whether or not the Player can move to the space passed as the argument.
	 * @param move		The coordinates that the Player wants to move to.
	 * @return			Whether or not the Player is able to move there.
	 */
	public boolean canMove(String move) {
		if(moves.contains(move)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether or not the Player has moves that they can make.
	 * @return		Whether or not the Player has a move they can make.
	 */
	public boolean hasMoves() {
		if(moves.isEmpty()){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Calculates the possible moves that the Player can make given the current board state.
	 * @param board		The current board state.
	 */
	public void calculate(Board board) {
		moves.clear();
		char[][] boardState = board.getBoardState();
		//N
		int tempX = x;
		int tempY = y - 1;
		while (tempY >= 0) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempY--;
			}else {
				break;
			}
		}
		//NE
		tempX = x + 1;
		tempY = y - 1;
		while (tempY >= 0 && tempX < 8) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
				tempY--;
			}else {
				break;
			}
		}
		
		//E
		tempX = x + 1;
		tempY = y;
		while (tempX < 8) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
			}else {
				break;
			}
		}
		//SE
		tempX = x + 1;
		tempY = y + 1;
		while (tempX < 8 && tempY < 8) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX++;
				tempY++;
			}else {
				break;
			}
		}
		
		//S
		tempX = x;
		tempY = y + 1;
		while (tempY < 8) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempY++;
			}else {
				break;
			}
		}
		//SW
		tempX = x - 1;
		tempY = y + 1;
		while (tempX >= 0 && tempY < 8) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
				tempY++;
			}else {
				break;
			}
		}
		
		//W
		tempX = x - 1;
		tempY = y;
		while (tempX >= 0) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
			}else {
				break;
			}
		}
		//NW
		tempX = x - 1;
		tempY = y - 1;
		while (tempX >= 0 && tempY >= 0) {
			if(boardState[tempX][tempY] == '-')  {
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
				tempY--;
			}else {
				break;
			}
		}
	}

	/**
	 * Getter method for the number of moves available that the player can make.
	 * @return		The number of moves available.
	 */
	public int getNumMovesAvailable() {
		return moves.size();
	}
	
	/**
	 * Getter method for the moveset of available moves that the player can make.
	 * @return		The whole moveset.
	 */
	public ArrayList<String> getMoves(){
		return moves;
	}
	
	
}
