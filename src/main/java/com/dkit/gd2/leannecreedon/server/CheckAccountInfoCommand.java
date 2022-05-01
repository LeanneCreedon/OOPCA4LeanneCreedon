package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class CheckAccountInfoCommand implements Command
{
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String accountToBeFound = incomingPacket.getPayload();
        String account = null;
        try
        {
            account = IPatronDAO.findPatronByEmailJsonString(accountToBeFound);
        }
        catch(DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
        return new Packet(incomingPacket.getMessageType(), account);
    }
}
