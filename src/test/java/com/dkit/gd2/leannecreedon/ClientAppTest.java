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

import java.time.LocalDate;
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
    public void loginTest1()
    {
        System.out.println("\nTest : patron login true");
        Patrons instance = new Patrons();
        String lName = "Meegan";
        String fName = "Vicky";
        String lCard = "55246530458920";
        String pin = "7298";
        boolean expectedResult = true;
        boolean result = instance.login(lName, fName, Long.parseLong(lCard), Long.parseLong(pin));
        assertEquals(expectedResult, result);
    }
    @Test
    public void loginTest2()
    {
        System.out.println("\nTest : patron login false");
        Patrons instance = new Patrons();
        String lName = "Meegan";
        String fName = "Vicky";
        String lCard = "55246530458920";
        String pin = "1234"; // wrong pin, should result in false
        boolean expectedResult = false;
        boolean result = instance.login(lName, fName, Long.parseLong(lCard), Long.parseLong(pin));
        assertEquals(expectedResult, result);
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
            System.out.println("\nTest : checkBookFound method where id = 58.");
            Book book = IBookDAO.findBookById(58);
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

    // Test findPatronByEmail
    @Test
    public void findPatronByEmailTest1()
    {
        try {
            System.out.println("\nTest : findPatronByEmail() method test 1 - true");
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
    @Test
    public void findPatronByEmailTest2()
    {
        try {
            System.out.println("\nTest : findPatronByEmail() method test 2 - false");
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

    // Test findBookUsingFilter()
    @Test
    public void findBookUsingFilter()
    {
        try {
            System.out.println("\nTest : findBookUsingFilter() method.");
            LocalDate from = LocalDate.parse("1998-08-23");
            LocalDate to = LocalDate.parse("2016-09-20");
            List<Book> filteredBooks = IBookDAO.findBookUsingFilter(from, to);
            Books instance = new Books();
            boolean expectedResult = true;
            boolean result = instance.printBookList(filteredBooks);
            assertEquals(expectedResult, result);
        }
        catch(DaoException dao)
        {
            System.out.println(dao.getMessage());
        }
    }

    // Test getCurrentAccountEmail().
    @Test
    public void getAccountEmailTest1()
    {
        System.out.println("\nTest : getCurrentAccountEmail() method test 1 - correct details");
        Patrons instance = new Patrons();
        String lCard = "55246530458920";
        String pin = "7298";
        String expectedResult = "vickyknowswhatsup@gmail.com";
        String result = instance.getCurrentAccountEmail(Long.parseLong(lCard), Long.parseLong(pin));
        assertEquals(expectedResult, result);
    }
    @Test
    public void getAccountEmailTest2()
    {
        System.out.println("\nTest : getCurrentAccountEmail() method test 1 - incorrect details");
        Patrons instance = new Patrons();
        String lCard = "55246530458920";
        String pin = "1234";
        String expectedResult = null;
        String result = instance.getCurrentAccountEmail(Long.parseLong(lCard), Long.parseLong(pin));
        assertEquals(expectedResult, result);
    }

    // Test checkPatronFoundByEmail().
    @Test
    public void checkPatronFoundByEmailTest1()
    {
        System.out.println("\nTest : checkPatronFoundByEmail() method test 1 - true");
        Patrons instance = new Patrons();
        String email = "vickyknowswhatsup@gmail.com";
        boolean expectedResult = true;
        boolean result = instance.checkPatronFoundByEmail(email);
        assertEquals(expectedResult, result);
    }
    @Test
    public void checkPatronFoundByEmailTest2()
    {
        System.out.println("\nTest : checkPatronFoundByEmail() method test 2 - false");
        Patrons instance = new Patrons();
        String email = "hello@hotmail.com";
        boolean expectedResult = false;
        boolean result = instance.checkPatronFoundByEmail(email);
        assertEquals(expectedResult, result);
    }

    // Test checkPatronFound().
    @Test
    public void checkPatronFoundTest1()
    {
        System.out.println("\nTest : checkPatronFound() method test 1 - true");
        Patrons instance = new Patrons();
        String pin = "7298";
        String lCard = "55246530458920";
        Patron patron= new Patron("Vicky", "Meegan", 1998, 8, 23, "Female",
                "14 Ard Easmuinn", "Dundalk", "Co. Louth", "A23KJ54",
                "vickyknowswhatsup@gmail.com", "vickyknowswhatsup@gmail.com", "836593384",
                Long.parseLong(pin), Long.parseLong(lCard));
        boolean expectedResult = true;
        boolean result = instance.checkPatronFound(patron);
        assertEquals(expectedResult, result);
    }
    @Test
    public void checkPatronFoundTest2()
    {
        System.out.println("\nTest : checkPatronFound() method test 2 - false");
        Patrons instance = new Patrons();
        String pin = "1234";
        String lCard = "17296748372955";
        Patron patron= new Patron("Jimmy", "McBride", 1998, 8, 23, "Male",
                "14 Ard Easmuinn", "Dundalk", "Co. Louth", "A23KJ54",
                "jimmy@gmail.com", "jimmy@gmail.com", "836593384",
                Long.parseLong(pin), Long.parseLong(lCard));
        boolean expectedResult = false;
        boolean result = instance.checkPatronFound(patron);
        assertEquals(expectedResult, result);
    }

    // Test checkAuthorOnSystem().
    @Test
    public void checkAuthorOnSystemTest1()
    {
        System.out.println("\nTest : checkAuthorOnSystem() method test 1 - true");
        Books instance = new Books();
        String author = "J.R.R. Tolkien";
        boolean expectedResult = true;
        boolean result = instance.checkAuthorOnSystem(author);
        assertEquals(expectedResult, result);
    }
    @Test
    public void checkAuthorOnSystemTest2()
    {
        System.out.println("\nTest : checkAuthorOnSystem() method test 2 - false");
        Books instance = new Books();
        String author = "Rold Dahl";
        boolean expectedResult = false;
        boolean result = instance.checkAuthorOnSystem(author);
        assertEquals(expectedResult, result);
    }
}
