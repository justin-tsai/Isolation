import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static Scanner keyboard = new Scanner(System.in);
	private static boolean validInput = false;
	private static char first;
	private static int timeLimit;

	public static void main(String[] args) {

		while (!validInput) {
			try {
				System.out.print("Who goes first, C for computer, O for opponent: ");
				first = keyboard.next().charAt(0);
				if (first == 'C' || first == 'O' || first == 'c' || first == 'o') {
					validInput = true;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. ");
			}
		}

		validInput = false;
		
		while (!validInput) {
			try {
				System.out.print("Input time limit per move (seconds): ");
				timeLimit = keyboard.nextInt();
				if (timeLimit > 0) {
					validInput = true;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid time.");
				keyboard.nextLine();
			}
		}
		keyboard.nextLine();
		Game game = new Game(first, timeLimit);
		game.start();
		keyboard.close();
		
		
		
	}
}
