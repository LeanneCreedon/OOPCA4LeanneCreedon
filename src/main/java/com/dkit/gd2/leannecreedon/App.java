package com.dkit.gd2.leannecreedon;

import java.util.Scanner;

/**
 * References:
 *
 * Colours & Text Decoration help => https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
 * Menu structure help => from my CA1 Project
 * Researched => the Libraries Ireland online catalogue for help with understanding how online library systems work
 *
 * CA3 - Leanne Creedon
 */
public class App 
{
    private static final Scanner keyboard = new Scanner(System.in);
    private static final Books books = new Books();

    public static void main( String[] args )
    {
        System.out.println( "-- " + Colours.BOLD + "Library System" + Colours.RESET + " --\n");
        books.add10Books();
        mainMenu();
    }

    private static void printMainMenu()
    {
        System.out.println("Temporary menu system!");
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Main Menu" + Colours.RESET + "\n"
                + "0. print out all books(ArrayList,HashMap,TreeMap)" + "\n"
                + "1. Retrieve book by key(HashMap)" + "\n"
                + "2. display books in TreeMap"+ "\n"
                + "3. display books in Alphabetical order(PriorityQueue)"+ "\n"
                + "4. Exit");
    }

    public static void mainMenu ()
    {
        MainMenuOptions selectedOption = MainMenuOptions.PRINT_MENU;
        printMainMenu();

        while (selectedOption != MainMenuOptions.QUIT)
        {
            try
            {
                System.out.print("\nEnter choice >>> ");
                selectedOption = MainMenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        books.printAllBooks();
                        break;
                    case RETRIEVE_BOOK_BY_KEY:
                        retrieveBookByKey();
                        break;
                    case DISPLAY_BOOKS_TREE_MAP:
                        books.displayBooksFromTreeMap();
                        break;
                    case PRINT_BOOKS_ORDER:
                        books.printAlphabeticalOrder();
                        break;
                    case QUIT:
                        System.out.println(Colours.BLUE + "Shutting down the system....." + Colours.RESET);
                        break;
                    default:
                        throw new Exception("Shouting message");
                }
            }
            catch (Exception e)
            {
                System.out.println(Colours.RED + "\nPlease enter valid option" + Colours.RESET);
            }
        }
    }

    private static void retrieveBookByKey()
    {
        String key = getUserInput("Enter key: ");

        if(!(books.findBookUsingKey(key)))
        {
            System.out.println(Colours.RED + "Book under key " + key + " not found." + Colours.RESET);
        }

    }

    // Get user input

    private static String getUserInput(String message)
    {
        System.out.println(message);
        return keyboard.nextLine();
    }

    private static int getUserInputInteger(String message)
    {
        System.out.println(message);
        return Integer.parseInt(keyboard.nextLine());
    }

}
