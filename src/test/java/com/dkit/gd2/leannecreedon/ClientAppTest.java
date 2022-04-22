package com.dkit.gd2.leannecreedon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for my Library System
 */
public class ClientAppTest
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();

    // Test construction of Book object.
    @Test
    public void createBook()
    {
        System.out.println("\nTest : construction of Book object.");
        Book book= new Book(1, "Harry Potter and the Chamber of Secrets", "J.K Rolling",
                "On-Shelf", "Fantasy", 1999, 6,2, "Arthur A. Levine", 4.43);
        assertEquals(1, book.getId());
        assertEquals("Harry Potter and the Chamber of Secrets", book.getTitle());
        assertEquals("J.K Rolling", book.getAuthor());
        assertEquals("On-Shelf", book.getStatus());
        assertEquals("Fantasy", book.getGenre());
        assertEquals(1999, book.getDatePublished().getYear());
        assertEquals(6, book.getDatePublished().getMonthValue());
        assertEquals(2, book.getDatePublished().getDayOfMonth());
        assertEquals("Arthur A. Levine", book.getPublisher());
        assertEquals(4.43, book.getRating(),0.05);
    }

    // Test construction of Patron object.
    @Test
    public void createPatron()
    {
        System.out.println("\nTest : construction of Patron object.");
        Patron patron= new Patron("Mary", "O'Brain", 1999, 6, 3, "Female",
                "17 St James Avenue", "Drumcondra", "Dublin", "D03HF43",
                "marysnottoobad@hotmail.com", "marysnottoobad@hotmail.com", "856642286", 1234, 0);
        assertEquals("Mary", patron.getFirstName());
        assertEquals("O'Brain", patron.getLastName());
        assertEquals(1999, patron.getDateOfBirth().getYear());
        assertEquals(6, patron.getDateOfBirth().getMonthValue());
        assertEquals(3, patron.getDateOfBirth().getDayOfMonth());
        assertEquals("Female", patron.getGender());
        assertEquals("17 St James Avenue", patron.getAddressLine1());
        assertEquals("Drumcondra", patron.getAddressLine2());
        assertEquals("Dublin", patron.getCounty());
        assertEquals("D03HF43", patron.getEirCode());
        assertEquals("marysnottoobad@hotmail.com", patron.getEmail());
        assertEquals("marysnottoobad@hotmail.com", patron.getConfirmEmail());
        assertEquals("856642286", patron.getMobile());
        assertEquals(1234, patron.getPin());
        assertEquals(0, patron.getLibraryCardNumber()); // Error comes up when I try to test for a 14 digit number
    }

    // Test patron login.
    @Test
    public void patronLogin()
    {
        System.out.println("\nTest : patron login.");
        Patron patron= new Patron("O'Brian", "Mary", 0, 1234);
        assertEquals("O'Brian", patron.getLastName());
        assertEquals("Mary", patron.getFirstName());
        assertEquals(0, patron.getLibraryCardNumber());
        assertEquals(1234, patron.getPin());
    }

    // Test part 2 check book found.
    @Test
    public void checkBookFoundTest1()
    {
        try {
            System.out.println("\nTest : checkBookFound method where id = 1.");
            Book book = IBookDAO.findBookById(1);
            Books instance = new Books();
            boolean expectedResult = true;
            boolean result = instance.checkBookFound(book);
            assertEquals(expectedResult, result);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test part 2 check book found.
    @Test
    public void checkBookFoundTest2()
    {
        try {
            System.out.println("\nTest : checkBookFound method where id = 20.");
            Book book = IBookDAO.findBookById(20);
            Books instance = new Books();
            boolean expectedResult = false;
            boolean result = instance.checkBookFound(book);
            assertEquals(expectedResult, result);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test findAllBooks not null.
    @Test
    public void findAllBooks()
    {
        try {
            System.out.println("\nTest : findAllBooks() method.");
            List<Book> books = IBookDAO.findAllBooks();
            assertNotNull(books);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test findAllPatrons not null.
    @Test
    public void findAllPatrons()
    {
        try {
            System.out.println("\nTest : findAllPatrons() method.");
            List<Patron> patrons = IPatronDAO.findAllPatrons();
            assertNotNull(patrons);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test findPatronByEmail true
    @Test
    public void findPatronByEmailTest1()
    {
        try {
            System.out.println("\nTest : findPatronByEmail() method.");
            Patron patron = IPatronDAO.findPatronByEmail("vickyknowswhatsup@gmail.com");
            Patrons instance = new Patrons();
            boolean expectedResult = true;
            boolean result = instance.checkPatronFound(patron);
            assertEquals(expectedResult, result);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test findPatronByEmail false
    @Test
    public void findPatronByEmailTest2()
    {
        try {
            System.out.println("\nTest : findPatronByEmail() method.");
            Patron patron = IPatronDAO.findPatronByEmail("jimmymartin@hotmail.com");
            Patrons instance = new Patrons();
            boolean expectedResult = false;
            boolean result = instance.checkPatronFound(patron);
            assertEquals(expectedResult, result);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

}
