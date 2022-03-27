package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlBookDAO extends MySqlDao implements IBookInterface
{
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
                String status = rs.getString("author");
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
                String status = rs.getString("author");
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
}
