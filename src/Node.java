
public class Node {
	private Board board;
	private double value;
	
	public Node() {
		
	}
	
	public Node(Board board, double value) {
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
	
	public double getValue() {
		return value;
	}
}
