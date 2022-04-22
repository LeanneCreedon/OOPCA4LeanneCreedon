package com.dkit.gd2.leannecreedon.server;


import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class DisplayBookByIdCommand implements Command
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String bookToBeFound = incomingPacket.getPayload();
        String book = null;
        try
        {
            book = IBookDAO.findBookByIdJsonString((Integer.parseInt(bookToBeFound)));
        }
        catch(DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
        return new Packet(incomingPacket.getMessageType(), book);
    }
}
