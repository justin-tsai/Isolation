
public class Node {
	private Board board;
	private double value;
	private boolean success;
	
	public Node() {
		
	}
	
	public Node(Board board, double value, boolean success) {
		this.board = board;
		this.value = value;
		this.success = success;
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
	
	public boolean successfulRun() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
