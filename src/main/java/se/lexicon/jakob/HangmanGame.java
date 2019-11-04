package se.lexicon.jakob;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static Scanner scan;

    public static void main(String[] args)
    {
        scan = new Scanner(System.in);

        mainMenu();

        scan.close();
    }

    static void mainMenu()
    {
        boolean gameRunning = true;

        while (gameRunning) {
            System.out.println("==========[Main Menu]==========");
            System.out.println("=====[Welcome to Hangman!]=====");
            System.out.print("1.Play a game\n2.Game rules\n3.Exit game\nSelection: ");
            int option = scan.nextInt();

            switch (option) {
                case 1:
                    mainGame();
                    break;
                case 2:
                    gameRules();
                    break;
                case 3:
                    gameRunning = false;
                    break;
                default:
                    System.out.print("Invalid selection!");
            }
        }
    }

    static void gameRules()
    {
        System.out.println("Hi there and welcome to Hangman." +
                "\nWhat is Hangman, it is a guessing game, Where one player chooses a Word," +
                "\nPhrase or Sentence and then draws one line-" +
                "\n-for each letter the newWords contain." +
                "\nThe game usually requires at least two players to be able to play a game." +
                "\nThe player that is guessing can either guess one letter at the time and try to fill the blanks-" +
                "\n-but can also try their luck and guess the whole newWord or sentence at once." +
                "\nIf the you guess wrongCounterCounter more then eight times you loose the game." +
                "\nIf you successfully fill all the blanks you win!");
    }

    //This method will keep the game running as long as the player has not guessed wrong to many times or the player wins by finding the right word
    static void mainGame()
    {
        System.out.println("===========[Hangman]===========");
        boolean keepPlaying = true;


        while (keepPlaying) {
            String newWord = wordLibrary(); //New variable for the random selected newWord
            char[] underscore = new char[newWord.length()]; //get the length of the newWord and create array with same length
            Arrays.fill(underscore, '_'); //Fill all indexes in the underscore array with '_'

            int count = 0;

            while (count <= 8) {

                if(count >= 8)
                {
                    loose();
                }

                for (int i = 0; i <newWord.length(); i++) //This loop will create a space between every index in the character array
                {
                    System.out.print(underscore[i]);
                    System.out.print(" ");
                }

                System.out.print("\nLetter: ");
                String guess = scan.next();

                count = userGuess(guess, newWord, underscore, count);
            }
        }
    }

    //Main method for user guesses
    static int userGuess(String guess, String newWord, char[] underscore, int count)
    {
        boolean letterFound = false;

        for (int i = 0; i < newWord.length(); i++) {
            if (guess.charAt(0) == newWord.charAt(i)) {
                underscore[i] = newWord.charAt(i);
                letterFound = true;
            } else if (guess.charAt(0) != newWord.charAt(i)) { //If the input is not equal to any letter in the newWord
                underscore[i] = underscore[i];
            }

            if (underscore.equals(newWord))
            {
                System.out.println("The word was " + newWord + " You win!");
            }
        }
        if (!letterFound) {
            count = wrongCounter(count);
            failedCharacters(guess);
        }

        return count;
    }

    //Method to store all the letter that is not correct
    static void failedCharacters(String guess)
    {
        StringBuilder usedCharacters = new StringBuilder();
        usedCharacters.append(guess);

        System.out.println(usedCharacters);
    }

    //Method to tell when the player win
    static void win()
    {

    }

    //Method to exit to menu when player loose
    static void loose()
    {
        System.out.println("You loose!");
        mainMenu();
    }

    //Method to count fails from user
    static int wrongCounter(int count)
    {
        count++;
        System.out.println("-------------------------------");
        System.out.println("Tries: " + count);
        return count;
    }

    //This method will return a random newWord from a String array
    static String wordLibrary()
    {
        String[] newWordArray = {"cow", "apple", "bicycle", "distinct", "democratic", "sticky", "elevator", "doctor", "bull", "colors"};

        Random random = new Random(); //Create Random variable
        int randomWord = random.nextInt(newWordArray.length); //This will assign a random newWord from the array to a new variable

        return newWordArray[randomWord]; //Return the random newWord selected from the array
    }
}
