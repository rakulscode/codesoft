import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    private static final int MAX_ATTEMPTS = 10;
    private static int rounds = 0; // Added missing declaration

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int totalAttempts = 0;
        int totalRoundsWon = 0;
        double bestScore = Double.MAX_VALUE;

        System.out.println("Welcome to the Number Game!");

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        do {
            int lowerBound, upperBound;
            System.out.print("\nSelect difficulty level (easy/medium/hard): ");
            String difficulty = scanner.next().toLowerCase();

            switch (difficulty) {
                case "easy":
                    lowerBound = 1;
                    upperBound = 50;
                    break;
                case "medium":
                    lowerBound = 1;
                    upperBound = 100;
                    break;
                case "hard":
                    lowerBound = 1;
                    upperBound = 200;
                    break;
                default:
                    System.out.println("Invalid difficulty. Defaulting to medium.");
                    lowerBound = 1;
                    upperBound = 100;
            }

            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = playRound(scanner, playerName, lowerBound, upperBound, targetNumber);

            totalAttempts += attempts;
            totalRoundsWon += (attempts <= MAX_ATTEMPTS) ? 1 : 0;
            bestScore = Math.min(bestScore, attempts);

            rounds++;

        } while (playAgain(scanner));

        scanner.close();

        if (rounds > 0) {
            double averageAttempts = (double) totalAttempts / rounds;
            displayGameResult(playerName, rounds, totalRoundsWon, averageAttempts, bestScore);
        } else {
            System.out.println("Thanks for playing, " + playerName + "!");
        }
    }

    private static int playRound(Scanner scanner, String playerName, int lowerBound, int upperBound, int targetNumber) {
        int attempts = 0;
        long startTime = System.currentTimeMillis();

        System.out.println("\nHello, " + playerName + "! Round " + rounds + ": Guess the number between "
                + lowerBound + " and " + upperBound);

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter your guess: ");
            try {
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == targetNumber) {
                    long endTime = System.currentTimeMillis();
                    double timeTaken = (endTime - startTime) / 1000.0;
                    System.out.println("Congratulations, " + playerName + "! You guessed the correct number in "
                            + attempts + " attempts and " + timeTaken + " seconds.");
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low. Try again. Attempts left: " + (MAX_ATTEMPTS - attempts));
                } else {
                    System.out.println("Too high. Try again. Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        if (attempts == MAX_ATTEMPTS) {
            System.out.println("Sorry, " + playerName
                    + "! You've reached the maximum number of attempts. The correct number was: " + targetNumber);
        }

        return attempts;
    }

    private static boolean playAgain(Scanner scanner) {
        System.out.print("Do you want to play again? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }

    private static void displayGameResult(String playerName, int rounds, int totalRoundsWon, double averageAttempts,
            double bestScore) {
        System.out.println("\nGame Over, " + playerName + "! Your score: Rounds played: " + rounds +
                ", Rounds won: " + totalRoundsWon +
                ", Average attempts per round: " + averageAttempts +
                ", Best score: " + (bestScore == Double.MAX_VALUE ? "N/A" : bestScore));
    }
}
