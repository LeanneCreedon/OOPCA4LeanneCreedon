package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.util.List;

public class DatabaseMain
{
    public static void main(String[] args)
    {

        IBookInterface IBookDAO = new MySqlBookDAO(); // Easy to switch for Mongo!

        try
        {
            List<Book> books = IBookDAO.findAllBooks();

            if(books.isEmpty())
            {
                System.out.println("There are no books");
            }

            printAllBooks(books);

            Book book = IBookDAO.findBookById(4);
            checkBookFound(book);

        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }

    private static void checkBookFound(Book book)
    {
        if(book != null)
        {
            System.out.println("Book found: " + book);
        }
        else
        {
            System.out.println("No book with that id found");
        }
    }

    private static void printAllBooks(List<Book> books)
    {
        for(Book book : books)
        {
            System.out.println(book);
        }
    }
}
