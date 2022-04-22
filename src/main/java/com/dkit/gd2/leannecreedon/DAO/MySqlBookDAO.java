package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.*;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlBookDAO extends MySqlDao implements IBookInterface
{
    /* SOME FEATURES ARE TEMPORARY! */

    @Override
    public List<Book> findAllBooks() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();

        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM BOOKS";
            ps = con.prepareStatement(query);

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                Date dateP = rs.getDate("date_published");
                String publisher = rs.getString("publisher");
                double rating = rs.getDouble("rating");

                // Help from this site => https://www.javadevjournal.com/java/convert-date-to-localdate/
                LocalDate datePublished = Instant.ofEpochMilli(dateP.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                Book b = new Book(id, title, author, status, genre, datePublished,
                        publisher, rating);
                books.add(b);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllBooks() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllBooks() " + sqe.getMessage());
            }
        }
        return books;
    }

    @Override
    public Book findBookById(int id) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book b = null;

        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "select * from books where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                Date dateP = rs.getDate("date_published");
                String publisher = rs.getString("publisher");
                double rating = rs.getDouble("rating");

                LocalDate datePublished = Instant.ofEpochMilli(dateP.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                b = new Book(bookId, title, author, status, genre, datePublished,
                        publisher, rating);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllBooks() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllBooks() " + sqe.getMessage());
            }
        }
        return b;
    }

    @Override
    public void deleteBookById(int id) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book b = null;

        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "delete from books where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            // Use the prepared statement to execute the sql
            ps.executeUpdate();
        }

        catch(SQLException sqe)
        {
            throw new DaoException("findAllBooks() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllBooks() " + sqe.getMessage());
            }
        }
    }

    @Override
    public void insertABook(Book book) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "INSERT INTO books(title, author, status, genre, date_published, publisher, rating) values(?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getStatus());
            ps.setString(4, book.getGenre());
            ps.setDate(5,java.sql.Date.valueOf(book.getDatePublished()));
            ps.setString(6, book.getPublisher());
            ps.setDouble(7, book.getRating());

            // Use the prepared statement to execute the sql
            ps.executeUpdate();
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllBooks() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllBooks() " + sqe.getMessage());
            }
        }
    }


    @Override
    public List<Book> findBookUsingFilter(LocalDate from, LocalDate to) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Book> dateRangeBooks = new ArrayList<>();
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM BOOKS WHERE date_published BETWEEN ? AND ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                Date dateP = rs.getDate("date_published");
                String publisher = rs.getString("publisher");
                double rating = rs.getDouble("rating");

                LocalDate datePublished = Instant.ofEpochMilli(dateP.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                Book b = new Book(bookId, title, author, status, genre, datePublished,
                        publisher, rating);
                dateRangeBooks.add(b);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllBooks() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllBooks() " + sqe.getMessage());
            }
        }
        return dateRangeBooks;
    }

    @Override
    public String findAllBooksJson() throws DaoException
    {
        List<Book> books = findAllBooks();
        return new Gson().toJson(books);
    }

    @Override
    public JSONObject findBookByIdJson(int id) throws DaoException {
        return null;
    }

    @Override
    public String findBookByIdJsonString(int id) throws DaoException
    {
        Book book = findBookById(id);

        Gson parser = new Gson();
        String bookS = parser.toJson(book);

        return bookS;
    }
}
