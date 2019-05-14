
/**
 * Main acts as a Driver class to start a game.
 * @author Justin
 *
 */

import java.util.Scanner;

public class Main {

	private static Scanner keyboard = new Scanner(System.in);
	private static boolean validInput = false;
	private static char first;
	private static char computerSymbol;
	private static int timeLimit;

	/**
	 * Asks the user for the parameters of the game, and then creates a game with those parameters.
	 * @param args
	 */
	public static void main(String[] args) {

		while (!validInput) {
			try {
				System.out.print("Who goes first, C for computer, O for opponent: ");
				first = keyboard.next().charAt(0);
				if (first == 'C' || first == 'O' || first == 'c' || first == 'o') {
					first = Character.toUpperCase(first);
					validInput = true;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. ");
			}
		}

		validInput = false;
		keyboard.nextLine();
		
		while (!validInput) {
			try {
				System.out.print("Is computer X or O?: ");
				computerSymbol = keyboard.next().charAt(0);
				if (computerSymbol == 'X' || computerSymbol == 'x' || computerSymbol == 'O' || computerSymbol == 'o') {
					computerSymbol = Character.toUpperCase(computerSymbol);
					validInput = true;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. ");
				keyboard.nextLine();
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
		Game game = new Game(first, computerSymbol, timeLimit);
		game.start();
		keyboard.close();
		
	}
}