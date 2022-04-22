package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class AddAPatronCommand implements Command
{
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
//        String patronToBeAdded = incomingPacket.getPayload();
//        String patron = null;
//        try
//        {
//            patron = IPatronDAO.insertAPatron((Integer.parseInt(patronToBeAdded)));
//        }
//        catch(DaoException daoe)
//        {
//            System.out.println(daoe.getMessage());
//        }
        return new Packet(incomingPacket.getMessageType(), "patron");
    }
}
