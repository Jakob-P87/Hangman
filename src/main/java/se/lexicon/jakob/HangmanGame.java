package se.lexicon.jakob;

import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        mainMenu();

        scan.close();
    }

    static void mainMenu() {
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

    static void gameRules() {
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

    static void mainGame() {
        System.out.println("===========[Hangman]===========");
        boolean keepPlaying = true;


        while (keepPlaying) {
            String word = wordRandomizer();
            String letterToChar = new String(new char[word.length()]).replace("\0", "_ "); //Convert the letters in the word to a character
            while (true) {
                System.out.println("The word is: " + letterToChar); //Print out the word as characters
                System.out.print("Enter a new letter: ");
                String guess = scan.next();
                userGuess(guess, word, letterToChar);
            }
        }
    }

    static String userGuess(String guess, String word, String letterToChar) {
        String newCharacter = "";
        int count = 0;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newCharacter += guess.charAt(0);
            } else if (letterToChar.charAt(i) != '_') {
                newCharacter += word.charAt(i);
            } else {
                newCharacter += "_";
            }
        }

        if (letterToChar.equals(newCharacter)) {
            count++;
        } else {
            letterToChar = newCharacter;
        }
        if (letterToChar.equals(word)) {
            System.out.println("You win!");
            mainMenu();
        }

        return guess + word + letterToChar;
    }

    //This method will return a random word from a String array
    static String wordRandomizer() {
        String[] wordArray = {"cow", "apple", "bicycle", "distinct", "democratic", "sticky", "elevator", "doctor", "bull", "colors"};

        Random random = new Random();
        int word = random.nextInt(wordArray.length); //This will assign a random word from the array to a new variable

        return wordArray[word]; //Return the random word
    }
}
