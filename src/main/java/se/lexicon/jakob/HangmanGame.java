package se.lexicon.jakob;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame
{
    public static Scanner scan;
    public static StringBuilder sb = new StringBuilder();
    public static int count; //Sorry had to make this global for the moment :*(

    //Method to start application
    public static void main(String[] args)
    {
        scan = new Scanner(System.in);

        mainMenu();

        scan.close();
    }

    //Method with selections for different choices in application
    static void mainMenu()
    {
        boolean gameRunning = true;

        while (gameRunning)
        {
            System.out.println("=====[Welcome to Hangman!]=====");
            System.out.println("==========[Main Menu]==========");
            System.out.print("1.Play a game\n2.Game rules\n3.Exit game\nSelection: ");

            inputIntCheck();
            int menuChoice = Integer.parseInt(scan.nextLine());

            switch (menuChoice)
            {
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

    //Only a method to explain how to play
    static void gameRules()
    {
        System.out.println("===========[RULES]============" +
                "\nThe computer will choose a random word." +
                "\nThe word will be presented as underscores, each representing a character in the word." +
                "\nThe player will have 8 guesses at the beginning." +
                "\nThe player will guess a letter they think is in the word." +
                "\nIf the word contains the chosen letter, it will be placed at the right position" +
                "\nIf the player guess wrong, they loose one guess" +
                "\nIf the player is confident enough they can guess the whole word" +
                "\n\nThis is basically how Hangman works, Good luck!\n");
    }

    //This method will keep the game running as long as the player has not guessed wrong to many times or the player wins by finding the right word
    static void mainGame()
    {
        System.out.println("===========[Hangman]===========");
        boolean keepPlaying = true;
        sb.delete(0, 20); //Reset the StringBuilder before new game
        count = 8; //Reset count before new game

        while (keepPlaying)
        {
            String word = wordLibrary().toUpperCase(); //New variable for the random selected word
            char[] underscore = new char[word.length()]; //get the length of the word and create array with same length
            Arrays.fill(underscore, '_'); //Fill all indexes in the underscore array with '_'

            while (count >= 0)
            {

                if (count <= 0)
                {
                    loose();
                }

                //System.out.println(word);
                System.out.println("Guesses left: " + count); //Show how many guesses left
                System.out.println("UsedLetters: " + sb); //Show used letter that don't work

                for (int i = 0; i < word.length(); i++) //This loop will create a space between every index in the character array
                {
                    System.out.print(underscore[i]);
                    System.out.print(" ");
                }

                System.out.print("\nLetter: ");
                String guess = scan.nextLine().toUpperCase();

                if (guess.length() > 1) //If statement to see if input is single char or a word
                {
                    guessWord(guess, word, underscore);
                } else
                {
                    guessLetter(guess, word, underscore);
                }
            }
        }
    }

    //Method will check user input with the word, if character exist then insert letter at correct position in the array
    static int guessLetter(String guess, String word, char[] underscore)
    {
        boolean letterFound = false;

        for (int i = 0; i < word.length(); i++)
        {
            //If statement to check single char input
            if (guess.charAt(0) == word.charAt(i))
            {
                underscore[i] = word.charAt(i); //Insert character in char array
                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) //Check if the new string is equal to the secret word
                {
                    win(word);
                }

                letterFound = true;
            } else if (guess.charAt(0) != word.charAt(i)) //If the input is not equal to any letter in the word, do nothing to char array
            {
                underscore[i] = underscore[i];
            }
        }

        if (!letterFound) //If letter does not exist in the word, add it to the StringBuilder
        {
            sb = guessedLetters(guess);
            //System.out.println("UsedLetters: " + sb);
        }

        return count;
    }

    static void guessWord(String guess, String word, char[] underscore)
    {
        for (int i = 0; i < guess.length(); i++) //Loop as many times as characters in input
        {
            if (guess.charAt(i) == word.charAt(i)) //Check the input and compare to the secret word
            {
                if (guess.length() != word.length()) //If input is not the same length as secret word, then the input is wrong
                {
                    System.out.println("Not the right word");
                    wrongCounter(); //Add to counter
                    break;
                } else
                {
                    underscore[i] = word.charAt(i); //Convert the input to char array
                }
                String charToString = String.copyValueOf(underscore); //Convert the array of characters to string again

                if (charToString.equals(word)) //If the new string matches the secret word then player wins
                {
                    win(word);
                }
            } else if (guess.charAt(i) != word.charAt(i)) //If the char input is not the same as secret word, then input is wrong
            {
                System.out.println("Not the right word");
                wrongCounter(); //Add to counter
                return;
            }
        }
    }

    //This method will contain all the inputs from the user.
    //It will also check if the letter has already been used, so it can't be used again.
    static StringBuilder guessedLetters(String guess)
    {
        boolean letterFound = false;

        if (sb.length() > 0) //If the StringBuilder has more then 0 letters
        {
            for (int i = 0; i < sb.length(); i++) //Keep looping as many times as the StringBuilder has characters
            {
                if (guess.charAt(0) == sb.charAt(i)) //Check if input is in StringBuilder
                {
                    letterFound = true; //Don't add new character
                }
            }

            if (!letterFound) //If the input is not in the StringBuilder
            {
                sb.append(guess); //Add the new character
                wrongCounter(); //Add to counter
            }

        } else //Add the first wrong letter in the StringBuilder
        {
            sb.append(guess); //Add the new character
            wrongCounter(); //Add to counter
        }
        return sb;
    }

    //Method for checking if the input from user is valid
    static void inputIntCheck()
    {
        while (!scan.hasNextInt())
        { //Check if input is a valid choice, if not then ask user to make a new choice
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

    //Method to count user guesses
    static void wrongCounter()
    {
        count--;
        System.out.println("_______________________________");

        return;
    }

    //This method will return a random word from a String array
    static String wordLibrary()
    {
        String[] wordArray = {"cow", "apple", "bicycle", "distinct",
                "democratic", "sticky", "elevator", "doctor",
                "bull", "colors", "cat", "dog", "bird", "oven", "mushroom",};

        Random random = new Random(); //Create Random variable
        int secretWord = random.nextInt(wordArray.length); //This will assign a random word from the array to a new variable

        return wordArray[secretWord]; //Return the random word selected from the array
    }
}