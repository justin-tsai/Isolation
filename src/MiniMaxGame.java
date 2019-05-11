import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class MiniMaxGame {
	static char[][] board = new char[][] {
		{'X', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', '-'},
		{'-', '-', '-', '-', '-', '-', '-', 'O'},
	};
	static int movesMade = 0;
	static ArrayList<String> computerMoveList = new ArrayList<String>();
	static ArrayList<String> opponentMoveList = new ArrayList<String>();
	static String xPosition = "A1";
	static String oPosition = "H8";
	static Scanner sc = new Scanner(System.in);
	static int timeLimit;
	static int winner = -1;
	
	public static void main(String[] args) {
		System.out.print("What is the time limit per move? ");
		timeLimit = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Who is going first? C for Computer, O for Opponent.");
		String starter = sc.nextLine();
		while (!starter.toUpperCase().equals("C") && !starter.toUpperCase().equals("O")){
			System.out.println("Not valid input. Enter C or O");
			starter = sc.nextLine();
		}
		if (starter.toUpperCase().equals("C")) {
			start(0);
		} else {
			start(1);
		}
		sc.close();
	}
	
	
	static void start(int firstPlayer) {
		printBoard();
		//firstPlayer will be 0 if computer is starting
		//and 1 if opponent is starting.
		if (firstPlayer == 1) {
			opponentMove();
//			//Print out possible moves
//			ArrayList<String> moves = possibleMoves(0);
//			System.out.println("PRINTING POSSIBLE MOVES");
//			for (int i = 0; i < moves.size(); i++) {
//				System.out.println(moves.get(i));
//			}
		}
		
		while (winner != 1 && winner != 0) {
			computerMove();
			//Checking to see if computer has hit goal state
			winner = checkGoalState(0);
			if (winner != -1) break;
			opponentMove();
			//Checking to see if opponent has hit goal state
			winner = checkGoalState(1);
			if (winner != -1) break;
		}
		//If computer wins, winner = 0, if opponent wins, winner = 1
		if (winner == 1) {
			System.out.println("Opponent wins!");
		} else if (winner == 0) {
			System.out.println("Computer Wins!");
		}
	}
	
	public static int checkGoalState(int player) {
		if (player == 0) {
			//Check how many possible moves for opponent
			ArrayList<String> moves = possibleMoves(1);
			System.out.println("Moves size " + moves.size());
			if (moves.size() == 0) return 1;
		} else if (player == 1) {
			//Check how many possible moves for computer
			ArrayList<String> moves = possibleMoves(0);
			if (moves.size() == 0) return 0;
		}
		return -1;
	}
	
	public static ArrayList<String> possibleMoves(int player) {
		ArrayList<String> a = new ArrayList<String>();
		int y = -1, x = -1, checkX, checkY;
		
		if (player == 0) {
			//Checking what moves opponent has
			y = convertToInt(oPosition.charAt(0));
			x = Integer.parseInt(String.valueOf(oPosition.charAt(1))) - 1;
		} else if (player == 1) {
			//Checking what moves computer has
			y = convertToInt(xPosition.charAt(0));
			x = Integer.parseInt(String.valueOf(xPosition.charAt(1))) - 1;
		}
			
		//check moves above
		for (int i = 1; i < 8; i++) {
			checkY = y - i;
			if (checkY < 0 || checkY > 7) break;
			if (board[checkY][x] != '-') break;
			a.add(createMove(checkY, x+1));
		}
		
		//check moves below
		for (int i = 1; i < 8; i++) {
			checkY = y + i;
			if (checkY < 0 || checkY > 7) break;
			if (board[checkY][x] != '-') break;
			a.add(createMove(checkY, x+1));
		}
		
		//check moves right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			if (checkX < 0 || checkX > 7) break;
			if (board[y][checkX] != '-') break;
			a.add(createMove(y, checkX+1));
		}
		
		//check moves left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			if (checkX < 0 || checkX > 7) break;
			if (board[y][checkX] != '-') break;
			a.add(createMove(y, checkX+1));
		}
		
		//check moves up right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			checkY = y - i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves down right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			checkY = y + i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves down left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			checkY = y + i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves up left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			checkY = y - i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
			
		return a;
	}
	
	public static void computerMove() {
		String move = findBestMove();
		computerMoveList.add(move);
		updateMovesMade();
		
		//update board
		int y = convertToInt(move.charAt(0));
		int x = Integer.parseInt(String.valueOf(move.charAt(1)));
		updateBoard(0, x, y, move);
		printBoard();
	}
	
	public static void opponentMove() {
		//ask
		String move = ask();
		opponentMoveList.add(move);
		updateMovesMade();
		
		//update board
		int y = convertToInt(move.charAt(0));
		int x = Integer.parseInt(String.valueOf(move.charAt(1)));
		updateBoard(1, x, y, move);
		
		printBoard();
	}
	
	public static String findBestMove() {
		int depth = 1;
		String bestMove = "NOT INITIALIZED";
		int highestIndex = 0;
		int highestValue = 0;
		int currentValue = 0;
		long startTime = System.nanoTime();
		long time = (System.nanoTime() - startTime)/1000000;
		boardNode b = new boardNode(board, xPosition, oPosition);
		b.generateChildren(1);
		while(time < (timeLimit*1000)) {
			for (int i = 0; i < b.children.size(); i++) {
				time = (System.nanoTime() - startTime)/1000000;
				if (time > (timeLimit*1000)) {
					return bestMove;
				}
				currentValue = minimax(new boardNode(b.children.get(i).board, b.children.get(i).xPosition, b.children.get(i).oPosition), depth, 1, -100000, 100000);
				if (currentValue > highestValue) {
					highestValue = currentValue;
					highestIndex = i;
				}
			}
			
			bestMove = b.children.get(highestIndex).move;
			time = (System.nanoTime() - startTime)/1000000;
			depth++;
		}
		
		return bestMove;
	}
	
	public static int minimax(boardNode node, int depth, int player, int alpha, int beta){
		//0 is opponent
		//1 is computer
		//Generate Children
		if (player == 0) {
			node.generateChildren(0);
		} else {
			node.generateChildren(1);
		}
		
		if (depth == 0 || node.children.size() == 0) {
			return node.evaluate();
		}
		//Computer: Max
		if (player == 1) {
			//System.out.println("Max");
			int value = -100000;
			for (int i = 0; i < node.children.size(); i++) {
				value = max(value, minimax(node.children.get(i), depth - 1, 0, alpha, beta));
				alpha = max(alpha, value);
				if (alpha >= beta) {
					break;
				}
			}
			return value;
		}
		//Opponent: Min
		else {
			//System.out.println("Min");
			int value = 100000;
			for (int i = 0; i < node.children.size(); i++) {
				value = min(value, minimax(node.children.get(i), depth - 1, 1, alpha, beta));
				beta = min(beta, value);
				if (alpha >= beta) {
					break;
				}
			}
			return value;
		}
	}
	
	public static int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	public static int min(int a, int b) {
		if (a < b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static int evaluate(boardNode node) {
		//We need evaluate to the an increasing function...
		//The less available moves for the opponent, the higher this will evaluate
		return node.evaluate();
	}
	
 	public static void updateBoard(int player, int x, int y, String move) {
		if (player == 1) {
			//opponent
			int oldY = convertToInt(oPosition.charAt(0));
			int oldX = Integer.parseInt(String.valueOf(oPosition.charAt(1)));
			board[oldY][oldX-1] = '#';
			board[y][x-1] = 'O';
			updateOpponent(move);
		} else if (player == 0) {
			//computer
			int oldY = convertToInt(xPosition.charAt(0));
			int oldX = Integer.parseInt(String.valueOf(xPosition.charAt(1)));
			board[oldY][oldX-1] = '#';
			board[y][x-1] = 'X';
			updateComputer(move);
		}
	}

	public static String ask() {
		System.out.print("What was the opponent's move? ");
		String move = sc.nextLine();
		return move;
		
	}
	
	public static void updateOpponent(String move) {
		oPosition = move;
	}
	
	public static void updateComputer(String move) {
		xPosition = move;
	}
			
	public static void updateMovesMade() {
		if (computerMoveList.size() > opponentMoveList.size()) {
			movesMade = computerMoveList.size();
		} else {
			movesMade = opponentMoveList.size();
		}
	}
	
	public static void printBoard() {
		if (movesMade == 0) {
			System.out.println("  1 2 3 4 5 6 7 8");
			for (int i = 0; i < 8; i++) {
				System.out.print(convertToChar(i) + " ");
				for (int j = 0; j < 8; j++) {
					System.out.print(board[i][j] + " ");
				}
				System.out.println();
			}
		} else {
			int count = 0;
			System.out.printf("  1 2 3 4 5 6 7 8%30s\n", "Computer vs. Opponent");
			for (int i = 0; i < 8; i++) {
				System.out.print(convertToChar(i) + " ");
				for (int j = 0; j < 8; j++) {
					System.out.print(board[i][j] + " ");
				}
				if (count < movesMade) {
					String computerMove, opponentMove;
					if (computerMoveList.size() != movesMade && (count+1) == movesMade) {
						computerMove = "  ";
					} else {
						computerMove = computerMoveList.get(count);
					}
					
					if (opponentMoveList.size() != movesMade && (count+1) == movesMade) {
						opponentMove = "  ";
					} else {
						opponentMove = opponentMoveList.get(count);
					}
					
					System.out.printf("%6d. %s %12s", count+1, computerMove, opponentMove);
					count++;
				}
				System.out.println();
			}
			
			while (count < movesMade) {
				String computerMove, opponentMove;
				if (computerMoveList.size() != movesMade && (count+1) == movesMade) {
					computerMove = "  ";
				} else {
					computerMove = computerMoveList.get(count);
				}
				
				if (opponentMoveList.size() != movesMade && (count+1) == movesMade) {
					opponentMove = "  ";
				} else {
					opponentMove = opponentMoveList.get(count);
				}
				
				System.out.printf("%24d. %s %12s\n", count+1, computerMove, opponentMove);
				count++;
			}
		}
		System.out.println();
	}
	
	static String createMove(int y, int x) {
		String s = "" + convertToChar(y) + x;
		return s;
	}
	
	static char convertToChar(int row) {
		switch (row) {
			case 0: 
				return 'A';
			case 1:
				return 'B';
			case 2: 
				return 'C';
			case 3:
				return 'D';
			case 4: 
				return 'E';
			case 5:
				return 'F';
			case 6: 
				return 'G';
			case 7:
				return 'H';
		}
		return '$';
	}
	
	static int convertToInt(char row) {
		switch (row) {
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			case 'E':
				return 4;
			case 'F':
				return 5;
			case 'G':
				return 6;
			case 'H':
				return 7;
		}
		return -1;
	}

}

class boardNode {
	public char[][] board = new char[8][8];
	public String xPosition, oPosition;
	public boardNode parent;
	public ArrayList<boardNode> children = new ArrayList<boardNode>();
	public int value;
	public String move;
	
	public boardNode(char[][] parentBoard, String move, int whoToMove, boardNode parent, String xPos, String oPos) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = parentBoard[i][j];
			}
		}
		this.parent = parent;
		xPosition = xPos;
		oPosition = oPos;
		this.move = move;
		makeMove(move, whoToMove);
	}
	
	public boardNode(char[][] parentBoard, String xPos, String oPos) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = parentBoard[i][j];
			}
		}
		xPosition = xPos;
		oPosition = oPos;
	}
	
	public void setValue(int val) {
		this.value = val;
	}
	
	public int evaluate() {
		ArrayList<String> moves = possibleMoves(0, oPosition, xPosition, board);
		return (1000 - moves.size());
	}
	
	public void generateChildren(int player) {
		if (player == 0) {
			ArrayList<String> pm = possibleMoves(0, oPosition, xPosition, board);
			for (int i = 0; i < pm.size(); i++) {
				boardNode n = new boardNode(board, pm.get(i), 0, this, xPosition, oPosition);
				children.add(n);
			}
		} else {
			ArrayList<String> pm = possibleMoves(1, oPosition, xPosition, board);
			for (int i = 0; i < pm.size(); i++) {
				boardNode n = new boardNode(board, pm.get(i), 1, this, xPosition, oPosition);
				children.add(n);
			}
		}
	}
	
	public static ArrayList<String> possibleMoves(int player, String oPosition, String xPosition, char[][] board) {
		ArrayList<String> a = new ArrayList<String>();
		int y = -1, x = -1, checkX, checkY;
		
		if (player == 0) {
			//Checking what moves opponent has
			y = convertToInt(oPosition.charAt(0));
			x = Integer.parseInt(String.valueOf(oPosition.charAt(1))) - 1;
		} else if (player == 1) {
			//Checking what moves computer has
			y = convertToInt(xPosition.charAt(0));
			x = Integer.parseInt(String.valueOf(xPosition.charAt(1))) - 1;
		}
			
		//check moves above
		for (int i = 1; i < 8; i++) {
			checkY = y - i;
			if (checkY < 0 || checkY > 7) break;
			if (board[checkY][x] != '-') break;
			a.add(createMove(checkY, x+1));
		}
		
		//check moves below
		for (int i = 1; i < 8; i++) {
			checkY = y + i;
			if (checkY < 0 || checkY > 7) break;
			if (board[checkY][x] != '-') break;
			a.add(createMove(checkY, x+1));
		}
		
		//check moves right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			if (checkX < 0 || checkX > 7) break;
			if (board[y][checkX] != '-') break;
			a.add(createMove(y, checkX+1));
		}
		
		//check moves left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			if (checkX < 0 || checkX > 7) break;
			if (board[y][checkX] != '-') break;
			a.add(createMove(y, checkX+1));
		}
		
		//check moves up right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			checkY = y - i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves down right
		for (int i = 1; i < 8; i++) {
			checkX = x + i;
			checkY = y + i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves down left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			checkY = y + i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
		
		//check moves up left
		for (int i = 1; i < 8; i++) {
			checkX = x - i;
			checkY = y - i;
			if (checkX < 0 || checkX > 7 || checkY < 0 || checkY > 7) break;
			if (board[checkY][checkX] != '-') break;
			a.add(createMove(checkY, checkX+1));
		}
			
		return a;
	}
	
	static String createMove(int y, int x) {
		String s = "" + convertToChar(y) + x;
		return s;
	}
	
	public void makeMove(String move, int whoToMove) {
		// 0 is opponent
		// 1 is computer
		if (whoToMove == 1) {
			int oldY = convertToInt(xPosition.charAt(0));
			int oldX = Integer.parseInt(String.valueOf(xPosition.charAt(1)));
			int y = convertToInt(move.charAt(0));
			int x = Integer.parseInt(String.valueOf(move.charAt(1)));
			board[oldY][oldX-1] = '#';
			board[y][x-1] = 'X';
			xPosition = move;
		} else if (whoToMove == 0) {
			int oldY = convertToInt(oPosition.charAt(0));
			int oldX = Integer.parseInt(String.valueOf(oPosition.charAt(1)));
			int y = convertToInt(move.charAt(0));
			int x = Integer.parseInt(String.valueOf(move.charAt(1)));
			board[oldY][oldX-1] = '#';
			board[y][x-1] = 'X';
			oPosition = move;
		}
	}
	
	static char convertToChar(int row) {
		switch (row) {
			case 0: 
				return 'A';
			case 1:
				return 'B';
			case 2: 
				return 'C';
			case 3:
				return 'D';
			case 4: 
				return 'E';
			case 5:
				return 'F';
			case 6: 
				return 'G';
			case 7:
				return 'H';
		}
		return '$';
	}
	
	static int convertToInt(char row) {
		switch (row) {
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			case 'E':
				return 4;
			case 'F':
				return 5;
			case 'G':
				return 6;
			case 'H':
				return 7;
		}
		return -1;
	}
	
}
