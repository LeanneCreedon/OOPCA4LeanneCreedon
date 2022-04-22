package com.dkit.gd2.leannecreedon.server;

import com.dkit.gd2.leannecreedon.core.Protocol;

public class CommandFactory
{
    public Command createCommand(Protocol command)
    {
        Command c = null;

        if(command == Protocol.DISPLAY_BOOK_BY_ID)
        {
            c = new DisplayBookByIdCommand();
        }
        if(command == Protocol.DISPLAY_ALL_BOOKS)
        {
            c = new DisplayAllBooksCommand();
        }
        if(command == Protocol.ADD_A_PATRON)
        {
            c = new AddAPatronCommand();
        }
        if(command == Protocol.DELETE_A_PATRON)
        {
            c = new DeleteAPatronCommand();
        }
        return c;
    }
}
