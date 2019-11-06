package se.lexicon.jakob;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static Scanner scan;
    public static StringBuilder sb = new StringBuilder();

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

            inputIntCheck();
            int menuChoice = Integer.parseInt(scan.nextLine());

            switch (menuChoice) {
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

            }
        }
    }

    static void gameRules()
    {
        System.out.println("Hi there and welcome to Hangman." +
                "\nWhat is Hangman, it is a guessing game, Where one player chooses a Word," +
                "\nPhrase or Sentence and then draws one line-" +
                "\n-for each letter the word contain, if there is more then one of the same letter-" +
                "\n-it will fill all the positions that contains that letter" +
                "\nThe game usually requires at least two players to be able to play a game." +
                "\nThe player that is guessing can either guess one letter at the time and try to fill the blanks-" +
                "\n-but can also try their luck and guess the whole word or sentence at once." +
                "\nIf the you guess wrong more then eight times you loose the game." +
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
                String guess = scan.nextLine();

                if (guess.length() > 1) //If statement to make sure the game uses the correct method for word or letter
                {
                    guessWord(guess, word, underscore, count);
                } else {
                    count = guessLetter(guess, word, underscore, count);
                }
            }
        }
    }

    //Main method for user guesses
    /*static int userGuess(String guess, String word, char[] underscore, int count)
    {
        boolean letterFound = false;

        for (int i = 0; i < word.length(); i++) {

            //This if statement will only accept a single character, even if you input the whole word
            if (guess.charAt(0) == word.charAt(i)) {
                underscore[i] = word.charAt(i);
                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) {
                    win(word);
                }

                letterFound = true;
            }

            //Use this to input whole word, but it will make the game crash
            else if(guess.charAt(i) == word.charAt(i))
            {
                underscore[i] = word.charAt(i);
                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) {
                    win(word);
                }
            }

            else if (guess.charAt(0) != word.charAt(i)) { //If the input is not equal to any letter in the word
                underscore[i] = underscore[i];
            }
        }

        if (!letterFound) {
            count = wrongCounter(count);
        }

        return count;
    }*/

    //This method will get the secret word and convert it to an array of characters
    static void convertWord()
    {
        String word = wordLibrary(); //New variable for the random selected word
        char[] underscore = new char[word.length()]; //get the length of the word and create array with same length
        Arrays.fill(underscore, '_'); //Fill all indexes in the underscore array with '_'
    }

    //Method will check user input with the word, if character exist then insert letter at correct position in the array
    static int guessLetter(String guess, String word, char[] underscore, int count)
    {
        boolean letterFound = false;

        for (int i = 0; i < word.length(); i++) {

            //This if statement will only accept a single character, even if you input the whole word
            if (guess.charAt(0) == word.charAt(i)) {
                underscore[i] = word.charAt(i);
                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) {
                    win(word);
                }

                letterFound = true;
            } else if (guess.charAt(0) != word.charAt(i)) { //If the input is not equal to any letter in the word
                underscore[i] = underscore[i];
            }
        }

        if (!letterFound) {
            count = wrongCounter(count);
            sb = guessedLetters(guess);
            System.out.println("UsedLetters: " + sb);
        }

        return count;
    }

    static void guessWord(String guess, String word, char[] underscore, int count)
    {
        for (int i = 0; i < word.length(); i++) {

            if (guess.charAt(i) == word.charAt(i)) {
                underscore[i] = word.charAt(i);

                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) { //If the new string matches the secret word then player wins
                    win(word);
                }
            } else if (guess.charAt(0) != word.charAt(i)) { //If the input is not equal to any letter in the word
                underscore[i] = underscore[i];
            }
        }
    }

    //This method will contain all the letters that is not needed for the chosen word.
    //It will also check if the letter has already been used.
    static StringBuilder guessedLetters(String guess)
    {
        if (sb.length() > 0) {

            for (int i = 0; i < sb.length(); i++) {
                if (guess.charAt(0) == sb.charAt(i)) {
                    break;
                } else{
                    sb.append(guess);
                }
            }
        } else {
            sb.append(guess);

        }
        return sb;
    }

    //Method for checking if the input from user is valid
    static void inputIntCheck()
    {
        while (!scan.hasNextInt()) { //Check if input is a valid choice, if not then ask user to make a new choice
            System.out.print("Invalid selection!\nSelection: ");
            scan.nextLine();
        }
    }

    //Method to tell when the player win, and exit to menu
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
        String[] wordArray = {"cow", "apple", "bicycle", "distinct",
                "democratic", "sticky", "elevator", "doctor",
                "bull", "colors", "cat", "dog", "bird", "oven", "mushroom",};

        Random random = new Random(); //Create Random variable
        int randomWord = random.nextInt(wordArray.length); //This will assign a random word from the array to a new variable

        return wordArray[randomWord]; //Return the random word selected from the array
    }
}