package com.dkit.gd2.leannecreedon.client;

import com.dkit.gd2.leannecreedon.*;
import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;
import com.dkit.gd2.leannecreedon.core.Protocol;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.dkit.gd2.leannecreedon.core.Details.SERVER_PORT;

/**
 * References:
 *
 * Colours & Text Decoration help => https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
 * Menu structure help => from my CA1 Project
 * Researched => the Libraries Ireland online catalogue for help with understanding how online library systems work
 * Prithvi helped me => with understanding the PriorityQueue and a few other parts of the assignment that I was struggling with
 * Help with Comparator for comparing string then int => https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
 * Niall O'Reilly helped me => with the delete book DAO method that I was struggling to get working. And with the Client server integration.
 * Email regex help => https://www.w3schools.blog/validate-email-regular-expression-regex-java
 * Mobile phone regex help => https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
 * Random number generation help => https://stackoverflow.com/questions/37216645/generate-a-random-integer-with-a-specified-number-of-digits-java
 * Name regex => https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters
 * No characters regex => https://stackoverflow.com/questions/47239276/regex-to-allow-only-numbers-alphabets-spaces-and-hyphens-java
 *
 *
 * CA4 Project - Leanne Creedon
 */
public class ClientApp
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    private static final Scanner keyboard = new Scanner(System.in);
    private static final Books books = new Books();
    private static final Patrons patrons = new Patrons();
    private static final Gson gson = new Gson();
    private static boolean validMenuChoice=true;
    private static boolean loggedIn=false;
    private static String currentAccount;
    private static boolean needNewLine=false;

    public static void main( String[] args )
    {
        System.out.println( "-- " + Colours.BOLD + "Library System" + Colours.RESET + " --\n");
        mainMenu();
    }

    /* Menu systems */

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
                    case PART1_MENU:
                        printPart1Menu();
                        part1Menu();
                        return;
                    case PART2_MENU:
                        logInMenu();
                        return;
                    case QUIT:
                        System.out.println(Colours.BLUE+"\nThank you for using the Library Service, have a nice day!"+Colours.RESET);
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

    public static void logInMenu ()
    {
        SignInMenuOptions selectedOption = SignInMenuOptions.PRINT_MENU;
        printLogInMenu();
        while (selectedOption != SignInMenuOptions.PREVIOUS_PAGE)
        {
            try
            {
                System.out.print("\nEnter choice (0 = print menu) >>> ");
                selectedOption = SignInMenuOptions.values()[Integer.parseInt(keyboard.nextLine().trim())];

                switch (selectedOption)
                {
                    case PRINT_MENU:
                        printLogInMenu();
                        break;
                    case LOG_IN:
                        if(logInToSystem()) {
                            loggedIn=true;
                            clientMenu();
                            return;
                        }
                        else {
                            break;
                        }
                    case CONTINUE_AS_GUEST:
                        System.out.println("Welcome, you may proceed as a guest user\n");
                        clientMenu();
                        return;
                    case PREVIOUS_PAGE:
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

    private static boolean logInToSystem() {

        String lName = getUserInput("Please enter last name: ");
        if (patrons.searchByLastName(lName)) {
            System.out.println(Colours.GREEN+"Valid"+Colours.RESET);
        }
        else {
            System.out.println(Colours.RED+"No account on system with last name " + lName + Colours.RESET);
            System.out.println();
            printLogInMenu();
            return false;
        }

        String fName = getUserInputNotNumbers("Please enter first name: ");
        if (patrons.searchByFirstName(fName)) {
            System.out.println(Colours.GREEN+"Valid"+Colours.RESET);
        }
        else {
            System.out.println(Colours.RED+"No "+lName+" on system with first name " + fName + Colours.RESET);
            System.out.println();
            printLogInMenu();
            return false;
        }

        long lCardNum = getUserInputLibraryCardNum("Please enter library card number: ");
        if(lCardNum==0) {
            return false;
        }
        if(patrons.searchByLCardNum(lCardNum, fName, lName)) {
            System.out.println(Colours.GREEN+"Valid card number"+Colours.RESET);
        }
        else{
            System.out.println(Colours.RED+"Library card number [ " + lCardNum + " ] doesn't match account"+Colours.RESET);
            System.out.println();
            printLogInMenu();
            return false;
        }

        long pin = getUserInputPin("Please enter pin number: ");
        if(pin==0) {
            return false;
        }
        if(patrons.searchByPinNum(pin, lCardNum))
        {
            System.out.println(Colours.GREEN+"Valid pin"+Colours.RESET);
        }
        else{
            System.out.println(Colours.RED+"Pin number [ " + pin + " ] doesn't match account"+Colours.RESET);
            System.out.println();
            printLogInMenu();
            return false;
        }
        currentAccount = patrons.getCurrentAccountEmail(lCardNum, pin);
        return patrons.login(lName, fName, lCardNum, pin);
    }


    //CLIENT MENU
    private static void clientMenu()
    {
        try
        {
            printClientMenu();
            //Step 1: Establish a connection with the server
            Socket dataSocket = new Socket("localhost", SERVER_PORT);
            //Step 2: Build input and output streams linked to the socket
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
            InputStream in = dataSocket.getInputStream();
            //An example of the Decorator design pattern
            Scanner input = new Scanner(new InputStreamReader(in));
            //Step 3: Get the input from the user
            Scanner keyboard = new Scanner(System.in);
            Protocol messageType = Protocol.NONE;
            String message = "";
            //Step 4: Depending on the input build a message, send it
            // to the server and wait for a response

            while(!message.equals(Protocol.END.name()))
            {
                System.out.print("\nEnter choice (1 = print menu) >>> ");
                Protocol choice = getChoice(keyboard);
                Packet outgoingPacket = new Packet(choice, message);
                Packet responsePacket = new Packet(Protocol.NONE, null);
                String response = "Unrecognised";
                switch(choice)
                {
                    case PRINT_MENU:
                        printClientMenu();
                        response="Printing client menu...";
                        break;
                    case DISPLAY_BOOK_BY_ID:
                        response="Displaying book by ID...";
                        if(!loggedIn) {
                            System.out.println("Cannot access database as Guest user");
                            break;
                        }

                        int idToSearch = getUserInputInteger("Please enter id: ");
                        if(idToSearch == 0) {
                            break;
                        }

                        String bookToSearch = String.valueOf(idToSearch);

                        //Send the message
                        outgoingPacket.setPayload(bookToSearch);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));
                        Book book = gson.fromJson(responsePacket.getPayload(), Book.class);
                        books.checkBookFound(book);

                        break;
                    case DISPLAY_ALL_BOOKS:
                        response="Displaying all books...";
                        if(!loggedIn) {
                            System.out.println("Cannot access database as Guest user");
                            break;
                        }

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));

                        Type ArrayList = new TypeToken<ArrayList<Book>>(){}.getType();
                        List<Book> listOfBooks = books.setBookList(gson.fromJson(responsePacket.getPayload(), ArrayList));

                        books.displayBooks(listOfBooks);
                        break;
                    case FILTER_BY_PUBLISH_DATE:
                        response="Filtering books by publish date...";
                        //Need to set new line to true in case user chooses filter by author
                        //As this would cause an un-wanted error. Must create new line!
                        needNewLine=true;
                        if(!loggedIn) {
                            System.out.println("Cannot access database as Guest user");
                            break;
                        }
                        System.out.println("Enter the publish date range ->");
                        LocalDate fromDate = getUserInputLocalDate("FROM: ");
                        if(fromDate==null) {
                            break;
                        }
                        LocalDate toDate = getUserInputLocalDate("TO: ");
                        if(toDate==null) {
                            break;
                        }
                        if(fromDate.isAfter(toDate)) {
                            System.out.println(Colours.RED+"Invalid date range - please try again"+Colours.RESET);
                            break;
                        }

                        String dates = fromDate+"&&"+toDate;

                        outgoingPacket.setPayload(dates);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));

                        Type ArrayListFilter = new TypeToken<ArrayList<Book>>(){}.getType();
                        List<Book> FilteredListOfBooks = books.setBookList(gson.fromJson(responsePacket.getPayload(), ArrayListFilter));

                        books.displayBooks(FilteredListOfBooks);

                        break;
                    case FILTER_BY_AUTHOR:
                        response="Filtering books by author...";
                        if(!loggedIn) {
                            System.out.println("Cannot access database as Guest user");
                            break;
                        }

                        String author = getUserInputNotNumbers("Please enter author: ");
                        if(author==null) {
                            break;
                        }

                        //Didn't work. Allowed first author but if I tried again and used a valid author
                        //it would return false. Couldn't get it working in time so left out this check.

//                        if(!(books.checkAuthorOnSystem(author))) {
//                            System.out.println(Colours.RED+"Author "+author+" not on system"+Colours.RESET);
//                            break;
//                        }

                        outgoingPacket.setPayload(author);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));

                        Type ArrayListAuthorFilter = new TypeToken<ArrayList<Book>>(){}.getType();
                        List<Book> FilteredListOfBooksByAuthor = books.setBookList(gson.fromJson(responsePacket.getPayload(), ArrayListAuthorFilter));

                        books.displayBooks(FilteredListOfBooksByAuthor);

                        break;
                    case ADD_A_PATRON:
                        response="Adding Patron...";
                        if(loggedIn) {
                            System.out.println("-Already have an account-");
                            break;
                        }
                        String patronToAdd = addPatron();

                        if(patronToAdd==null) {
                            break;
                        }

                        //Send the message
                        outgoingPacket.setPayload(patronToAdd);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));
                        Gson gsonParser = Converters.registerLocalDate(new GsonBuilder()).create();
                        Patron patron = gsonParser.fromJson(responsePacket.getPayload(), Patron.class);
                        patrons.updatePatronList(patron);
                        patrons.checkPatronAdded(patron);

                        break;
                    case DELETE_A_PATRON:
                        response="Deleting Patron...";
                        if(!loggedIn) {
                            System.out.println(Colours.RED+"Must login to delete your account"+Colours.RESET);
                            break;
                        }

                        String emailAddr = getUserInput("Please enter email: ");

                        if(!(patrons.searchEmailOnSystem(emailAddr))) {
                            System.out.println(Colours.RED+"Email not on system"+Colours.RESET);
                            break;
                        }
                        if(!(emailAddr.equalsIgnoreCase(currentAccount))) {
                            System.out.println(Colours.RED+"Cannot delete other users information!"+Colours.RESET);
                            break;
                        }
                        String deleteEmailCheck = deletePatronByEmail(emailAddr);
                        if(deleteEmailCheck==null) {
                            break;
                        }
                        if(deleteEmailCheck.equals("no")) {
                            break;
                        }

                        //Logging user out of the system as their account is being deleted
                        //so they are no longer logged in
                        loggedIn=false;
                        currentAccount=null;

                        //Removing their account from the array list
                        patrons.updatePatronListDelete(emailAddr);

                        //Send the message
                        outgoingPacket.setPayload(emailAddr);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));
                        System.out.println(responsePacket.getPayload());

                        break;
                    case CHECK_ACCOUNT_INFO:
                        response="Checking account information...";
                        if(!loggedIn) {
                            System.out.println("-Logged in as Guest user-");
                            break;
                        }
                        String email = getUserInput("Please enter email: ");
                        if(!(patrons.searchEmailOnSystem(email))) {
                            System.out.println(Colours.RED+"Email not on system"+Colours.RESET);
                            break;
                        }
                        if(!(email.equalsIgnoreCase(currentAccount))) {
                            System.out.println(Colours.RED+"Cannot check other users information!"+Colours.RESET);
                            break;
                        }

                        //Send the message
                        outgoingPacket.setPayload(email);

                        output.println(outgoingPacket.writeJSON());
                        output.flush();

                        //get the response
                        responsePacket.readFromJSON(new JSONObject((input).nextLine()));
                        Patron patronToCheck = gson.fromJson(responsePacket.getPayload(), Patron.class);
                        patrons.checkPatronFound(patronToCheck);

                        break;
                    case PREVIOUS_PAGE:
                        if(loggedIn) {
                            System.out.println("Logging out...\n");
                            currentAccount=null;
                            loggedIn=false;
                        }
                        logInMenu();
                        return;
                    case END:
                        response="Ending application...";
                        if(loggedIn) {
                            System.out.println("Logging out...");
                        }
                        message = Protocol.END.name();
                }
                if(response.equals("Unrecognised"))
                {
                    validMenuChoice=false;
                }
            }
            System.out.println(Colours.BLUE+"\nThank you for using the Library Service, have a nice day!"+Colours.RESET);
            dataSocket.close();
        }
        catch (NoSuchElementException | IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static Protocol getChoice(Scanner keyboard)
    {
        Protocol choice = Protocol.END;
        boolean validChoice = false;
        while(!validChoice)
        {
            try
            {
                int input = keyboard.nextInt();
                choice = Protocol.values()[input];
                if(validMenuChoice && choice != Protocol.NONE)
                {
                    validChoice = true;
                }
                else {
                    throw new InputMismatchException("Shouting message");
                }
            }
            catch (InputMismatchException | ArrayIndexOutOfBoundsException ime)
            {
                System.out.println(Colours.RED+"\nPlease choose an option from the menu"+Colours.RESET);
                System.out.println();
                clientMenu();
            } finally {
                keyboard.nextLine();
            }
        }
        return choice;
    }


    /* Print menu methods */

    private static void printMainMenu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "-Main Menu-" + Colours.RESET + "\n"
                + "0. Print Menu" + "\n"
                + "1. Part1 Menu" + "\n"
                + "2. Part2 Menu" + "\n"
                + "3. Exit");
    }

    private static void printPart1Menu()
    {
        System.out.println(Colours.YELLOW + Colours.BOLD + Colours.UNDERLINE + "-Part1 Menu-" + Colours.RESET + "\n"
                + "0. print menu" + "\n"
                + "1. display books (ArrayList, HashMap, TreeMap, PriorityQueue)" + "\n"
                + "2. Retrieve book by key (HashMap)" + "\n"
                + "3. Part 6 - PriorityQueueInt, Remove/Display" + "\n"
                + "4. Back to main menu");
    }

    private static void printLogInMenu()
    {
        System.out.println(
                Colours.YELLOW+Colours.BOLD+Colours.UNDERLINE+
                        "-Sign in-\n"+ Colours.RESET+
                        "0. Print menu\n" +
                        "1. Log in to system\n" +
                        "2. Continue as guest\n" +
                        "3. Previous page");
    }

    private static void printClientMenu()
    {
        System.out.println(
                Colours.YELLOW+Colours.BOLD+Colours.UNDERLINE+
                        "-Client Menu-\n"+ Colours.RESET+
                        "1. Print menu\n" +
                        Colours.PURPLE+"\n--- Catalogue Options ---\n"+Colours.RESET+
                        "2. Display book by Id\n" +
                        "3. Display all books\n" +
                        "4. Filter by publish date\n" +
                        "5. Filter by Author\n" +
                        Colours.PURPLE+"\n--- Account Options ---\n"+Colours.RESET+
                        "6. Create an account\n" +
                        "7. Delete your account\n" +
                        "8. Check account information\n" +
                        Colours.PURPLE+"-----------------------\n"+Colours.RESET+
                        "9. Back to Sign in page\n" +
                        "0. Exit");
    }

    /* Delete patron by email double check method */
    private static String deletePatronByEmail(String emailToDelete)
    {
        String confirmation;
        if(patrons.checkPatronFoundByEmail(emailToDelete))
        {
            confirmation = getUserInput(Colours.BOLD+"\nAre you sure you want to delete this account? (Yes/No) : "+Colours.RESET);
            if(confirmation.equalsIgnoreCase("Yes"))
            {
                System.out.println("Deleting patron from database...");
                return emailToDelete;
            }
            else if(confirmation.equalsIgnoreCase("No"))
            {
                System.out.println("-Account remains in system-");
            }
            else
            {
                System.out.println(Colours.RED + "\nInvalid input - please try again" + Colours.RESET);
                return null;
            }
        }
        return "no";
    }

    /* Adding patron to database validation */
    private static String addPatron()
    {
        System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Personal Details-\n"+Colours.RESET);
        String fName = getUserInput("Enter first name: ");
        if(!(patrons.checkNameRegex(fName))) {
            System.out.println(Colours.RED+"Invalid input - please use alphabetical characters"+Colours.RESET);
            return null;
        }
        String lName = getUserInput("Enter last name: ");
        if(!(patrons.checkNameRegex(lName))) {
            System.out.println(Colours.RED+"Invalid input - please use alphabetical characters"+Colours.RESET);
            return null;
        }
        LocalDate dob = getUserInputLocalDate("Enter your date of birth (Y/M/D): ");
        keyboard.nextLine();
        if(dob==null) {
            return null;
        }
        String gender = getUserInputLettersOnly("Enter your gender: ");
        if(gender==null) {
            return null;
        }
        if(!(patrons.genderConfirmation(gender))) {
            return null;
        }
        System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Residential Details-\n"+Colours.RESET);
        String addressLine1 = getUserInputNotChar("Enter address line 1: ");
        if(addressLine1==null) {
            return null;
        }
        String addressLine2 = getUserInputNotChar("Enter address line 2: ");
        if(addressLine2==null) {
            return null;
        }
        String county = getUserInput("Enter county: ");
        if(!patrons.countyValidation(county)) {
            return null;
        }
        String eirCode = getUserInputNotChar("Enter your eirCode: ");
        if(eirCode==null) {
            return null;
        }
        System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Contact Details-\n"+Colours.RESET);
        String email = getUserInput("Enter your email address: ");
        if(!(patrons.checkUniqueEmail(email))) {
            return null;
        }
        if(!(patrons.checkEmailRegex(email))) {
            return null;
        }
        String confirmEmail = getUserInput("Please confirm your email: ");
        if(!(email.equalsIgnoreCase(confirmEmail))) {
            System.out.println(Colours.RED + "Confirmation email did not match original" + Colours.RESET);
            return null;
        }
        String mobile = getUserInput("Enter your mobile number: ");
        if(!(patrons.checkMobileRegex(mobile))) {
            return null;
        }
        System.out.println(Colours.BOLD+Colours.UNDERLINE+"\n-Login Details-\n"+Colours.RESET);
        long pin = patrons.generateRandomNumber(4);
        long libraryCardNum = patrons.generateRandomNumber(14);
        Patron newPatron = new Patron(fName, lName, dob, gender, addressLine1, addressLine2,
                county, eirCode, email, confirmEmail, mobile, pin, libraryCardNum);
        System.out.println("Your Pin number is [ " + pin + " ]");
        System.out.println("Your Library card number is [ " + libraryCardNum + " ]");
        System.out.println("\nThank you for joining us!\n" +
                           "Your Library card will be mailed out to you in the coming weeks.");
        return newPatron.toString();
    }

    // For handy addition of books to my database, but users won't have access
    // to this functionality.
    private static void insertBook()
    {
        try
        {
            String title = getUserInputNotNumbers("Please enter the title of the book: ");
            String author = getUserInputNotNumbers("Please enter the author of the book: ");
            String status = getUserInputNotNumbers("Please enter the status of the book: ");
            String genre = getUserInputNotNumbers("Please enter the genre of the book: ");
            LocalDate datePub = getUserInputLocalDate("Please enter the date published (year, month, day): ");
            keyboard.nextLine();
            String publisher = getUserInputNotNumbers("Please enter the publisher of the book: ");
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

    // Same as above, here in case I want to delete a book.
    private static void deleteBook()
    {
        String confirmation;
        try
        {
            Book book = searchForBook();
            if(books.checkBookFound(book))
            {
                confirmation = getUserInputNotNumbers(Colours.BOLD+"\nAre you sure you want to delete this book? (Yes/No) : "+Colours.RESET);

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

    /* Search Methods */

    // not part of new client-server system structure
    public static Book searchForBook() throws DaoException
    {
        int bookID = getUserInputInteger("Please enter book id: ");
        return IBookDAO.findBookById(bookID);
    }

    //method for part1
    private static void retrieveBookByKey()
    {
        int key = getUserInputInteger("Enter key: ");
        if(!(books.findBookUsingKey(key)) && key!=0)
        {
            System.out.println(Colours.RED + "Book under key " + key + " not found." + Colours.RESET);
        }
    }

    /* Getting user input */

    public static String getUserInput(String message)
    {
        System.out.println(message);
        return keyboard.nextLine();
    }

    //Allows Letters and Characters
    private static String getUserInputNotNumbers(String message)
    {
        System.out.println(message);
        if(needNewLine) {
            keyboard.nextLine();
            needNewLine=false;
        }
        String input = keyboard.nextLine();
        try
        {
            for(int i=0; i < input.length(); i++)
            {
                boolean flag = Character.isDigit(input.charAt(i));
                if(flag) {
                    System.out.println(Colours.RED+"Invalid input - Please enter alphabetical characters"+Colours.RESET);
                    input=null;
                }
            }
        }
        catch(NullPointerException npe)
        {
            input=null;
        }
        return input;
    }

    //Allows only Letters
    private static String getUserInputLettersOnly(String message)
    {
        System.out.println(message);
        String input = keyboard.nextLine();
        for(int i = 0; i < input.length(); i++)
        {
            if (!Character.isLetter(input.charAt(i)))
            {
                System.out.println(Colours.RED+"Invalid input - please enter alphabetical character only"+Colours.RESET);
                return null;
            }
        }
        return input;
    }

    //Allows Letters and Numbers
    private static String getUserInputNotChar(String message)
    {
        System.out.println(message);
        String input = keyboard.nextLine();
        String regex = "^[0-9A-Za-z\\s-]+$";

        if (!(input.matches(regex)))
        {
            System.out.println(Colours.RED+"Invalid input - please enter correct format"+Colours.RESET);
            input=null;
        }
        return input;
    }

    private static LocalDate getUserInputLocalDate(String message)
    {
        LocalDate date;
        System.out.println(message);
        try
        {
            date = LocalDate.parse(keyboard.next());
        }
        catch(DateTimeParseException dtpe)
        {
            System.out.println(Colours.RED+"Please enter correct format (yyyy/mm/dd)"+Colours.RESET);
            return null;
        }
        return date;
    }

    public static double getUserInputDouble(String message)
    {
        System.out.println(message);
        return Double.parseDouble(keyboard.nextLine());
    }

    //Allows only numbers
    private static int getUserInputInteger(String message)
    {
        System.out.println(message);
        int num=0;
        try
        {
            num = Integer.parseInt(keyboard.nextLine());
        }
        catch(NumberFormatException e)
        {
            //If number is not integer,you wil get exception and exception message will be printed
            System.out.println(Colours.RED+"Invalid input - please enter a number"+Colours.RESET);
        }
        return num;
    }

    private static long getUserInputLibraryCardNum(String message)
    {
        System.out.println(message);
        long input=0;
        try
        {
            input = Long.parseLong(keyboard.nextLine());
        }
        catch (NumberFormatException e)
        {
            System.out.println(Colours.RED+"Invalid input - please enter your 14 digit library card number"+Colours.RESET);
        }
        return input;
    }

    private static long getUserInputPin(String message)
    {
        System.out.println(message);
        long input=0;
        try
        {
            input = Long.parseLong(keyboard.nextLine());
        }
        catch (NumberFormatException e)
        {
            System.out.println(Colours.RED+"Invalid input - please enter your 4 digit pin number"+Colours.RESET);
        }
        return input;
    }
}
