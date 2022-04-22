package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public interface IBookInterface
{
    public List<Book> findAllBooks() throws DaoException;
    public Book findBookById(int id) throws DaoException;
    public void deleteBookById(int id) throws DaoException;
    public void insertABook(Book book) throws DaoException;
    public List<Book> findBookUsingFilter(LocalDate from, LocalDate to) throws DaoException;
    public String findAllBooksJson() throws DaoException;
    public JSONObject findBookByIdJson(int id) throws DaoException;
    public String findBookByIdJsonString(int id) throws DaoException;

}
