package com.dkit.gd2.leannecreedon;

import java.util.Scanner;

/**
 * References:
 *
 * Colours & Text Decoration help => https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
 * Menu structure help => from my CA1 Project
 * Researched => the Libraries Ireland online catalogue for help with understanding how online library systems work
 * Prithvi helped me => with understanding the PriorityQueue and a few other parts of the assignment that I was struggling with
 * Help with Comparator for comparing string then int => https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
 *
 *
 * CA5 - Leanne Creedon
 */
public class App 
{
    private static final Scanner keyboard = new Scanner(System.in);
    private static final Books books = new Books();
    private static final Patrons patrons = new Patrons();

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
                + "0. print menu" + "\n"
                + "1. display books (ArrayList, HashMap, TreeMap, PriorityQueue)" + "\n"
                + "2. Retrieve book by key (HashMap)" + "\n"
                + "3. Part 6 - PriorityQueueInt, Remove/Display" + "\n"
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
                System.out.print("\nEnter choice (0 = print menu) >>> ");
                selectedOption = MainMenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        printMainMenu();
                        break;
                    case DISPLAY_BOOKS:
                        books.display();
                        break;
                    case RETRIEVE_BOOK_BY_KEY:
                        retrieveBookByKey();
                        break;
                    case PART6:
                        books.priorityQueueIntRemoveDisplay();
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
        int key = getUserInputInteger("Enter key: ");

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
