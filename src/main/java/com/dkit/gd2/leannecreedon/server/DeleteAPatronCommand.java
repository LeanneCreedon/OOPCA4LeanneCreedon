package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.Colours;
import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;

public class DeleteAPatronCommand implements Command
{
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String email = incomingPacket.getPayload();
        String message = Colours.GREEN+"Successfully deleted account"+Colours.RESET;
        try
        {
            IPatronDAO.deletePatronByEmail(email);
        }
        catch(DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
        return new Packet(incomingPacket.getMessageType(), message);
    }
}
