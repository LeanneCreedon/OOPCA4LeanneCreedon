package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Book;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.util.List;

public interface IBookInterface
{
    public List<Book> findAllBooks() throws DaoException;
    public Book findBookById(int id)
            throws DaoException;
}
