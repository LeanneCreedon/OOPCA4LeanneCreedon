package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class DisplayAllBooksCommand implements Command
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    @Override
    public Packet createResponse(Packet incomingMessage)
    {
        String books = null;
        try
        {
            books = IBookDAO.findAllBooksJson();
        }
        catch(DaoException doae)
        {
            System.out.println(doae.getMessage());
        }
        return new Packet(incomingMessage.getMessageType(), books);
    }
}
