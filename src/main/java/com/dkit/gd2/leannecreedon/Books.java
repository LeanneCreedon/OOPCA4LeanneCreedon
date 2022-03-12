package com.dkit.gd2.leannecreedon;

import java.util.*;

public class Books
{
    /* Manager class for books */

    private final ArrayList<Book> bookList;
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
    }

    public void add10Books()
    {
        // Creating 10 Book objects
        Book harryPotter = new Book(1, "Harry Potter and the Chamber of Secrets", "J.K Rolling",
                "On-Shelf", "Fantasy", 1999, 6,2, "Arthur A. Levine", 4.43);

        Book hungerGames = new Book(2, "The Hunger Games Catching Fire", "Suzanne Collins",
                "On-Shelf", "Dystopian", 2009, 9, 1, "Scholastic Press", 4.30);

        Book divergent = new Book(3, "Divergent", "Veronica Roth",
                "DUE 04-03-22", "Dystopian", 2012, 2, 8, "Katherine Tegen Books", 4.17);

        Book faultInOurStars = new Book(4, "The Fault in our stars", "John Green",
                "On-Shelf", "Realistic novel", 2012, 1, 10, "Dutton Books", 4.17);

        Book twilight = new Book(5, "Twilight", "Stephenie Meyer",
                "On-Shelf", "Romance", 2006, 9, 6, "Little, Brown and Company", 3.62);

        Book theHobbit = new Book(6, "The Hobbit", "J.R.R Tolkien",
                "On-Shelf", "Fantasy", 2002, 8, 15, "Houghton Mifflin", 4.28);

        Book narnia = new Book(7, "The Lion the Witch and the Wardrobe", "C.S Louis",
                "On-Shelf", "Fantasy", 1950, 10, 16, "HarperCollins", 4.23);

        Book charlotteWeb = new Book(8, "Charlotte's Web", "E.B White",
                "DUE 02-04-22", "Children's", 1952, 5, 15, "HarperCollins", 4.18);

        Book theBFG = new Book(9, "The BFG", "Roald Dahl",
                "On-Shelf", "Children's", 1982, 11, 1, "Puffin Books", 4.22);

        Book toKillAMockingBird = new Book(10, "To Kill a Mocking bird", "Harper Lee",
                "On-Shelf", "Fantasy", 1960, 7, 11, "Harper Perennial Modern Classics", 4.27);

        Book charlotteWeb2 = new Book(11, "Charlotte's Web", "E.B White",
                "DUE 02-04-22", "Children's", 1952, 10, 15, "HarperCollins", 4.18);

        // Adding the objects into the ArrayList
        bookList.add(harryPotter);
        bookList.add(hungerGames);
        bookList.add(divergent);
        bookList.add(faultInOurStars);
        bookList.add(twilight);
        bookList.add(theHobbit);
        bookList.add(narnia);
        bookList.add(charlotteWeb);
        bookList.add(theBFG);
        bookList.add(toKillAMockingBird);
        bookList.add(charlotteWeb2);

        // Adding the objects into the HashMap
        for(Book book : bookList)
        {
            bookHashMap.put(book.getId(), book);
        }

        // Adding the objects into the TreeMap
        for(Book book : bookList)
        {
            bookTreeMap.put(book.getId(), book);
        }

        // Adding books to Priority Queue
        // Alphabetical ordering
        bookPriorityQueue.addAll(bookList);
    }

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

    private void displayByTitleThenMonth(PriorityQueue<Book> books)
    {
        System.out.println("\n----- PriorityQueue -----\n");

        while(!books.isEmpty())
        {
            System.out.println(books.poll());
        }
    }

    private void displayArrayList(ArrayList<Book> books)
    {
        System.out.println("\n----- ArrayList -----\n");
        
        if(!(books.isEmpty()))
        {
            System.out.printf(Colours.BOLD+"%-8s%-50s%-25s%-25s%-25s%-20s%-35s%s%n", "ID", "Title", "Author",
                    "Status", "Genre", "Date_Published", "Publisher", "Rating" + Colours.RESET);

            for (Book book : bookList) {
                System.out.printf("%-8s%-50s%-25s%-25s%-25s%-20s%-35s%s%n", book.getId(), book.getTitle(), book.getAuthor(),
                        book.getStatus(), book.getGenre(), book.getDatePublished(), book.getPublisher(), book.getRating());
            }
        }
        else
        {
            System.out.println(Colours.RED + "No Books in the system." + Colours.RESET);
        }
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
        displayByTitleThenMonth(bookPriorityQueue);
    }

}
