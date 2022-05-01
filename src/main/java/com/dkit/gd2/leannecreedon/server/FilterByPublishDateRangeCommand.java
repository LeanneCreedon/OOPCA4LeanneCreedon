package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IBookInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlBookDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

import java.time.LocalDate;

public class FilterByPublishDateRangeCommand implements Command
{
    private static final IBookInterface IBookDAO = new MySqlBookDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String dates = incomingPacket.getPayload();
        String[] datesSplit = dates.split("&&");
        String from = datesSplit[0];
        String to = datesSplit[1];

        String books = null;
        try
        {
            books = IBookDAO.findBookUsingFilterJson(LocalDate.parse(from),LocalDate.parse(to));
        }
        catch(DaoException doae)
        {
            System.out.println(doae.getMessage());
        }
        return new Packet(incomingPacket.getMessageType(), books);
    }
}
