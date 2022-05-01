package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;
import com.dkit.gd2.leannecreedon.core.Packet;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddAPatronCommand implements Command
{
    private static final IPatronInterface IPatronDAO = new MySqlPatronDAO();
    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String patronToBeAdded = incomingPacket.getPayload();
        Gson gsonParser = Converters.registerLocalDate(new GsonBuilder()).create();
        Patron patron = gsonParser.fromJson(patronToBeAdded, Patron.class);
        try
        {
            IPatronDAO.insertAPatron(patron);
        }
        catch(DaoException daoe)
        {
            System.out.println(daoe.getMessage());
        }
        return incomingPacket;
    }
}
