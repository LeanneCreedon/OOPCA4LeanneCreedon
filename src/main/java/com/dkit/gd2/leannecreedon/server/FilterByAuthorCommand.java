package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class FilterByAuthorCommand implements Command
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String author = incomingPacket.getPayload();
        String books = null;
        try
        {
            books = IBookDAO.findBookUsingFilterByAuthorJson(author);
        }
        catch(DaoException doae)
        {
            System.out.println(doae.getMessage());
        }
        return new Packet(incomingPacket.getMessageType(), books);
    }
}
