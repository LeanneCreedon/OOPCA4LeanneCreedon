package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.util.List;

public interface IPatronInterface
{

    public List<Patron> findAllPatrons() throws DaoException;
    public Patron findPatronByEmail(String email) throws DaoException;
    public Patron findPatronByPin(long pin) throws DaoException;
    public void deletePatronByPin(long pin) throws DaoException;
    public void insertAPatron(Patron patron) throws DaoException;
    //public void createPinNumber(long pin) throws DaoException;
}
