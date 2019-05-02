import java.util.ArrayList;
import java.util.List;

public class Player {

	private int[] possibleMoves;
	private int numMovesMade;
	private int x;
	private int y;
	private char symbol;
	private List<String> moves = new ArrayList<String>();
	
	public Player(int x, int y, char symbol) {
		possibleMoves = new int[8];
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		numMovesMade = 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public void moveMade() {
		numMovesMade++;
	}
	
	public int getNumMovesMade() {
		return numMovesMade;
	}
	
	public boolean canMove(String move) {
		if(moves.contains(move)) {
			return true;
		}
		return false;
	}
	
	public boolean hasMoves() {
		if(moves.isEmpty()){
			return false;
		}else {
			return true;
		}
	}
	
	public int[] calculate(Board board) {
		moves.clear();
		char[][] boardState = board.getBoardState();
		//N
		int tempX = x;
		int tempY = y - 1;
		while (tempY >= 0) {
			if(boardState[tempX][tempY] == '-')  {
				possibleMoves[0]++;
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
			//System.out.println("x: " + tempX);
			//System.out.println("y: " + tempY);
			if(boardState[tempX][tempY] == '-')  {
				possibleMoves[1]++;
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
				possibleMoves[2]++;
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
				possibleMoves[3]++;
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
				possibleMoves[4]++;
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
				possibleMoves[5]++;
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
				possibleMoves[6]++;
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
				possibleMoves[7]++;
				String move = Integer.toString(tempX) + Integer.toString(tempY);
				moves.add(move);
				tempX--;
				tempY--;
			}else {
				break;
			}
		}
		return possibleMoves;
	}
	
	
	
	
}
