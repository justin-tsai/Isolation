
public class Node {
	private Board b;
	private int n;
	
	public Node() {
		
	}
	
	public Node(Board b, int n) {
		this.b = b;
		this.n = n;
	}
	
	public void setBoard(Board b) {
		this.b = b;
	}
	
	public Board getBoard() {
		return b;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
}
