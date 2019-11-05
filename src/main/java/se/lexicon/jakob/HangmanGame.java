package se.lexicon.jakob;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static Scanner scan;
    public static StringBuilder charToString = new StringBuilder();

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
                "\n-for each letter the words contain." +
                "\nThe game usually requires at least two players to be able to play a game." +
                "\nThe player that is guessing can either guess one letter at the time and try to fill the blanks-" +
                "\n-but can also try their luck and guess the whole word or sentence at once." +
                "\nIf the you guess wrongCounterCounter more then eight times you loose the game." +
                "\nIf you successfully fill all the blanks you win!");
    }

    //This method will keep the game running as long as the player has not guessed wrong to many times or the player wins by finding the right word
    static void mainGame()
    {
        System.out.println("===========[Hangman]===========");
        boolean keepPlaying = true;


        while (keepPlaying) {
            String word = wordLibrary(); //New variable for the random selected word
            char[] underscore = new char[word.length()]; //get the length of the word and create array with same length
            Arrays.fill(underscore, '_'); //Fill all indexes in the underscore array with '_'

            int count = 0;

            while (count <= 8) {
                if (underscore.equals(word)) {
                    win(word);
                }
                System.out.println(charToString);

                if (count >= 8) {
                    loose();
                }

                System.out.println(word);
                for (int i = 0; i < word.length(); i++) //This loop will create a space between every index in the character array
                {
                    System.out.print(underscore[i]);
                    System.out.print(" ");
                }

                System.out.print("\nLetter: ");
                String guess = scan.next();

                count = userGuess(guess, word, underscore, count);
            }
        }
    }

    //Main method for user guesses
    static int userGuess(String guess, String word, char[] underscore, int count)
    {
        boolean letterFound = false;

        for (int i = 0; i < word.length(); i++) {
            if (guess.charAt(0) == word.charAt(i)) {
                underscore[i] = word.charAt(i);
                letterFound = true;
                charToString.append(underscore[i]);
            } else if (guess.charAt(0) != word.charAt(i)) { //If the input is not equal to any letter in the word
                underscore[i] = underscore[i];
            }
        }
        if (!letterFound) {
            count = wrongCounter(count);
        }

        return count;
    }

    //Method to tell when the player win
    static void win(String word)
    {
        System.out.println("The word was " + word + " You win!");
        mainMenu();
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

    //This method will return a random word from a String array
    static String wordLibrary()
    {
        String[] wordArray = {"cow", "apple", "bicycle", "distinct", "democratic", "sticky", "elevator", "doctor", "bull", "colors"};

        Random random = new Random(); //Create Random variable
        int randomWord = random.nextInt(wordArray.length); //This will assign a random word from the array to a new variable

        return wordArray[randomWord]; //Return the random word selected from the array
    }
}
