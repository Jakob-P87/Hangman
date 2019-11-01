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
            //System.out.println("===============================");

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
                "\nIf the you guess wrong more then eight times you loose the game." +
                "\nIf you successfully fill all the blanks you win!");
    }

    static void mainGame()
    {
        System.out.println("===========[Hangman]===========");
        boolean keepPlaying = true;


        while (keepPlaying) {
            String word = wordLibrary();
            char[] underscore = new char[word.length()]; //get the length of the word and create array with same length
            Arrays.fill(underscore, '_'); //Fill all indexes in the underscore array with '_'

            int count = 0;

            while (true) {
                System.out.println(word);
                System.out.println(underscore);
                //System.out.println("The word is: " + newCharacter); //Print out the word as characters
                System.out.print("Letter: ");
                String guess = scan.next();

                userGuess(guess, word, underscore);
            }
        }
    }

    static String userGuess(String guess, String word, char[] underscore)
    {
        for (int i = 0; i < word.length(); i++) {
                if (guess.charAt(0) == word.charAt(i)) {
                    underscore[i] = word.charAt(i);
                }
        }
        //winLoose();

        return guess + word + underscore;
    }

    /*static void winLoose()
    {
        if(underscore == word)
        {
            System.out.println("The word was 'cow' You win!");
            mainMenu();
        }
    }*/

    //This method will return a random word from a String array
    static String wordLibrary()
    {
        String[] wordArray = {"cow", "apple", "bicycle", "distinct", "democratic", "sticky", "elevator", "doctor", "bull", "colors"};

        Random random = new Random(); //Create Random variable
        int randomWord = random.nextInt(wordArray.length); //This will assign a random word from the array to a new variable

        return wordArray[randomWord]; //Return the random word selected from the array
    }
}
