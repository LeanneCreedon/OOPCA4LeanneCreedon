package com.dkit.gd2.leannecreedon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Books
{
    /* Manager class for books */

    private final ArrayList<Book> bookList;
    private final HashMap<String, Book> bookHashMap;
    private final TreeMap<Integer, Book> bookTreeMap;
    private final PriorityQueue<Book> bookAlphabeticalOrder;

    public Books()
    {
        this.bookList = new ArrayList<>();
        this.bookHashMap = new HashMap<>();
        this.bookTreeMap = new TreeMap<>();
        this.bookAlphabeticalOrder = new PriorityQueue<>(new OrderBooksByTitleComparator());
    }

    public void add10Books()
    {
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
                "DUE 02-04-22", "Children's", 1952, 10, 15, "HarperCollins", 4.18);

        Book theBFG = new Book(9, "The BFG", "Roald Dahl",
                "On-Shelf", "Children's", 1982, 11, 1, "Puffin Books", 4.22);

        Book toKillAMockingBird = new Book(10, "To Kill a Mocking bird", "Harper Lee",
                "On-Shelf", "Fantasy", 1960, 7, 11, "Harper Perennial Modern Classics", 4.27);

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

        // Adding the objects into the HashMap
        bookHashMap.put("A", harryPotter); //Not sure what key to use, just have letter for now
        bookHashMap.put("B", hungerGames);
        bookHashMap.put("C", divergent);
        bookHashMap.put("D", faultInOurStars);
        bookHashMap.put("E", twilight);
        bookHashMap.put("F", theHobbit);
        bookHashMap.put("G", narnia);
        bookHashMap.put("H", charlotteWeb);
        bookHashMap.put("I", theBFG);
        bookHashMap.put("J", toKillAMockingBird);

        // Adding the objects into the TreeMap
        bookTreeMap.put(101, harryPotter); //Not sure what key to use, just have number for now
        bookTreeMap.put(102, hungerGames);
        bookTreeMap.put(103, divergent);
        bookTreeMap.put(104, faultInOurStars);
        bookTreeMap.put(105, twilight);
        bookTreeMap.put(106, theHobbit);
        bookTreeMap.put(107, narnia);
        bookTreeMap.put(108, charlotteWeb);
        bookTreeMap.put(109, theBFG);
        bookTreeMap.put(110, toKillAMockingBird);

        // Adding books to Priority Queue
        bookAlphabeticalOrder.add(harryPotter);
        bookAlphabeticalOrder.add(hungerGames);
        bookAlphabeticalOrder.add(divergent);
        bookAlphabeticalOrder.add(faultInOurStars);
        bookAlphabeticalOrder.add(twilight);
        bookAlphabeticalOrder.add(theHobbit);
        bookAlphabeticalOrder.add(narnia);
        bookAlphabeticalOrder.add(charlotteWeb);
        bookAlphabeticalOrder.add(theBFG);
        bookAlphabeticalOrder.add(toKillAMockingBird);
    }

    public void printAllBooks()
    {
        System.out.println("\n-------------------- ArrayList ------------------------\n");
        // Printing out books using ArrayList
        if(!(bookList.isEmpty()))
        {
            System.out.println("Printing all details of books: -\n");
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

        // Printing out books using HashMap
        System.out.println("\n-------------------- HashMap ------------------------\n");
        for(String key : bookHashMap.keySet())
        {
            System.out.println(key + " : " + bookHashMap.get(key));
        }

        System.out.println("\n-------------------- TreeMap ------------------------\n");
        for(int key : bookTreeMap.keySet())
        {
            System.out.println(key + " : " + bookTreeMap.get(key));
        }
    }

    public boolean findBookUsingKey(String userKey)
    {
        for(String key : bookHashMap.keySet())
        {
            if(key.equalsIgnoreCase(userKey))
            {
                System.out.println(key + " : " + bookHashMap.get(key));
                return true;
            }
        }
        return false;
    }

    public void displayBooksFromTreeMap()
    {
        System.out.println("\n--- TreeMap ---\n");
        for(int key : bookTreeMap.keySet())
        {
            System.out.println(key + " : " + bookTreeMap.get(key));
        }
    }

    public void printAlphabeticalOrder()
    {
        System.out.println("\n--- PriorityQueue ---\n");

        while(!bookAlphabeticalOrder.isEmpty())
        {
            System.out.println(bookAlphabeticalOrder.poll());
        }
    }

}
