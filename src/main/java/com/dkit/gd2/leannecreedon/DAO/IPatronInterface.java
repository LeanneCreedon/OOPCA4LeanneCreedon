package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import java.util.List;

public interface IPatronInterface
{
    public List<Patron> findAllPatrons() throws DaoException;
    public Patron findPatronByEmail(String email) throws DaoException;
    public void deletePatronByEmail(String pin) throws DaoException;
    public void insertAPatron(Patron patron) throws DaoException;
    public String findPatronByEmailJsonString(String email) throws DaoException;
}
