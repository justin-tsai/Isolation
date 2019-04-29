
public class Game {

	private Board board;
	private char first;
	private int timeLimit;
	
	public Game(char first, int timeLimit) {
		this.first = first;
		this.timeLimit = timeLimit;
		board = new Board();
		//System.out.println("first: " + first);
		//System.out.println("time limit per move: " + timeLimit + " seconds");
		start();
	}
	
	public void start() {
		System.out.println("Game start!\n\n" + board.toString());
		boolean playing = true;
		if(first == 'C') {
			while(playing) {
				playing = movePLAYER();
				playing = moveAI();
			}
		}else if(first == 'O') {
			while(playing) {
				playing = moveAI();
				playing = movePLAYER();
			}
		}else {
			System.out.println("Error with determining who goes first");
		}
	}
	
	public boolean movePLAYER() {
		//System.out.println("plyr");
		//board.move(true, "D3");
		//System.out.println(board.toString());
		return true;
	}
	
	public boolean moveAI() {
		//System.out.println("ai");
		//board.move(false, "D4");
		//System.out.println(board.toString());
		return false;
	}
}
