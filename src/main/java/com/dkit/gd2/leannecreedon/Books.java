package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.util.*;

public class Books
{
    /* Manager class for books */

    private ArrayList<Book> bookList;
    private final HashMap<Integer, Book> bookHashMap;
    private final TreeMap<Integer, Book> bookTreeMap;
    private final PriorityQueue<Book> bookPriorityQueue;
    private final PriorityQueue<Integer> bookPriorityQueueInt;

    public Books()
    {
        this.bookList = new ArrayList<>();
        this.bookHashMap = new HashMap<>();
        this.bookTreeMap = new TreeMap<>();
        this.bookPriorityQueue = new PriorityQueue<>(new OrderBooksByTitleComparator());
        this.bookPriorityQueueInt = new PriorityQueue<>();
        connectToBookDatabase();
    }

    public List<Book> setBookList(ArrayList<Book> bookList)
    {
        this.bookList = bookList;
        return bookList;
    }

    /* Part2 & Part3 of spec methods */

    public void connectToBookDatabase()
    {
        IBookInterface IBookDAO = new MySqlBookDAO(); // Easy to switch for Mongo!

        try
        {
            List<Book> books = IBookDAO.findAllBooks();

            //Adding books from database to all datastructures in my system
            //for part1 of spec
            bookList.addAll(books);
            bookPriorityQueue.addAll(bookList);
            for(Book book : bookList) {
                bookHashMap.put(book.getId(), book);
            }
            for(Book book : bookList) {
                bookTreeMap.put(book.getId(), book);
            }

            if(books.isEmpty())
            {
                System.out.println(Colours.RED + "There are no books" + Colours.RESET);
            }

        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }

    //Part1 of spec method
    public void priorityQueueIntRemoveDisplay()
    {
        // Part 6
        // PriorityQueue integer
        int thirdPriority1 = bookList.get(4).getDatePublished().getMonthValue();
        int thirdPriority2 = bookList.get(4).getDatePublished().getMonthValue();

        int secondPriority1 = bookList.get(2).getDatePublished().getMonthValue();
        int secondPriority2 = bookList.get(2).getDatePublished().getMonthValue();

        int topPriority = bookList.get(7).getDatePublished().getMonthValue();

        // Adding elements to PriorityQueue
        bookPriorityQueueInt.add(thirdPriority1);
        bookPriorityQueueInt.add(thirdPriority2);

        bookPriorityQueueInt.add(secondPriority1);
        bookPriorityQueueInt.add(secondPriority2);

        System.out.println("Removing and displaying one element");

        // Remove and display one element
        System.out.println("Item to remove " + thirdPriority1 + ", Remove success = " + bookPriorityQueueInt.remove(thirdPriority1));

        // Adding one top priority element
        bookPriorityQueueInt.add(topPriority);

        System.out.println("Removing and displaying all elements in top priority order using book month of publish priority queue");

        // Remove and display all elements in top priority order
        while(!bookPriorityQueueInt.isEmpty())
        {
            System.out.println(bookPriorityQueueInt.poll());
        }

        System.out.println("Displaying all items in priority queue " + bookPriorityQueueInt);
    }

    /* Search Methods */

    public boolean findBookUsingKey(int userKey)
    {
        for(int key : bookHashMap.keySet())
        {
            if(key == userKey)
            {
                System.out.println(key + " : " + bookHashMap.get(key));
                return true;
            }
        }
        return false;
    }

    public boolean checkBookFound(Book book)
    {
        if(book != null)
        {
            System.out.println(Colours.GREEN + "Book found: "  + Colours.RESET);
            displayBook(book);
            return true;
        }
        else
        {
            System.out.println(Colours.RED + "No book with that id found" + Colours.RESET);
            return false;
        }
    }

    public boolean checkAuthorOnSystem(String author)
    {
        for(Book book : bookList)
        {
            if(book.getAuthor().equalsIgnoreCase(author)) {
                return true;
            }
        }
        return false;
    }

    /* Display Methods */

    public boolean printBookList(List<Book> books)
    {
        for(Book book : books)
        {
            System.out.println(book);
        }
        return true;
    }

    private void displayByTitleThenYear(PriorityQueue<Book> books)
    {
        System.out.println("\n----- PriorityQueue -----\n");

        // Testing the ordering of the comparator
        Book charlotteWeb2 = new Book(11, "Charlotte's Web", "E.B White",
                "DUE 02-04-22", "Children's", 2001, 10, 15, "HarperCollins", 4.18);

        bookPriorityQueue.add(charlotteWeb2);

        while(!books.isEmpty())
        {
            System.out.println(books.poll());
        }
    }

    public void displayArrayList(List<Book> books)
    {
        System.out.println();
        if(!(books.isEmpty()))
        {
            System.out.printf(Colours.BOLD+"%-8s%-50s%-25s%-20s%-20s%-20s%-35s%s%n", "ID", "Title", "Author",
                    "Status", "Genre", "Date_Published", "Publisher", "Rating" + Colours.RESET);

            for (Book book : bookList) {
                System.out.printf("%-8s%-50s%-25s%-20s%-20s%-20s%-35s%s%n", book.getId(), book.getTitle(), book.getAuthor(),
                        book.getStatus(), book.getGenre(), book.getDatePublished(), book.getPublisher(), book.getRating());
            }
        }
        else
        {
            System.out.println(Colours.RED + "No Books in the system." + Colours.RESET);
        }
    }

    public void displayBooks(List<Book> books)
    {
        System.out.println();

        //Getting the max length for each entity so that the table displays neatly
        //regardless of the length of the entries.
        int maxTitleLength = 0;
        int maxAuthorLength = 0;
        int maxStatusLength = 0;
        int maxGenreLength = 0;
        int maxPublisherLength = 0;

        for(Book book : books) {
            if (book.getTitle().length() > maxTitleLength) {
                maxTitleLength = book.getTitle().length();
            }
            if (book.getAuthor().length() > maxAuthorLength) {
                maxAuthorLength = book.getAuthor().length();
            }
            if (book.getStatus().length() > maxStatusLength) {
                maxStatusLength = book.getStatus().length();
            }
            if (book.getGenre().length() > maxGenreLength) {
                maxGenreLength = book.getGenre().length();
            }
            if (book.getPublisher().length() > maxPublisherLength) {
                maxPublisherLength = book.getPublisher().length();
            }
        }
        //Printing headings
        System.out.printf(Colours.BOLD+"%-5s%-"+ //ID
                        (maxTitleLength+5)+"s%-"
                        +(maxAuthorLength+5)+"s%-"
                        +(maxStatusLength+5)+"s%-"
                        +(maxGenreLength+5)+"s%-"
                        +"20s%-" //Publish date
                        +(maxPublisherLength+5)+"s%s%n",

                "ID", "Title", "Author",
                "Status", "Genre", "Date_Published", "Publisher", "Rating" + Colours.RESET);
        //Displaying books
        for(Book book : books)
        {
            System.out.printf("%-5s%-"+ //ID
                            (maxTitleLength+5)+"s%-"
                            +(maxAuthorLength+5)+"s%-"
                            +(maxStatusLength+5)+"s%-"
                            +(maxGenreLength+5)+"s%-"
                            +"20s%-" //Publish date
                            +(maxPublisherLength+5)+"s%s%n"

                    , book.getId(), book.getTitle(), book.getAuthor(),
                    book.getStatus(), book.getGenre(), book.getDatePublished(), book.getPublisher(), book.getRating());
        }
    }

    public void displayBook(Book book)
    {
        System.out.println();
        System.out.printf(Colours.BOLD+"%-5s%-"+ //ID
                        (book.getTitle().length()+5)+"s%-"
                        +(book.getAuthor().length()+5)+"s%-"
                        +(book.getStatus().length()+5)+"s%-"
                        +(book.getGenre().length()+5)+"s%-"
                        +"20s%-" //Publish date
                        +(book.getPublisher().length()+5)+"s%s%n",

                        "ID", "Title", "Author",
                        "Status", "Genre", "Date_Published", "Publisher", "Rating" + Colours.RESET);

        System.out.printf("%-5s%-"+ //ID
                        (book.getTitle().length()+5)+"s%-"
                        +(book.getAuthor().length()+5)+"s%-"
                        +(book.getStatus().length()+5)+"s%-"
                        +(book.getGenre().length()+5)+"s%-"
                        +"20s%-" //Publish date
                        +(book.getPublisher().length()+5)+"s%s%n"

                , book.getId(), book.getTitle(), book.getAuthor(),
                book.getStatus(), book.getGenre(), book.getDatePublished(), book.getPublisher(), book.getRating());
    }
    
    private void displayHashMap(HashMap<Integer, Book> books)
    {
        System.out.println("\n--- HashMap ---\n");
        for(int key : books.keySet())
        {
            System.out.println(key + " : " + books.get(key));
        }
    }

    private void displayTreeMap(TreeMap<Integer, Book> books)
    {
        System.out.println("\n--- TreeMap ---\n");
        for(int key : books.keySet())
        {
            System.out.println(key + " : " + books.get(key));
        }
    }

    // Display method to print out all books using each different
    // Interface type
    public void display()
    {
        displayArrayList(bookList);
        displayHashMap(bookHashMap);
        displayTreeMap(bookTreeMap);
        displayByTitleThenYear(bookPriorityQueue);
    }

}
