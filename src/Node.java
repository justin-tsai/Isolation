/**
 * Node to representing nodes for Alpha Beta.
 * @author Justin
 *
 */
public class Node {
	
	private Board board;
	private int value;
	
	public Node() {
		
	}
	
	public Node(Board board, int value) {
		this.board = board;
		this.value = value;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
