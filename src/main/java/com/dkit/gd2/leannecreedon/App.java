package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * References:
 *
 * Colours & Text Decoration help => https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
 * Menu structure help => from my CA1 Project
 * Researched => the Libraries Ireland online catalogue for help with understanding how online library systems work
 * Prithvi helped me => with understanding the PriorityQueue and a few other parts of the assignment that I was struggling with
 * Help with Comparator for comparing string then int => https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
 * Niall O'Reilly helped me => with the delete book DAO method that I was struggling to get working
 * Email regex help => https://www.w3schools.blog/validate-email-regular-expression-regex-java
 * Mobile phone regex help => https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
 *
 *
 *
 * CA5 - Leanne Creedon
 */
public class App 
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();
    private static final Scanner keyboard = new Scanner(System.in);
    private static final Books books = new Books();
    private static final Patrons patrons = new Patrons();

    public static void main( String[] args )
    {
        System.out.println( "-- " + Colours.BOLD + "Library System" + Colours.RESET + " --\n");

        mainMenu();
    }

    private static void printMainMenu()
    {
        System.out.println("Temporary menu system!");
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Main Menu" + Colours.RESET + "\n"
                + "1. Part1 Menu" + "\n"
                + "2. Part2 Menu" + "\n"
                + "3. Exit");
    }

    private static void printPart1Menu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Part1 Menu" + Colours.RESET + "\n"
                + "0. print menu" + "\n"
                + "1. display books (ArrayList, HashMap, TreeMap, PriorityQueue)" + "\n"
                + "2. Retrieve book by key (HashMap)" + "\n"
                + "3. Part 6 - PriorityQueueInt, Remove/Display" + "\n"
                + "4. Back to main menu");
    }

    private static void printPart2Menu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Part2 Menu" + Colours.RESET + "\n"
                + "1. Manage Account" + "\n"
                + "2. Book Catalogue" + "\n"
                + "3. Back to main menu");
    }
    private static void printCatalogueMenu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Book Catalogue Menu" + Colours.RESET + "\n"
                + "0. Print menu" + "\n"
                + "1. Find all books" + "\n"
                + "2. Check book on system using ID" + "\n"
                + "3. Delete book by ID" + "\n"
                + "4. Add a book" + "\n"
                + "5. Filter books by publish date range" + "\n"
                + "6. Find all books (JSON)" + "\n"
                + "7. Check book on system using ID (JSON)" + "\n"
                + "8. Back to previous menu");
    }
    private static void printAccountMenu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "Account Menu" + Colours.RESET + "\n"
                + "0. Print menu" + "\n"
                + "1. Create an account" + "\n"
                + "2. Print all patron accounts" + "\n"
                + "3. Delete an account" + "\n"
                + "4. Check patron account on system using email" + "\n"
                + "5. Back to previous menu");
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
                    case PART1_MENU:
                        printPart1Menu();
                        part1Menu();
                        return;
                    case PART2_MENU:
                        printPart2Menu();
                        part2Menu();
                        return;
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

    public static void part1Menu ()
    {
        Part1MenuOptions selectedOption = Part1MenuOptions.PRINT_MENU;

        while (selectedOption != Part1MenuOptions.BACK_TO_MAIN)
        {
            try
            {
                System.out.print("\nEnter choice (0 = print menu) >>> ");
                selectedOption = Part1MenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        printPart1Menu();
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
                    case BACK_TO_MAIN:
                        mainMenu();
                        return;
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

    public static void part2Menu ()
    {
        Part2MenuOptions selectedOption = Part2MenuOptions.PRINT_MENU;

        while (selectedOption != Part2MenuOptions.BACK_TO_MAIN)
        {
            try
            {
                System.out.print("\nEnter choice  >>> ");
                selectedOption = Part2MenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case ACCOUNT_MENU:
                        printAccountMenu();
                        patronAccountMenu();
                        return;
                    case BOOKS_MENU:
                        printCatalogueMenu();
                        bookCatalogueMenu();
                        return;
                    case BACK_TO_MAIN:
                        mainMenu();
                        return;
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

    public static void bookCatalogueMenu ()
    {
        CatalogueMenuOptions selectedOption = CatalogueMenuOptions.PRINT_MENU;

        while (selectedOption != CatalogueMenuOptions.BACK_TO_PREVIOUS_MENU)
        {
            try
            {
                System.out.print("\nEnter choice (0 = print menu) >>> ");
                selectedOption = CatalogueMenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        printCatalogueMenu();
                        break;
                    case PRINT_ALL_BOOKS:
                        books.printAllBooks();
                        break;
                    case CHECK_BOOK_FOUND:
                        books.checkBookFound(searchForBook());
                        break;
                    case DELETE_BOOK_BY_ID:
                        deleteBook();
                        break;
                    case INSERT_A_BOOK:
                        insertBook();
                        break;
                    case FILTER_BY_PUBLISH_DATE:
                        filterBooksByDateRange();
                        break;
                    case FIND_ALL_BOOKS_JSON:
                        printAllBooksJson();
                        break;
                    case FIND_BOOK_BY_ID_JSON:
                        books.checkBookFoundJson(searchForBookJson());
                        break;
                    case BACK_TO_PREVIOUS_MENU:
                        printPart2Menu();
                        part2Menu();
                        return;
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

    public static void patronAccountMenu ()
    {
        PatronAccountMenuOptions selectedOption = PatronAccountMenuOptions.PRINT_MENU;

        while (selectedOption != PatronAccountMenuOptions.BACK_TO_PREVIOUS_MENU)
        {
            try
            {
                System.out.print("\nEnter choice (0 = print menu) >>> ");
                selectedOption = PatronAccountMenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        printAccountMenu();
                        break;
                    case INSERT_A_PATRON:
                        addPatron();
                        break;
                    case PRINT_ALL_PATRONS:
                        patrons.printAllPatrons();
                        break;
                    case DELETE_PATRON_BY_EMAIL:
                        deletePatron();
                        break;
                    case CHECK_PATRON_FOUND:
                        patrons.checkPatronFound(searchForPatron());
                        break;
                    case BACK_TO_PREVIOUS_MENU:
                        printPart2Menu();
                        part2Menu();
                        return;
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

    private static void printAllBooksJson()
    {
        try
        {
            books.printAllBooksJson(IBookDAO.findAllBooksJson());
        }
        catch(DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void filterBooksByDateRange()
    {
        try
        {
            List<Book> filteredList;
            System.out.println("Enter the publish date range ->");
            LocalDate fromDate = getUserInputLocalDate("FROM: ");
            keyboard.nextLine();
            LocalDate toDate = getUserInputLocalDate("TO: ");
            keyboard.nextLine();
            filteredList = IBookDAO.findBookUsingFilter(fromDate, toDate);
            books.printBookList(filteredList);
        }
       catch(DaoException dao)
       {
           System.out.println(dao.getMessage());
       }
    }

    private static void deletePatron()
    {
        String confirmation;
        try
        {
            Patron patron = searchForPatronByPin();
            if(patrons.checkPatronFound(patron))
            {
                confirmation = getUserInput(Colours.BOLD+"\nAre you sure you want to delete this account? (Yes/No) : "+Colours.RESET);
                if(confirmation.equalsIgnoreCase("Yes"))
                {
                    IPatronDAO.deletePatronByPin(patron.getPin());
                    System.out.println(Colours.GREEN + "Successfully deleted patron" + Colours.RESET);
                }
                else if(confirmation.equalsIgnoreCase("No"))
                {
                    System.out.println("-Account remains in system-");
                }
                else
                {
                    System.out.println(Colours.RED + "\nInvalid option - please try again" + Colours.RESET);
                }
            }
        }
        catch (DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static Patron searchForPatronByPin() throws DaoException {
        int patronPin = getUserInputInteger("Please enter pin number: ");
        return IPatronDAO.findPatronByPin(patronPin);
    }

    private static void addPatron()
    {
        try
        {
            System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Personal Details-\n"+Colours.RESET);
            String fName = getUserInput("Enter first name: ");
            String lName = getUserInput("Enter last name: ");
            LocalDate dob = getUserInputLocalDate("Enter your date of birth (Y/M/D): ");
            keyboard.nextLine();
            String gender = getUserInput("Enter your gender: ");
            if(!(patrons.genderConfirmation(gender))) {
                return;
            }
            System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Residential Details-\n"+Colours.RESET);
            String addressLine1 = getUserInput("Enter address line 1: ");
            String addressLine2 = getUserInput("Enter address line 2: ");
            String county = getUserInput("Enter county: ");
            String eirCode = getUserInput("Enter your eirCode: ");
            System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Contact Details-\n"+Colours.RESET);
            String email = getUserInput("Enter your email address: ");
            if(!(patrons.checkUniqueEmail(email))) {
                return;
            }
            if(!(patrons.checkEmailRegex(email))) {
                return;
            }
            String confirmEmail = getUserInput("Please confirm your email: ");
            if(!(email.equalsIgnoreCase(confirmEmail))) {
                System.out.println(Colours.RED + "Confirmation email did not match original" + Colours.RESET);
                return;
            }
            else {
                System.out.println(Colours.GREEN + "Email confirmed" + Colours.RESET);
            }
            String mobile = getUserInput("Enter your mobile number: ");
            if(!(patrons.checkMobileRegex(mobile))) {
                return;
            }
            System.out.println(Colours.GREEN+"\nAccount Successfully created"+Colours.RESET);
            System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Login Details-\n"+Colours.RESET);
            long pin = patrons.generateRandomNumber(4);
            long libraryCardNum = patrons.generateRandomNumber(14);
            Patron newPatron = new Patron(fName, lName, dob, gender, addressLine1, addressLine2, county, eirCode, email, confirmEmail, mobile, pin, libraryCardNum);
            IPatronDAO.insertAPatron(newPatron);
            System.out.println("Your Pin number is [ " + pin + " ]");
            System.out.println("Your Library card number is [ " + libraryCardNum + " ]");
            System.out.println("\nThank you for joining us!\n" +
                               "Your Library card will be mailed out to you in the coming weeks.");
            patrons.updatePatronList(newPatron);
        }
        catch (DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void insertBook()
    {
        try
        {
            String title = getUserInput("Please enter the title of the book: ");
            String author = getUserInput("Please enter the author of the book: ");
            String status = getUserInput("Please enter the status of the book: ");
            String genre = getUserInput("Please enter the genre of the book: ");
            LocalDate datePub = getUserInputLocalDate("Please enter the date published (year, month, day): ");
            keyboard.nextLine();
            String publisher = getUserInput("Please enter the publisher of the book: ");
            double rating = getUserInputDouble("Please enter the rating of the book: ");

            // Doesn't matter what I put as ID here, it will auto increment because of the database SQL connectivity
            Book newBook = new Book(0, title, author, status, genre, datePub, publisher, rating);
            IBookDAO.insertABook(newBook);
        }
        catch (DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void deleteBook()
    {
        String confirmation;
        try
        {
            Book book = searchForBook();
            if(books.checkBookFound(book))
            {
                confirmation = getUserInput(Colours.BOLD+"\nAre you sure you want to delete this book? (Yes/No) : "+Colours.RESET);

                if(confirmation.equalsIgnoreCase("Yes"))
                {
                    IBookDAO.deleteBookById(book.getId());
                    System.out.println(Colours.GREEN + "Successfully deleted book" + Colours.RESET);
                }
                else if(confirmation.equalsIgnoreCase("No"))
                {
                    System.out.println("-Book remains in system-");
                }
                else
                {
                    System.out.println(Colours.RED + "\nInvalid option - please try again" + Colours.RESET);
                }
            }
        }
        catch (DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static Book searchForBook() throws DaoException
    {
        int bookID = getUserInputInteger("Please enter book id: ");
        return IBookDAO.findBookById(bookID);
    }

    private static JSONObject searchForBookJson() throws DaoException
    {
        int bookID = getUserInputInteger("Please enter book id: ");
        return IBookDAO.findBookByIdJson(bookID);
    }

    private static Patron searchForPatron() throws DaoException
    {
        String patronEmail = getUserInput("Please enter user email: ");
        return IPatronDAO.findPatronByEmail(patronEmail);
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

    private static LocalDate getUserInputLocalDate(String message)
    {
        System.out.println(message);
        return LocalDate.parse(keyboard.next());
    }

    public static double getUserInputDouble(String message)
    {
        System.out.println(message);
        return Double.parseDouble(keyboard.nextLine());
    }

    private static int getUserInputInteger(String message)
    {
        System.out.println(message);
        return Integer.parseInt(keyboard.nextLine());
    }

}
